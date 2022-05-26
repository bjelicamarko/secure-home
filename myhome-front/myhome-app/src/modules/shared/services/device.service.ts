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

  getAllMessagesFromDevice(deviceName: string): Observable<HttpResponse<DeviceMessageDTO[]>> {
    let queryParams = {};

    queryParams = {
      headers: this.headers,
      observe: "response",
      responseType: "json"
    };

    return this.http.get<HttpResponse<DeviceMessageDTO[]>>("myhome/api/devices/getAllMessagesFromDevice/" + deviceName, queryParams);
  }

  filterMessages(startDateVal: string, endDateVal: string, selectedStatusVal: string): Observable<HttpResponse<DeviceMessageDTO[]>> {
    let queryParams = {};

      queryParams = {
        headers: this.headers,
        params: {
          startDate: startDateVal,
          endDate: endDateVal,
          selectedStatus: selectedStatusVal,

        },
        observe: 'response'
      };

    console.log(queryParams);
    return this.http.get<HttpResponse<DeviceMessageDTO[]>>("myhome/api/devices/filterMessages" , queryParams);
  }
}