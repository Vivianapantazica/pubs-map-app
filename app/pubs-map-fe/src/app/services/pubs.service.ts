import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders, HttpParams} from '@angular/common/http';
import { AuthRequest } from '../models/AuthRequest';
import { LocalStorageService } from './local-storage.service';
import { Observable } from 'rxjs';
import {Pub} from "../models/Pub";
import {Client} from "../models/Client";

@Injectable({
  providedIn: 'root'
})
export class PubsService {

  baseUrl = 'http://localhost:8080/api/v1/pubs';
  // baseUrl1 = 'http://localhost:8080/api/v1/clients';

  constructor(private http: HttpClient, private localStorageService: LocalStorageService) { }

  fetchPubs(): Observable<Pub[]> {
    const headers = new HttpHeaders({
      'Authorization': 'Bearer ' + this.localStorageService.getItem('token')
    });

// Opțiunile pentru apelul HTTP, inclusiv headerele
    const options = {
      headers: headers
    };
    return this.http.get<Pub[]>(this.baseUrl, options);
  }
  // fetchClients(): Observable<Client[]> {
  //   return this.http.get<Client[]>(this.baseUrl1);
  // }
}
