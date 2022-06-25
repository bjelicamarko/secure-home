import { HttpClient, HttpHeaders, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { CsrDTO } from '../models/CsrDTO';

@Injectable({
  providedIn: 'root'
})
export class CsrService {

  private headers = new HttpHeaders({ "Content-Type": "application/json" });

  constructor(private http: HttpClient) { }

  getAllVerified(): Observable<HttpResponse<CsrDTO[]>> {
    let queryParams = {};

    queryParams = {
        headers: this.headers,
        observe: "response",
        responseType: "json"
    };

    return this.http.get<HttpResponse<CsrDTO[]>>("adminapp/api/csrs/verified", queryParams);
  }

  findOneById(id: number): Observable<HttpResponse<CsrDTO>> {
    let queryParams = {};

    queryParams = {
        headers: this.headers,
        observe: "response",
        responseType: "json"
    };

    return this.http.get<HttpResponse<CsrDTO>>("adminapp/api/csrs/" + id, queryParams);
  }

}