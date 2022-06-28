import { Injectable } from "@angular/core";
import { Router, CanActivate } from "@angular/router";
import { AuthService } from "src/app/services/auth.service";
import { UtilService } from "src/app/services/util.service"; 

@Injectable({
  providedIn: "root",
})
export class LoginGuard implements CanActivate {
  constructor(public auth: AuthService, public router: Router, public utilService: UtilService) { }

  canActivate(): boolean {
    if (this.auth.isLoggedIn()) {
      // let role = this.utilsService.getLoggedUserRole();

      if (this.utilService.isRoleInUserRoles("ROLE_ADMIN")) {
        // this.router.navigate(["adm-app/neka_putanja"]); REDIREKT NA POCETNU ZA ADMINA
      }

      return false;
    }
    return true;
  }
}
