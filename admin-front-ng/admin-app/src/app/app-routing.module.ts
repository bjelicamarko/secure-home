import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './pages/login/login.component';
import { NotFoundPageComponent } from './pages/not-found-page/not-found-page.component';
import { RootLayoutPageComponent } from './pages/root-layout-page/root-layout-page.component';
import { UnauthorizedPageComponent } from './pages/unauthorized-page/unauthorized-page.component';

const routes: Routes = [
  {
    path: "adm-app",
    component: RootLayoutPageComponent,
    children: [
      {
        path: "login",
        component: LoginComponent,
        pathMatch: "full"
      },
    ]
  },
  {
    path: "adm-app/unauthorized",
    component: UnauthorizedPageComponent,
    pathMatch: "full"
  },
  {
    path: "",
    redirectTo: "adm-app/login",
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
