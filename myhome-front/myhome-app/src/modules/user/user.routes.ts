import { Routes } from "@angular/router";
import { RoleGuard } from "../auth/guards/role/role.guard";
import { RealEstatePageComponent } from "./pages/real-estate-page/real-estate-page.component";
import { UserHomePageComponent } from "./pages/user-home-page/user-home-page.component";

export const UserRoutes: Routes = [
  {
    path: "user-home-page",
    pathMatch: "full",
    component: UserHomePageComponent,
    canActivate: [RoleGuard],
    data: { expectedRoles: "ROLE_TENANT|ROLE_OWNER|ROLE_UNASSIGNED" },
  },
  {
    path: "real-estate-page/:name",
    pathMatch: "full",
    component: RealEstatePageComponent,
    canActivate: [RoleGuard],
    data: { expectedRoles: "ROLE_TENANT|ROLE_OWNER" },
  }
];