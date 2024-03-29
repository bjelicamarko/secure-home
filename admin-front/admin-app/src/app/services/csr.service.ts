import { HttpClient, HttpHeaders, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { CSRDTO } from '../models/CSRDTO';

@Injectable({
  providedIn: 'root'
})
export class CsrService {

  private headers = new HttpHeaders({ "Content-Type": "application/json" });

  constructor(private http: HttpClient) { }

  getAllVerified(): Observable<HttpResponse<CSRDTO[]>> {
    let queryParams = {};

    queryParams = {
        headers: this.headers,
        observe: "response",
        responseType: "json"
    };

    return this.http.get<HttpResponse<CSRDTO[]>>("adminapp/api/csrs/verified", queryParams);
  }

  findOneById(id: number): Observable<HttpResponse<CSRDTO>> {
    let queryParams = {};

    queryParams = {
        headers: this.headers,
        observe: "response",
        responseType: "json"
    };

    return this.http.get<HttpResponse<CSRDTO>>("adminapp/api/csrs/" + id, queryParams);
  }

}