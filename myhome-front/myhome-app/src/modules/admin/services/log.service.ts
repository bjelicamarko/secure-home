import { HttpHeaders, HttpClient, HttpResponse, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { LogDTO } from '../models/LogDTO';

@Injectable({
  providedIn: 'root'
})
export class LogService {

  private headers = new HttpHeaders({ "Content-Type": "application/json" });

  constructor(private http: HttpClient) { }

  getAllLogs(page: number, size: number): Observable<HttpResponse<LogDTO[]>> {
    let queryParams = {};

    queryParams = {
      headers: this.headers,
      observe: "response",
      params: new HttpParams()
        .set("page", String(page))
        .append("size", String(size))
        .append("sort", "id"),
    };
    return this.http.get<HttpResponse<LogDTO[]>>("myhome/api/logs", queryParams);
  }

  filterLogs(startDate: string, endDate: string, selectedLevel: string, searchValue: string, page: number, size: number) {
    let queryParams = {};

    queryParams = {
      headers: this.headers,
      params: {
        startDate: startDate,
        endDate: endDate,
        selectedLevel: selectedLevel,
        searchValue: searchValue,
        page: String(page),
        size: String(size)
      },
      observe: 'response'
    };

    return this.http.get<HttpResponse<LogDTO[]>>("myhome/api/logs/filterLogs", queryParams);
  }
}

