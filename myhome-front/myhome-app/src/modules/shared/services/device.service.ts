import { HttpHeaders, HttpClient, HttpResponse, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Device } from '../models/Device';
import { DeviceMessageDTO } from '../models/deviceMessageDTO';

@Injectable({
  providedIn: 'root'
})
export class DeviceService {

  private headers = new HttpHeaders({ "Content-Type": "application/json" });

  constructor(private http: HttpClient) { }

  getAllDevices(): Observable<HttpResponse<Device[]>> {
    let queryParams = {};

    queryParams = {
      headers: this.headers,
      observe: "response",
      responseType: "json"
    };

    return this.http.get<HttpResponse<Device[]>>("myhome/api/devices", queryParams);
  }

  getAllDeviceNames(): Observable<HttpResponse<String[]>> {
    let queryParams = {};

    queryParams = {
      headers: this.headers,
      observe: "response",
      responseType: "json"
    };

    return this.http.get<HttpResponse<String[]>>("myhome/api/devices/names", queryParams);
  }

  getAllMessagesFromDevice(deviceName: string, page: number, size: number): Observable<HttpResponse<DeviceMessageDTO[]>> {
    let queryParams = {};

    queryParams = {
      headers: this.headers,
      observe: "response",
      responseType: "json",
      params: new HttpParams()
        .set("page", String(page))
        .append("size", String(size))
    };

    return this.http.get<HttpResponse<DeviceMessageDTO[]>>("myhome/api/devices/getAllMessagesFromDevice/" + deviceName, queryParams);
  }

  filterMessages(deviceNameVal: string, startDateVal: string, endDateVal: string, selectedStatusVal: string,
    page: number, size: number): Observable<HttpResponse<DeviceMessageDTO[]>> {
    let queryParams = {};

      queryParams = {
        headers: this.headers,
        params: {
          deviceName: deviceNameVal,
          startDate: startDateVal,
          endDate: endDateVal,
          selectedStatus: selectedStatusVal,
          page: String(page),
          size: String(size)
        },
        observe: 'response'
      };

    return this.http.get<HttpResponse<DeviceMessageDTO[]>>("myhome/api/devices/filterMessages" , queryParams);
  }

  createReport(deviceNameVal: string, startDateVal: string, endDateVal: string, selectedStatusVal: string) 
    : Observable<HttpResponse<string>> {
      let queryParams = {};

      queryParams = {
        headers: this.headers,
        params: {
          deviceName: deviceNameVal,
          startDate: startDateVal,
          endDate: endDateVal,
          selectedStatus: selectedStatusVal,
        },
        observe: 'response',
        responseType: "text"
      };

    return this.http.get<HttpResponse<string>>("myhome/api/devices/createReport" , queryParams);
  }
  
}