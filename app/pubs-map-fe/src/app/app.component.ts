import { AuthRequest } from './models/AuthRequest';
import { Component } from '@angular/core';
import { AuthService } from './services/auth.service';
import { LocalStorageService } from './services/local-storage.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'pubs-map-fe';

  constructor(private authService: AuthService, private localStorageService: LocalStorageService) {}

  ngOnInit() {
    // save session even after refreshing the page
    if (localStorage.getItem('token') != null) {
      this.login = false;
      this.register = false;
    }
  }

  login = true;
  register = false;

  handleRegisterEvent(authRequest: AuthRequest) {
    this.authService.register(authRequest).subscribe(
      (response) => {
        this.localStorageService.setItem('token', response['token']);
        this.login = false;
        this.register = false;
      }, (error) => {
        console.log(error);
      }
    );
  }

  handleSwitchToLoginEvent() {
    this.login = true;
    this.register = false;
  }

  handleSwitchToRegisterEvent() {
    this.register = true;
    this.login = false;
  }

  handleLogoutEvent() {
    this.authService.logout();
    this.login = true;
    this.register = false;
  }

  handleLoginEvent(authRequest: AuthRequest) {
    this.authService.login(authRequest).subscribe(
      (response) => {
        this.localStorageService.setItem('token', response['token']);
        this.login = false;
        this.register = false;
      }, (error) => {
        console.log(error);
      }
    );
  }
}
