import { Injectable } from "@angular/core";
import { Router, ActivatedRouteSnapshot, CanActivate } from "@angular/router";
import { JwtHelperService } from "@auth0/angular-jwt";
import { AuthService } from "../../services/auth.service";

@Injectable({
  providedIn: "root",
})
export class RoleGuard implements CanActivate {
  constructor(public auth: AuthService, public router: Router) { }

  canActivate(route: ActivatedRouteSnapshot): boolean {
    const expectedRoles: string = route.data["expectedRoles"];
    const token = sessionStorage.getItem("user");
    const jwt: JwtHelperService = new JwtHelperService();

    if (!token) {
      this.router.navigate(["mh-app/auth/login"]);
      return false;
    }

    const info = jwt.decodeToken(token);
    const roles: string[] = expectedRoles.split("|");

    if (roles.indexOf(info.role) === -1) {
      //this.router.navigate(["/wine/list"]); oni su stavili redirect mi cemo samo ostati na stranici ako
      // nije validan role
      return false;
    }
    return true;
  }
}
