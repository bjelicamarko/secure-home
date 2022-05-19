import { Routes } from "@angular/router";
import { RoleGuard } from "../auth/guards/role/role.guard";
import { AssignEstatePageComponent } from "./pages/assign-estate-page/assign-estate-page.component";
import { HomePageComponent } from "./pages/home-page/home-page.component";
import { RealEstateCreationPageComponent } from "./pages/real-estate-creation-page/real-estate-creation-page.component";
import { UsersViewComponent } from "./pages/users-view/users-view.component";

export const AdminRoutes: Routes = [
  {
    path: "home-page",
    pathMatch: "full",
    component: HomePageComponent,
    canActivate: [RoleGuard],
    data: { expectedRoles: "ROLE_ADMIN" },
  },
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
  }
];