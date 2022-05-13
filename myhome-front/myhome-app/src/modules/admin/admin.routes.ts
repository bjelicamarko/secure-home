import { Routes } from "@angular/router";
import { RoleGuard } from "../auth/guards/role/role.guard";
import { HomePageComponent } from "./pages/home-page/home-page.component";
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
    path: "home-page/users-view",
    pathMatch: "full",
    component: UsersViewComponent,
    canActivate: [RoleGuard],
    data: { expectedRoles: "ROLE_ADMIN" },
  }
];