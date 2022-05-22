import { HttpHeaders, HttpClient, HttpResponse, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { RealEstateDTO, RealEstateWithHouseholdAndDevicesDTO, RealEstateWithPhotoAndRoleDTO } from '../../shared/models/RealEstateDTO';

@Injectable({
  providedIn: 'root'
})
export class RealEstateService {

  private headers = new HttpHeaders({ "Content-Type": "application/json" });

  constructor(private http: HttpClient) { }

  createRealEstate(realEstateDTO: RealEstateDTO): Observable<HttpResponse<string>> {
    let queryParams = {};

    queryParams = {
      headers: this.headers,
      observe: "response",
      responseType: "text"
    };

    return this.http.post<HttpResponse<string>>("myhome/api/realEstates", realEstateDTO, queryParams);
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

  getAllRealEstatesOfUser(username: string, page: number, size: number): Observable<HttpResponse<RealEstateWithPhotoAndRoleDTO[]>> {

    let queryParams = {};

    queryParams = {
      headers: this.headers,
      observe: "response",
      params: new HttpParams()
        .set("page", String(page))
        .append("size", String(size))
        .append("sort", "id"),
    };
    return this.http.get<HttpResponse<RealEstateWithPhotoAndRoleDTO[]>>("myhome/api/realEstates/all/" + username, queryParams);
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

}
