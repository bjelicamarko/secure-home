import { HttpHeaders, HttpClient, HttpResponse, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { AppUserDTO } from '../models/AppUserDTO';

@Injectable({
  providedIn: 'root'
})
export class AppUsersService {

  private headers = new HttpHeaders({ "Content-Type": "application/json" });

  constructor(private http: HttpClient) {}

  getAllUsersButAdmin(page: number, size: number): Observable<HttpResponse<AppUserDTO[]>> {
    let queryParams = {};

    queryParams = {
      headers: this.headers,
      observe: "response",
      params: new HttpParams()
        .set("page", String(page))
        .append("size", String(size))
        .append("sort", "id"),
    };

    return this.http.get<HttpResponse<AppUserDTO[]>>("myhome/api/users/getAllUsersButAdmin", queryParams);
  }

  searchUsers(searchFieldVal: string, userTypeVal: string, pageNum: number, pageSize: number): 
  Observable<HttpResponse<AppUserDTO[]>> {
    let queryParams = {};

      queryParams = {
        headers: this.headers,
        params: {
          searchField: searchFieldVal,
          userType: userTypeVal,
          size: pageSize,
          page: pageNum
        },
        observe: 'response'
      };
    return this.http.get<HttpResponse<AppUserDTO[]>>("myhome/api/users/searchUsers", queryParams);
  }
}
