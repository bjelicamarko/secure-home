import { HttpHeaders, HttpClient, HttpResponse, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { RuleDTO } from '../models/RuleDTO';

@Injectable({
  providedIn: 'root'
})
export class AlarmService {

  private headers = new HttpHeaders({ "Content-Type": "application/json" });

  constructor(private http: HttpClient) { }

  saveRule(ruleDTO: RuleDTO): Observable<HttpResponse<string>> {
    let queryParams = {};

    queryParams = {
      headers: this.headers,
      observe: "response",
      responseType: "text"
    };
    return this.http.post<HttpResponse<string>>("myhome/api/rules", ruleDTO, queryParams);
  }

  getAllRules(): Observable<HttpResponse<RuleDTO>> {
    let queryParams = {};

    queryParams = {
      headers: this.headers,
      observe: "response"
    };
    return this.http.get<HttpResponse<RuleDTO>>("myhome/api/rules", queryParams);
  }

  deleteAlarmRule(id: number): Observable<HttpResponse<string>> {
    let queryParams = {};

    queryParams = {
      headers: this.headers,
      observe: "response",
      responseType: "text",
    };

    return this.http.delete<HttpResponse<string>>("myhome/api/rules/" + id, queryParams);
  }
}
