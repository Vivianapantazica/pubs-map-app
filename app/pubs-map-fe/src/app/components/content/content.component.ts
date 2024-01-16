import {
  Component,
  OnInit,
  ViewChild,
  ElementRef,
  Input,
  Output,
  EventEmitter,
  OnDestroy
} from "@angular/core";

import esri = __esri;


import Config from '@arcgis/core/config';
import WebMap from '@arcgis/core/WebMap';
import MapView from '@arcgis/core/views/MapView';
import * as Locator from '@arcgis/core/rest/locator';
import SimpleMarkerSymbol from '@arcgis/core/symbols/SimpleMarkerSymbol';

import GraphicsLayer from "@arcgis/core/layers/GraphicsLayer";
import Graphic from '@arcgis/core/Graphic';
import Point from '@arcgis/core/geometry/Point';

import FeatureLayer from '@arcgis/core/layers/FeatureLayer';
import Locate from '@arcgis/core/widgets/Locate';


import FeatureSet from '@arcgis/core/rest/support/FeatureSet';
import RouteParameters from '@arcgis/core/rest/support/RouteParameters';
import * as route from "@arcgis/core/rest/route.js";
import Search from "@arcgis/core/widgets/Search.js";
import pubNames from './pubNames'
import {Pub} from "../../models/Pub";
import {PubsService} from "../../services/pubs.service";

@Component({
  selector: 'app-content',
  templateUrl: './content.component.html',
  styleUrls: ['./content.component.css']
})
export class ContentComponent implements OnInit, OnDestroy {
  private pubsList: Pub[] = [];

  @Output() logoutEvent = new EventEmitter<void>();

  handleLogout() {
    this.logoutEvent.emit();
  }

  @Output() mapLoadedEvent = new EventEmitter<boolean>();

  @ViewChild("mapViewNode", { static: true }) private mapViewEl: ElementRef;

  map: esri.Map;
  view: esri.MapView;
  pointGraphic: esri.Graphic;
  graphicsLayer: esri.GraphicsLayer;
  locate: esri.Locate;


  // Attributes
  zoom = 10;
  center: Array<number> = [26.1068, 44.4325];
  basemap = "streets-vector";
  loaded = false;
  pointCoords: number[] = [26.1068, 44.4325];
  dir: number = 0;
  count: number = 0;
  timeoutHandler = null;
  locationIndex: number = 0;

  constructor(private pubsService: PubsService) { }

  async findPlaces(addresses: string[], pt) {
    const geocodingServiceUrl = "http://geocode-api.arcgis.com/arcgis/rest/services/World/GeocodeServer";

    const geocodePromises = addresses.map((address) => {
      const params = {
        address: {
          address: address,
        },
        location: pt,
        outFields: ["PlaceName","Place_addr"]
      };

    return Locator.addressToLocations(geocodingServiceUrl, params);
    });

    try {
      const resultsArray = await Promise.all(geocodePromises);

      const results = resultsArray.reduce((acc, cur) => acc.concat(cur), []);

      this.showResults(results);
    } catch (error) {
      console.error("Error in geocoding:", error);
    }

  }

  showResults(results) {
    const obj = this;
    obj.view.popup.close();
    obj.view.graphics.removeAll();

    const markerSymbol = new SimpleMarkerSymbol({
      color: "yellow",
      size: "16px",
      outline: {
        color: "blue",
        width: "4px",
      },
    });

    const highlightedMarkerSymbol = new SimpleMarkerSymbol({
      color: "pink",
      size: "20px",
      outline: {
        color: "black",
        width: "4px",
      },
    });

    results.forEach((result) => {
      const pub = obj.pubsList.find((pub) => pub.name === result.attributes.PlaceName);
      const isHighlightedPub = obj.pubsList.indexOf(pub) === obj.locationIndex;

      const description = pub ? pub.description : "Description not available";

      obj.view.graphics.add(
        new Graphic({
          attributes: result.attributes,
          geometry: result.location,
          symbol: isHighlightedPub ? highlightedMarkerSymbol : markerSymbol,
          popupTemplate: {
            title: result.attributes.PlaceName,
            content: `${description}<br><br>${result.location.x.toFixed(5)}, ${result.location.y.toFixed(5)}`,
          },
        })
      );
    });
  }
  
