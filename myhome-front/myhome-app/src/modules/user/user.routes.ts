import { Routes } from "@angular/router";
import { RoleGuard } from "../auth/guards/role/role.guard";
import { UserHomePageComponent } from "./pages/user-home-page/user-home-page.component";

export const UserRoutes: Routes = [
  {
    path: "user-home-page",
    pathMatch: "full",
    component: UserHomePageComponent,
    canActivate: [RoleGuard],
    data: { expectedRoles: "ROLE_TENANT|ROLE_OWNER|ROLE_UNASSIGNED" },
  },
];