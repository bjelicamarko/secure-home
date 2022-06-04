import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { RoleGuard } from '../auth/guards/role/role.guard';
import { NotFoundPageComponent } from './pages/not-found-page/not-found-page.component';
import { RootLayoutPageComponent } from './pages/root-layout-page/root-layout-page.component';
import { UnauthorizedPageComponent } from './pages/unauthorized-page/unauthorized-page.component';

const routes: Routes = [
  {
    path: "mh-app",
    component: RootLayoutPageComponent,
    children: [
      {
        path: "auth",
        loadChildren: () =>
          import("./../auth/auth.module").then((m) => m.AuthModule),
      },
      {
        path: "admin",
        loadChildren: () =>
          import("./../admin/admin.module").then((m) => m.AdminModule),
      },
      {
        path: "user",
        loadChildren: () =>
          import("./../user/user.module").then((m) => m.UserModule),
      }
    ]
  },
  {
    path: "mh-app/unauthorized",
    component: UnauthorizedPageComponent,
    pathMatch: "full"
  },
  {
    path: "",
    redirectTo: "mh-app/auth/login",
    pathMatch: "full",
  },
  {
    path: "**",
    component: NotFoundPageComponent
  },

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
