import { HttpHeaders, HttpClient, HttpResponse, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { UserRealEstateDTO, UserRealEstateToViewDTO } from '../models/UserRealEstateDTO';

@Injectable({
  providedIn: 'root'
})
export class UserRealEstateService {

  private headers = new HttpHeaders({ "Content-Type": "application/json" });

  constructor(private http: HttpClient) {}

  saveUserRealEstate(userRealEstateDTO: UserRealEstateDTO): Observable<HttpResponse<string>> {
    let queryParams = {};

    queryParams = {
      headers: this.headers,
      observe: "response",
      responseType: "text"
    };

    return this.http.post<HttpResponse<string>>("myhome/api/ownerships", userRealEstateDTO, queryParams);
  }

  getUserRealEstatesFromUser(username: string): Observable<HttpResponse<UserRealEstateToViewDTO[]>> {
    let queryParams = {};

    queryParams = {
      headers: this.headers,
      observe: "response",
      responseType: "json"
    };

    return this.http.get<HttpResponse<UserRealEstateToViewDTO[]>>("myhome/api/ownerships/fromUser?username=" + username, queryParams);
  }

  changeRoleInUserRealEstate(userRealEstateDTO: UserRealEstateDTO): Observable<HttpResponse<string>> {
    let queryParams = {};

    queryParams = {
      headers: this.headers,
      observe: "response",
      responseType: "text"
    };

    return this.http.put<HttpResponse<string>>("myhome/api/ownerships", userRealEstateDTO, queryParams);
  }

}
