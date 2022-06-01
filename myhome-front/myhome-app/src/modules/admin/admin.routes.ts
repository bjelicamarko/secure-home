import { Routes } from "@angular/router";
import { RoleGuard } from "../auth/guards/role/role.guard";
import { AssignEstatePageComponent } from "./pages/assign-estate-page/assign-estate-page.component";
import { LogsViewComponent } from "./pages/logs-view/logs-view.component";
import { RealEstateCreationPageComponent } from "./pages/real-estate-creation-page/real-estate-creation-page.component";
import { RealEstatePageComponent } from "./pages/real-estate-page/real-estate-page.component";
import { RealEstatesPageComponent } from "./pages/real-estates-page/real-estates-page.component";
import { UserRealEstatePageComponent } from "./pages/user-real-estate-page/user-real-estate-page.component";
import { UsersViewComponent } from "./pages/users-view/users-view.component";

export const AdminRoutes: Routes = [
  {
    path: "users-view",
    pathMatch: "full",
    component: UsersViewComponent,
    canActivate: [RoleGuard],
    data: { expectedRoles: "ROLE_ADMIN" },
  },
  {
    path: "create-real-estate",
    pathMatch: "full",
    component: RealEstateCreationPageComponent,
    canActivate: [RoleGuard],
    data: { expectedRoles: "ROLE_ADMIN" },
  },
  {
    path: "assign-real-estate/:username",
    pathMatch: "full",
    component: AssignEstatePageComponent,
    canActivate: [RoleGuard],
    data: { expectedRoles: "ROLE_ADMIN" },
  },
  {
    path: "user-real-estate/:username",
    pathMatch: "full",
    component: UserRealEstatePageComponent,
    canActivate: [RoleGuard],
    data: { expectedRoles: "ROLE_ADMIN" },
  },
  {
    path: "real-estates",
    pathMatch: "full",
    component: RealEstatesPageComponent,
    canActivate: [RoleGuard],
    data: { expectedRoles: "ROLE_ADMIN" },
  },
  {
    path: "real-estate/:name",
    pathMatch: "full",
    component: RealEstatePageComponent,
    canActivate: [RoleGuard],
    data: { expectedRoles: "ROLE_ADMIN" },
  },
  {
    path: "logs-view",
    pathMatch: "full",
    component: LogsViewComponent,
    canActivate: [RoleGuard],
    data: { expectedRoles: "ROLE_ADMIN" }
  }
];