import { Injectable } from "@angular/core";
import { Router, CanActivate } from "@angular/router";
import { UtilService } from "src/modules/shared/services/util/util.service";
import { AuthService } from "../../services/auth.service";

@Injectable({
  providedIn: "root",
})
export class LoginGuard implements CanActivate {
  constructor(public auth: AuthService, public router: Router, public utilsService: UtilService) { }

  canActivate(): boolean {
    if (this.auth.isLoggedIn()) {
      let role = this.utilsService.getLoggedUserRole();

      if (role === "ROLE_ADMIN") {
        this.router.navigate(["mh-app"]);
      }
      else if (role === "ROLE_OWNER") {
        this.router.navigate(["mh-app"]);
      }
      else if (role === "ROLE_TENANT") {
        this.router.navigate(["mh-app"]);
      }
      return false;
    }
    return true;
  }
}
