import { Routes } from "@angular/router";
import { RoleGuard } from "../auth/guards/role/role.guard";
import { HomePageComponent } from "./pages/home-page/home-page.component";

export const AdminRoutes: Routes = [
  {
    path: "home-page",
    pathMatch: "full",
    component: HomePageComponent,
    canActivate: [RoleGuard],
    data: { expectedRoles: "ROLE_ADMIN" },
  },
];