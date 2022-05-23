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
import { AssignEstatePageComponent } from './pages/assign-estate-page/assign-estate-page.component';
import { UserRealEstatePageComponent } from './pages/user-real-estate-page/user-real-estate-page.component';

@NgModule({
  declarations: [
    HomePageComponent,
    UsersViewComponent,
    AppUserCardComponent,
    RealEstateCreationPageComponent,
    AssignEstatePageComponent,
    UserRealEstatePageComponent,
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
