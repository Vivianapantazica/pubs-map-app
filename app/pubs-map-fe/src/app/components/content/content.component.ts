import { Component, OnInit, Output, EventEmitter } from '@angular/core';
import { LocalStorageService } from 'src/app/services/local-storage.service';

@Component({
  selector: 'app-content',
  templateUrl: './content.component.html',
  styleUrls: ['./content.component.css']
})
export class ContentComponent implements OnInit {

  @Output() logoutEvent = new EventEmitter<void>();

  constructor() { }

  ngOnInit(): void {
  }

  handleLogout() {
    this.logoutEvent.emit();
  }
}
