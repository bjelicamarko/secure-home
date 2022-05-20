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

  public getLoggedUserRole(): string {
    const item = sessionStorage.getItem("user");

    if (item) {
      const jwt: JwtHelperService = new JwtHelperService();
      return jwt.decodeToken(item).role;
    }
    return "";
  }

}
