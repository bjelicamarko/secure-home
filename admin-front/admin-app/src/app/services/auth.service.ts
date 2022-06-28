import { Injectable } from "@angular/core";
import { HttpHeaders, HttpClient, HttpResponse } from "@angular/common/http";
import { Observable } from "rxjs";
import { Login } from "../models/login";
import { Token } from "../models/token";
import { RegistrationDTO } from "../models/RegistrationDTO";

@Injectable({
  providedIn: "root",
})
export class AuthService {
  private headers = new HttpHeaders({ "Content-Type": "application/json" });

  constructor(private http: HttpClient) { }

  login(auth: Login): Observable<Token> {
    return this.http.post<Token>("adminapp/api/users/login", auth, {
      headers: this.headers,
      responseType: "json",
    });
  }

  register(RegistrationDTO: RegistrationDTO): Observable<HttpResponse<string>> {
    let queryParams = {};

    queryParams = {
      headers: this.headers,
      observe: "response",
      responseType: "text"
    };

    return this.http.post<HttpResponse<string>>("adminapp/api/users/register", RegistrationDTO, queryParams);
  }

  logout(): Observable<String> {
    return this.http.post<String>("adminapp/api/users/logout", {
      headers: this.headers,
      responseType: "json",
    });
  }

  isLoggedIn(): boolean {
    if (!sessionStorage.getItem("user")) {
      return false;
    }
    return true;
  }
}
