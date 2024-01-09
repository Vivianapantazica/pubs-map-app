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

  login = false;
  register = true;

  handleRegisterEvent(authRequest: AuthRequest) {
    this.authService.register(authRequest);
    this.login = false;
    this.register = false;
    console.log("dupa register", this.login, this.register);
  }

  handleLogoutEvent() {
    this.authService.logout();
    this.login = true;
    this.register = false;
    console.log("dupa logout", this.login, this.register);
  }
}
