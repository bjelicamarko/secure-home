import { HttpClient, HttpHeaders, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { JwtHelperService } from '@auth0/angular-jwt';

@Injectable({
  providedIn: 'root'
})
export class UtilService {

  constructor(private http: HttpClient) { }

  public getNoPages(totalItems: number, pageSize: number): number {
    return Math.ceil(totalItems / pageSize);
  }

  public getLoggedUserRoles(): string[] {
    const item = sessionStorage.getItem("user");

    if (item) {
      const jwt: JwtHelperService = new JwtHelperService();
      return jwt.decodeToken(item).roles; // ['oasd','asdsad']
    }
    return [];
  }

  public isRoleInUserRoles(role: string): boolean {
    const item = sessionStorage.getItem("user");

    if (item) {
      const jwt: JwtHelperService = new JwtHelperService();
      const roles: string[] = jwt.decodeToken(item).roles;
      for (let i = 0; i < roles.length; i++) {
        if (role === roles[i])
          return true;
      }
    }
    return false;
  }

  public getLoggedUsername(): string {
    const item = sessionStorage.getItem("user");
    if (item) {
      const jwt: JwtHelperService = new JwtHelperService();
      return jwt.decodeToken(item).sub;
    }
    return "";
  }

}
