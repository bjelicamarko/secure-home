import { HttpHeaders, HttpClient, HttpResponse, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { RealEstateDTO, RealEstateWithDevicesDTO, RealEstateWithHouseholdAndDevicesDTO, RealEstateWithPhotoAndRoleDTO } from '../../shared/models/RealEstateDTO';

@Injectable({
  providedIn: 'root'
})
export class RealEstateService {

  private headers = new HttpHeaders({ "Content-Type": "application/json" });

  constructor(private http: HttpClient) { }

  createRealEstate(realEstateDTO: RealEstateWithDevicesDTO): Observable<HttpResponse<string>> {
    let queryParams = {};

    queryParams = {
      headers: this.headers,
      observe: "response",
      responseType: "text"
    };

    return this.http.post<HttpResponse<string>>("myhome/api/realEstates", realEstateDTO, queryParams);
  }

  updateRealEstate(realEstateDTO: RealEstateWithDevicesDTO): Observable<HttpResponse<string>> {
    let queryParams = {};

    queryParams = {
      headers: this.headers,
      observe: "response",
      responseType: "text"
    };

    return this.http.put<HttpResponse<string>>("myhome/api/realEstates", realEstateDTO, queryParams);
  }

  getRealEstateForUserToAssign(username: string): Observable<HttpResponse<RealEstateDTO[]>> {
    let queryParams = {};

    queryParams = {
      headers: this.headers,
      observe: "response",
      responseType: "json"
    };

    return this.http.get<HttpResponse<RealEstateDTO[]>>("myhome/api/realEstates/toAssign?username=" + username, queryParams);
  }

  getAllRealEstatesOfUser(page: number, size: number): Observable<HttpResponse<RealEstateWithPhotoAndRoleDTO[]>> {

    let queryParams = {};

    queryParams = {
      headers: this.headers,
      observe: "response",
      params: new HttpParams()
        .set("page", String(page))
        .append("size", String(size))
        .append("sort", "id"),
    };
    return this.http.get<HttpResponse<RealEstateWithPhotoAndRoleDTO[]>>("myhome/api/realEstates/all", queryParams);
  }

  getRealEstateByName(name: string | null): Observable<HttpResponse<RealEstateWithHouseholdAndDevicesDTO>> {
    let queryParams = {};

    queryParams = {
      headers: this.headers,
      observe: "response",
      responseType: "json"
    };

    return this.http.get<HttpResponse<RealEstateWithHouseholdAndDevicesDTO>>("myhome/api/ownerships/" + name, queryParams);
  }

  getAllRealEstates(page: number, size: number): Observable<HttpResponse<RealEstateWithPhotoAndRoleDTO>> {
    let queryParams = {};

    queryParams = {
      headers: this.headers,
      observe: "response",
      responseType: "json",
      params: new HttpParams()
        .set("page", String(page))
        .append("size", String(size))
        .append("sort", "id"),
    };

    return this.http.get<HttpResponse<RealEstateWithPhotoAndRoleDTO>>("myhome/api/realEstates", queryParams);
  }

  findDevicesByRealEstateName(name: string): Observable<HttpResponse<string[]>> {
    let queryParams = {};

    queryParams = {
      headers: this.headers,
      observe: "response",
      responseType: "json"
    };

    return this.http.get<HttpResponse<string[]>>("myhome/api/realEstates/deviceNames/" + name, queryParams);
  }

}
