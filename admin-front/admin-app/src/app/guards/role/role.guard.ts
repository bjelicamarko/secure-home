import { Injectable } from "@angular/core";
import { Router, ActivatedRouteSnapshot, CanActivate } from "@angular/router";
import { JwtHelperService } from "@auth0/angular-jwt";
import { AuthService } from "src/app/services/auth.service";
import { UtilService } from "src/app/services/util.service";

@Injectable({
  providedIn: "root",
})
export class RoleGuard implements CanActivate {
  constructor(public auth: AuthService, public router: Router, public utilService: UtilService) { }

  canActivate(route: ActivatedRouteSnapshot): boolean {
    const expectedRoles: string = route.data["expectedRoles"];
    const token = sessionStorage.getItem("user");
    const jwt: JwtHelperService = new JwtHelperService();

    if (!token) {
      this.router.navigate(["adm-app/login"]);
      return false;
    }

    // const info = jwt.decodeToken(token);
    const roles: string[] = expectedRoles.split("|");
    for (let i = 0; i < roles.length; i++) {
      if (this.utilService.isRoleInUserRoles(roles[i]))
        return true;
    }

    this.router.navigate(["adm-app/unauthorized"]);
    return false;
  }
}
