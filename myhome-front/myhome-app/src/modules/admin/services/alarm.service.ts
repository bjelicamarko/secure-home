import { HttpHeaders, HttpClient, HttpResponse, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { AlarmRuleDTO, AlarmRuleExtendedDTO } from '../models/AlarmRuleDTO';

@Injectable({
  providedIn: 'root'
})
export class AlarmService {

  private headers = new HttpHeaders({ "Content-Type": "application/json" });

  constructor(private http: HttpClient) { }

  saveAlarmRule(alarmRuleDTO: AlarmRuleDTO): Observable<HttpResponse<string>> {
    let queryParams = {};

    queryParams = {
      headers: this.headers,
      observe: "response",
      responseType: "text"
    };
    return this.http.post<HttpResponse<string>>("myhome/api/alarmRules", alarmRuleDTO, queryParams);
  }

  getAllExistingAlarmRules(): Observable<HttpResponse<AlarmRuleExtendedDTO>> {
    let queryParams = {};

    queryParams = {
      headers: this.headers,
      observe: "response"
    };
    return this.http.get<HttpResponse<AlarmRuleExtendedDTO>>("myhome/api/alarmRules", queryParams);
  }
}
