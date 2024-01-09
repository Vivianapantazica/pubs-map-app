import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { AuthRequest } from '../models/AuthRequest';
import { LocalStorageService } from './local-storage.service';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  baseUrl = 'http://localhost:8090/api/v1/auth';

  constructor(private http: HttpClient, private localStorageService: LocalStorageService) { }

  register(authRequest: AuthRequest): void {
    const url = `${this.baseUrl}/register`; // Replace 'endpoint' with your API endpoint
    this.http.post<any>(url, authRequest).subscribe(
      (response) => {
        this.localStorageService.setItem('token', response['token']);
      }
    );
  }

  login(authRequest: AuthRequest): void {
    const url = `${this.baseUrl}/login`; // Replace 'endpoint' with your API endpoint
    this.http.post<any>(url, authRequest).subscribe(
      (response) => {
        this.localStorageService.setItem('token', response.body['token']);
      }
    );
  }

  logout(): void {
    this.localStorageService.removeItem('token');
  }
}
