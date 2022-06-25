import { HttpClient, HttpHeaders, HttpResponse } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { CertificateDTO } from "../models/CertificateDTO";
import { CertificateSigningDTO } from "../models/CertificateSigningDTO";
import { RevokedCertificateDTO } from "../models/RevokedCertificateDTO";

@Injectable({
    providedIn: "root",
})
export class CertificateService {

    private headers = new HttpHeaders({ "Content-Type": "application/json" });

    constructor(private http: HttpClient) { }

    getAliases(): Observable<HttpResponse<string[]>> {
        let queryParams = {};

        queryParams = {
            headers: this.headers,
            observe: "response",
            responseType: "json"
        };

        return this.http.get<HttpResponse<string[]>>
            ("adminapp/api/certificates/getAliases", queryParams);
    }

    getCertificate(alias: string): Observable<HttpResponse<CertificateDTO[]>> {
        let queryParams = {};

        queryParams = {
            headers: this.headers,
            observe: "response",
            responseType: "json"
        };

        return this.http.get<HttpResponse<CertificateDTO[]>>
            ("adminapp/api/certificates/getCertificate/" + alias, queryParams);
    }

    invalidateCertificate(certificateDTO: RevokedCertificateDTO): Observable<HttpResponse<string>> {
        let queryParams = {};

        queryParams = {
            headers: this.headers,
            observe: "response",
            responseType: "text"
        };

        return this.http.post<HttpResponse<string>>
            ("adminapp/api/certificates/revoke", certificateDTO, queryParams);
    }

    checkCertificate(alias: string): Observable<HttpResponse<string>> {
        let queryParams = {};

        queryParams = {
            headers: this.headers,
            observe: "response",
            responseType: "text"
        };

        return this.http.post<HttpResponse<string>>("adminapp/api/certificates/validate", alias, queryParams);
    }

    save(certificateSigningDTO: CertificateSigningDTO): Observable<HttpResponse<string>> {
        let queryParams = {};

        queryParams = {
            headers: this.headers,
            observe: "response",
            responseType: "text"
        };

        return this.http.post<HttpResponse<string>>
            ("adminapp/api/certificates", certificateSigningDTO, queryParams);
    }
}