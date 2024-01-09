import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { AuthRequest } from '../models/AuthRequest';
import { LocalStorageService } from './local-storage.service';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  baseUrl = 'http://localhost:8090/api/v1/auth';

  constructor(private http: HttpClient, private localStorageService: LocalStorageService) { }

  register(authRequest: AuthRequest): Observable<any> {
    const url = `${this.baseUrl}/register`; // Replace 'endpoint' with your API endpoint
    return this.http.post<any>(url, authRequest);
  }

  login(authRequest: AuthRequest): Observable<any> {
    const url = `${this.baseUrl}/authenticate`; // Replace 'endpoint' with your API endpoint
    return this.http.post<any>(url, authRequest);
  }

  logout(): void {
    this.localStorageService.removeItem('token');
  }
}
