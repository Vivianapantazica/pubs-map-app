import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class LocalStorageService {

  constructor() { }

  // Method to set a key-value pair in localStorage
  setItem(key: string, value: any): void {
    localStorage.setItem(key, JSON.stringify(value));
  }

  // Method to get a value from localStorage by its key
  getItem(key: string): any {
    const storedItem = localStorage.getItem(key);
    return storedItem ? JSON.parse(storedItem) : null;
  }

  // Method to delete a value from localStorage by its key
  removeItem(key: string): void {
    localStorage.removeItem(key);
  }
}
