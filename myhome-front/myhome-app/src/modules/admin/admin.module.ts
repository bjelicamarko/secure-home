import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HomePageComponent } from './pages/home-page/home-page.component';
import { RouterModule } from '@angular/router';
import { AdminRoutes } from './admin.routes';
import { UsersViewComponent } from './pages/users-view/users-view.component';
import { AppUserCardComponent } from './components/app-user-card/app-user-card.component';

import { MaterialExampleModule } from 'src/material.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { SharedModule } from '../shared/shared.module';
import { RealEstateCreationPageComponent } from './pages/real-estate-creation-page/real-estate-creation-page.component';

@NgModule({
  declarations: [
    HomePageComponent,
    UsersViewComponent,
    AppUserCardComponent,
    RealEstateCreationPageComponent,
  ],
  imports: [
    CommonModule,
    SharedModule,
    RouterModule.forChild(AdminRoutes),
    MaterialExampleModule,
    FormsModule,
    ReactiveFormsModule
  ]
})
export class AdminModule { }
