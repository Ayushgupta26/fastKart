import { Injectable } from '@angular/core';

const TOKEN_KEY = 'token';
const USER_KEY = 'auth-user';

@Injectable({
  providedIn: 'root'
})
export class TokenStorageService {

  constructor() { }

  signOut() {
    window.sessionStorage.clear();
  }

  public saveToken(token: string) {
    window.sessionStorage.removeItem(TOKEN_KEY);
    window.sessionStorage.setItem(TOKEN_KEY, token);
  }

  public getToken(): string {
    return sessionStorage.getItem(TOKEN_KEY);
  }

  public saveUser(user: any) {
    window.sessionStorage.removeItem(USER_KEY);
    window.sessionStorage.setItem(USER_KEY, JSON.stringify(user));
  }

  public getUser(): any {
    const userString = sessionStorage.getItem(USER_KEY);
    return userString ? JSON.parse(userString) : null;
  }

  // Additional methods for retrieving specific user details

  public getUserRoles(): string[] {
    const user = this.getUser();
    if (user && user.roles) {
      return user.roles.map((role: any) => role.authority);
    } else {
      return [];
    }
  }

  public getUserId(): number {
    const user = this.getUser();
    return user && user.userId ? user.userId : 0;
  }

  public getUserName(): string {
    const user = this.getUser();
    return user && user.sub ? user.sub : '';
  }
}
