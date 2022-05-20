import { Injectable } from "@angular/core";
import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor,
} from "@angular/common/http";
import { Observable } from "rxjs";

@Injectable()
export class Interceptor implements HttpInterceptor {
  intercept(
    request: HttpRequest<any>,
    next: HttpHandler
  ): Observable<HttpEvent<any>> {
    const item = localStorage.getItem("user");
    if (item) {
      request = request.clone({
        setHeaders: {
          Authorization: `Bearer ${JSON.parse(item).accessToken}`
        },
        withCredentials: true
      });
    }
    return next.handle(request);
  }
}