  async initializeMap() {
    try {

      const mapProperties: esri.WebMapProperties = {
        basemap: this.basemap
      };

      Config.apiKey = "AAPKdaa77140e8c6494bb22c46016951ec55Maw6uZEVbMgj84cxD-zabCDB2kWcEOdAgGTkGum-UPKIhySapsfeQKBWqWZp1Gjt";

      this.map = new WebMap(mapProperties);

      this.addFeatureLayers();
      this.addPoint(this.pointCoords[1], this.pointCoords[0]);

      const mapViewProperties = {
        container: this.mapViewEl.nativeElement,
        center: this.center,
        zoom: this.zoom,
        map: this.map
      };

      this.view = new MapView(mapViewProperties);

      const locateProperties = {
        view: this.view,
        useHeadingEnabled: false,
        goToOverride: (view, options) => {
          options.target.scale = 1500;
          return view.goTo(options.target);
        }
      };

      this.locate = new Locate(locateProperties);

      this.view.ui.add(this.locate, "top-left");

      let obj = this;

      await this.findPlaces(this.pubsList.map(x => x.name), this.center)

      this.view.on('pointer-move', ["Shift"], (event) => {
        let point = this.view.toMap({ x: event.x, y: event.y });
        console.log("map moved: ", point.longitude, point.latitude);
      });

      const searchWidget = new Search({
        view: this.view
      });

      this.view.ui.add(searchWidget, {
        position: "top-left",
        index: 0
      });

      await this.view.when(); // wait for map to load
      console.log("ArcGIS map loaded");
      console.log(this.view.center);
      return this.view;
    } catch (error) {
      console.log("EsriLoader: ", error);
    }

  }


  addFeatureLayers() {

    const popupTrailheads = {
      "title": "Trailhead",
      "content": "<b>Trail:</b> {TRL_NAME}<br><b>City:</b> {CITY_JUR}<br><b>Cross Street:</b> {X_STREET}<br><b>Parking:</b> {PARKING}<br><b>Elevation:</b> {ELEV_FT} ft"
    }

    var trailheadsLayer: esri.FeatureLayer = new FeatureLayer({
      url:
        "https://services3.arcgis.com/GVgbJbqm8hXASVYi/arcgis/rest/services/Trailheads/FeatureServer/0",
    });

    this.map.add(trailheadsLayer);

    const popupTrails = {
      title: "Trail Information",
      content: [{
       type: "media",
        mediaInfos: [{
          type: "column-chart",
          caption: "",
          value: {
            fields: [ "ELEV_MIN","ELEV_MAX" ],
            normalizeField: null,
            tooltipField: "Min and max elevation values"
            }
          }]
      }]
    }

    var trailsLayer: esri.FeatureLayer = new FeatureLayer({
      url:
        "https://services3.arcgis.com/GVgbJbqm8hXASVYi/arcgis/rest/services/Trails/FeatureServer/0",
    opacity: 0.75,
    });

    this.map.add(trailsLayer, 0);

    var parksLayer: esri.FeatureLayer = new FeatureLayer({
      url:
        "https://services3.arcgis.com/GVgbJbqm8hXASVYi/arcgis/rest/services/Parks_and_Open_Space/FeatureServer/0"
    });

    this.map.add(parksLayer, 0);

    console.log("feature layers added");
  }

  addPoint(lat: number, lng: number) {
    this.graphicsLayer = new GraphicsLayer();
    this.map.add(this.graphicsLayer);

    let point = new Point({
      longitude: lng,
      latitude: lat
    });

    const simpleMarkerSymbol = {
      type: "simple-marker",
      color: [226, 119, 40],  // Orange
      outline: {
        color: [255, 255, 255], // White
        width: 1
      }
    };

    this.pointGraphic = new Graphic({
      geometry: point,
      symbol: simpleMarkerSymbol
    });

    this.graphicsLayer.add(this.pointGraphic);
  }

  removePoint() {
    if (this.pointGraphic != null) {
      this.graphicsLayer.remove(this.pointGraphic);
    }
  }

