import { Component, OnInit, Output, EventEmitter} from '@angular/core';
import { AuthRequest } from 'src/app/models/AuthRequest';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  @Output() loginEvent = new EventEmitter<AuthRequest>();

  constructor() { }

  ngOnInit(): void {
  }

  emitLoginEvent() {
    const username = (<HTMLInputElement> document.getElementById("username")).value;
    const password = (<HTMLInputElement> document.getElementById("password")).value;
    this.loginEvent.emit(new AuthRequest(username, password));
  }

}
