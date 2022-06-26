import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { RoleGuard } from './guards/role/role.guard';
import { AdminHomePageComponent } from './pages/admin-home-page/admin-home-page.component';
import { AllCertificatesViewComponent } from './pages/all-certificates-view/all-certificates-view.component';
import { CertificateSigningPageComponent } from './pages/certificate-signing-page/certificate-signing-page.component';
import { CsrFormPageComponent } from './pages/csr-form-page/csr-form-page.component';
import { LoginComponent } from './pages/login/login.component';
import { NotFoundPageComponent } from './pages/not-found-page/not-found-page.component';
import { RootLayoutPageComponent } from './pages/root-layout-page/root-layout-page.component';
import { UnauthorizedPageComponent } from './pages/unauthorized-page/unauthorized-page.component';
import { VerifyCsrPageComponent } from './pages/verify-csr-page/verify-csr-page.component';

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
      {
        path: "all-certificates-view",
        component: AllCertificatesViewComponent,
        canActivate: [RoleGuard],
        data: { expectedRoles: "ROLE_ADMIN" },
        pathMatch: "full"
      },
      {
        path: "homepage",
        component: AdminHomePageComponent,
        canActivate: [RoleGuard],
        data: { expectedRoles: "ROLE_ADMIN" },
        pathMatch: "full"
      },
      {
        path: "certificate-signing/:id",
        component: CertificateSigningPageComponent,
        canActivate: [RoleGuard],
        data: { expectedRoles: "ROLE_ADMIN" },
        pathMatch: "full"
      },
      {
        path: "csr-form",
        component: CsrFormPageComponent,
        pathMatch: "full"
      },
      {
        path: "verify-csr",
        component: VerifyCsrPageComponent,
        pathMatch: "full"
      }
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
