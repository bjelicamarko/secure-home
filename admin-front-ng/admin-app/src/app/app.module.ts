import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';
import { NotFoundPageComponent } from './pages/not-found-page/not-found-page.component';
import { RootLayoutPageComponent } from './pages/root-layout-page/root-layout-page.component';
import { UnauthorizedPageComponent } from './pages/unauthorized-page/unauthorized-page.component';
import { HeaderAdminComponent } from './components/header-admin/header-admin.component';
import { HeaderCommonComponent } from './components/header-common/header-common.component';
import { ReactiveFormsModule } from '@angular/forms';
import { LoginComponent } from './pages/login/login.component';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { Interceptor } from './interceptors/interceptor.interceptor';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { HeaderUnivComponent } from './components/header-univ/header-univ.component';

@NgModule({
  declarations: [
    AppComponent,
    NotFoundPageComponent,
    RootLayoutPageComponent,
    UnauthorizedPageComponent,
    HeaderAdminComponent,
    HeaderCommonComponent,
    LoginComponent,
    HeaderUnivComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    NoopAnimationsModule,
    ReactiveFormsModule,
    HttpClientModule,
    MatSnackBarModule,
  ],
  providers: [
    { provide: HTTP_INTERCEPTORS, useClass: Interceptor, multi: true },

  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
