import { Injectable } from "@angular/core";
import { Router, CanActivate } from "@angular/router";
import { UtilService } from "src/modules/shared/services/util/util.service";
import { AuthService } from "../../services/auth.service";

@Injectable({
  providedIn: "root",
})
export class LoginGuard implements CanActivate {
  constructor(public auth: AuthService, public router: Router, public utilService: UtilService) { }

  canActivate(): boolean {
    if (this.auth.isLoggedIn()) {
      // let role = this.utilsService.getLoggedUserRole();

      if (this.utilService.isRoleInUserRoles("ROLE_ADMIN")) {
        this.router.navigate(["mh-app/admin/users-view"]);
      }
      else if (this.utilService.isRoleInUserRoles("ROLE_OWNER")) {
        this.router.navigate(["mh-app/user/user-home-page"]);
      }
      else if (this.utilService.isRoleInUserRoles("ROLE_TENANT")) {
        this.router.navigate(["mh-app/user/user-home-page"]);
      }
      else if (this.utilService.isRoleInUserRoles("ROLE_UNASSIGNED")) {
        this.router.navigate(["mh-app/user/user-home-page"]);
      }

      // if (role === "ROLE_ADMIN") {
      //   this.router.navigate(["mh-app"]);
      // }
      // else if (role === "ROLE_OWNER") {
      //   this.router.navigate(["mh-app"]);
      // }
      // else if (role === "ROLE_TENANT") {
      //   this.router.navigate(["mh-app"]);
      // }
      return false;
    }
    return true;
  }
}
