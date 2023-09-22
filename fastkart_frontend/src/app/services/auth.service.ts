import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { AuthRequest } from '../shared/models/AuthRequest';
import { UserInfo } from '../shared/models/UserInfo';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  
  isUserLoggedIn: boolean = false;
  constructor(private http: HttpClient) { }

  save(userInfo: UserInfo) {
    return this.http.post(environment.baseURL + `/auth/register`, userInfo);
  }

  login(authRequest: AuthRequest): Observable<any> {
    return this.http.post(environment.baseURL + `/auth/token`, authRequest);
  }

  canActivate() {
    if (sessionStorage.getItem('token')) {
      this.isUserLoggedIn = true;
    }
    return this.isUserLoggedIn;
  }
}
