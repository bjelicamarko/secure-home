import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';
import { NotFoundPageComponent } from './pages/not-found-page/not-found-page.component';
import { RootLayoutPageComponent } from './pages/root-layout-page/root-layout-page.component';
import { UnauthorizedPageComponent } from './pages/unauthorized-page/unauthorized-page.component';
import { HeaderAdminComponent } from './components/header-admin/header-admin.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { LoginComponent } from './pages/login/login.component';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { Interceptor } from './interceptors/interceptor.interceptor';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { HeaderUnivComponent } from './components/header-univ/header-univ.component';
import { AllCertificatesViewComponent } from './pages/all-certificates-view/all-certificates-view.component';
import { CertificateDialogComponent } from './components/certificate-dialog/certificate-dialog.component';
import { MaterialExampleModule } from 'src/material.module';
import { CertificateInfoDialogComponent } from './components/certificate-info-dialog/certificate-info-dialog.component';
import { InvalidateCertificateDialogComponent } from './components/invalidate-certificate-dialog/invalidate-certificate-dialog.component';
import { AdminHomePageComponent } from './pages/admin-home-page/admin-home-page.component';
import { CsrViewerComponent } from './components/csr-viewer/csr-viewer.component';
import { CertificateSigningPageComponent } from './pages/certificate-signing-page/certificate-signing-page.component';
import { CertificateFormComponent } from './components/certificate-form/certificate-form.component';
import { KeyUsageComponent } from './components/key-usage/key-usage.component';
import { ExtendedKeyUsageComponent } from './components/extended-key-usage/extended-key-usage.component';
import { CsrFormPageComponent } from './pages/csr-form-page/csr-form-page.component';
import { VerifyCsrPageComponent } from './pages/verify-csr-page/verify-csr-page.component';

@NgModule({
  declarations: [
    AppComponent,
    NotFoundPageComponent,
    RootLayoutPageComponent,
    UnauthorizedPageComponent,
    HeaderAdminComponent,
    LoginComponent,
    HeaderUnivComponent,
    AllCertificatesViewComponent,
    CertificateDialogComponent,
    CertificateInfoDialogComponent,
    InvalidateCertificateDialogComponent,
    AdminHomePageComponent,
    CsrViewerComponent,
    CertificateSigningPageComponent,
    CertificateFormComponent,
    KeyUsageComponent,
    ExtendedKeyUsageComponent,
    CsrFormPageComponent,
    VerifyCsrPageComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    NoopAnimationsModule,
    HttpClientModule,
    MaterialExampleModule,
    FormsModule,
    ReactiveFormsModule,
    MatSnackBarModule,
  ],
  providers: [
    { provide: HTTP_INTERCEPTORS, useClass: Interceptor, multi: true },

  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
