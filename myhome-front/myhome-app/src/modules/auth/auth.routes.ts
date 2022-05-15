import { Routes } from "@angular/router";
import { LoginGuard } from "./guards/login/login.guard";
import { LoginComponent } from "./pages/login/login.component";
import { RegistrationComponent } from "./pages/registration/registration.component";

export const AuthRoutes: Routes = [
  {
    path: "login",
    pathMatch: "full",
    component: LoginComponent,
    canActivate: [LoginGuard],
  },
  {
    path: "registration",
    pathMatch: "full",
    component: RegistrationComponent,
    canActivate: [LoginGuard],
  },
];