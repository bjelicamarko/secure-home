import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { UserHomePageComponent } from './pages/user-home-page/user-home-page.component';
import { UserRoutes } from './user.routes';
import { RouterModule } from '@angular/router';
import { SharedModule } from '../shared/shared.module';
import { RealEstatePageComponent } from './pages/real-estate-page/real-estate-page.component';
import { DeviceCardComponent } from './components/device-card/device-card.component';

@NgModule({
  declarations: [
    UserHomePageComponent,
    RealEstatePageComponent,
    DeviceCardComponent
  ],
  imports: [
    CommonModule,
    RouterModule.forChild(UserRoutes),
    SharedModule
  ]
})
export class UserModule { }
