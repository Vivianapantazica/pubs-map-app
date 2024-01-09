import { Component, OnInit, Output, EventEmitter } from '@angular/core';
import { AuthRequest } from 'src/app/models/AuthRequest';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css'],
})
export class RegisterComponent implements OnInit {

  @Output() registerEvent = new EventEmitter<AuthRequest>();

  constructor() { }

  ngOnInit(): void {
  }

  emitRegisterEvent() {
    const username = (<HTMLInputElement> document.getElementById("username")).value;
    const password = (<HTMLInputElement> document.getElementById("password")).value;
    this.registerEvent.emit(new AuthRequest(username, password));
  }

}
