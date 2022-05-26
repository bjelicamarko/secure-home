import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { UserHomePageComponent } from './pages/user-home-page/user-home-page.component';
import { UserRoutes } from './user.routes';
import { RouterModule } from '@angular/router';
import { SharedModule } from '../shared/shared.module';
import { RealEstatePageComponent } from './pages/real-estate-page/real-estate-page.component';
import { DeviceCardComponent } from './components/device-card/device-card.component';
import { DeviceMessagesPageComponent } from './pages/device-messages-page/device-messages-page.component';

import { MaterialExampleModule } from 'src/material.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

@NgModule({
  declarations: [
    UserHomePageComponent,
    RealEstatePageComponent,
    DeviceCardComponent,
    DeviceMessagesPageComponent
  ],
  imports: [
    CommonModule,
    RouterModule.forChild(UserRoutes),
    MaterialExampleModule,
    FormsModule,
    ReactiveFormsModule,
    SharedModule
  ]
})
export class UserModule { }
