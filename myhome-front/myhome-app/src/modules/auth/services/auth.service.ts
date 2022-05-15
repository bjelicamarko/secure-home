import { Injectable } from "@angular/core";
import { HttpHeaders, HttpClient, HttpResponse } from "@angular/common/http";
import { Observable } from "rxjs";
import { Login } from "src/modules/shared/models/login";
import { Token } from "src/modules/shared/models/token";
import { RegistrationDTO } from "../models/RegistrationDTO";

@Injectable({
  providedIn: "root",
})
export class AuthService {
  private headers = new HttpHeaders({ "Content-Type": "application/json" });

  constructor(private http: HttpClient) {}

  login(auth: Login): Observable<Token> {
    return this.http.post<Token>("myhome/api/users/login", auth, {
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

    return this.http.post<HttpResponse<string>>("myhome/api/users/register", RegistrationDTO, queryParams);
  }

  logout(): void {
    localStorage.removeItem("user");
  }

  isLoggedIn(): boolean {
    if (!localStorage.getItem("user")) {
      return false;
    }
    return true;
  }
}