  addRouter() {
    const routeUrl = "https://route-api.arcgis.com/arcgis/rest/services/World/Route/NAServer/Route_World";

    this.view.on("click", (event) => {
      console.log("point clicked: ", event.mapPoint.latitude, event.mapPoint.longitude);
      if (this.view.graphics.length === 0) {
        addGraphic("origin", event.mapPoint);
      } else if (this.view.graphics.length === 1) {
        addGraphic("destination", event.mapPoint);
        getRoute(); // Call the route service
      } else {
        this.view.graphics.removeAll();
        addGraphic("origin", event.mapPoint);
      }
    });

    var addGraphic = (type: any, point: any) => {
      const graphic = new Graphic({
        symbol: {
          type: "simple-marker",
          color: (type === "origin") ? "white" : "black",
          size: "8px"
        } as any,
        geometry: point
      });
      this.view.graphics.add(graphic);
    }

    var getRoute = () => {
      const routeParams = new RouteParameters({
        stops: new FeatureSet({
          features: this.view.graphics.toArray()
        }),
        returnDirections: true
      });

      route.solve(routeUrl, routeParams).then((data: any) => {
        for (let result of data.routeResults) {
          result.route.symbol = {
            type: "simple-line",
            color: [5, 150, 255],
            width: 3
          };
          this.view.graphics.add(result.route);
        }

        if (data.routeResults.length > 0) {
          const directions: any = document.createElement("ol");
          directions.classList = "esri-widget esri-widget--panel esri-directions__scroller";
          directions.style.marginTop = "0";
          directions.style.padding = "15px 15px 15px 30px";
          const features = data.routeResults[0].directions.features;

          let sum = 0;
          features.forEach((result: any, i: any) => {
            sum += parseFloat(result.attributes.length);
            const direction = document.createElement("li");
            direction.innerHTML = result.attributes.text + " (" + result.attributes.length + " miles)";
            directions.appendChild(direction);
          });

          sum = sum * 1.609344;
          console.log('dist (km) = ', sum);
          this.view.ui.empty("top-right");
          this.view.ui.add(directions, "top-right");
        }
      }).catch((error: any) => {
        console.log(error);
      });
    }
  }

  runTimer() {
    this.timeoutHandler = setTimeout(() => {
      this.animatePointDemo();
      this.runTimer();
    }, 200);
  }

  animatePointDemo() {
    this.removePoint();
    switch (this.dir) {
      case 0:
        this.pointCoords[1] += 0.01;
        break;
      case 1:
        this.pointCoords[0] += 0.02;
        break;
      case 2:
        this.pointCoords[1] -= 0.01;
        break;
      case 3:
        this.pointCoords[0] -= 0.02;
        break;
    }

    this.count += 1;
    if (this.count >= 10) {
      this.count = 0;
      this.dir += 1;
      if (this.dir > 3) {
        this.dir = 0;
      }
    }

    this.addPoint(this.pointCoords[1], this.pointCoords[0]);
  }

  stopTimer() {
    if (this.timeoutHandler != null) {
      clearTimeout(this.timeoutHandler);
      this.timeoutHandler = null;
    }

  }

  async ngOnInit() {
    await this.fetchPubs();
    this.initializeMap().then(() => {
      console.log("mapView ready: ", this.view.ready);
      this.loaded = this.view.ready;
      this.mapLoadedEvent.emit(true);
    });

    // handle animation start
    setInterval(async () => {
      this.locationIndex = Math.floor(Math.random() * this.pubsList.length);
      await this.findPlaces(this.pubsList.map(x => x.name), this.center);
    }, 500);
  }

  private async fetchPubs(): Promise<void> {
    return new Promise<void>((resolve) => {
      // this.pubsService.fetchPubs().subscribe(
      //   data => {
      //     this.pubsList = data;
      //     console.log("lista pubs", this.pubsList);
      //     resolve();
      //   }
      // );
      this.pubsList = pubNames.map(pub => new Pub(pub, "adresa", "descriere"));
      resolve();
    });
  }

  ngOnDestroy() {
    if (this.view) {
      this.view.container = null;
    }
  }
}
