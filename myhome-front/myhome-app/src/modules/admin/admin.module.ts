import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
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
import { RealEstatesPageComponent } from './pages/real-estates-page/real-estates-page.component';
import { RealEstatePageComponent } from './pages/real-estate-page/real-estate-page.component';
import { LogsViewComponent } from './pages/logs-view/logs-view.component';
import { StackTraceComponent } from './components/stack-trace/stack-trace.component';
import { AlarmsViewComponent } from './pages/alarms-view/alarms-view.component';

@NgModule({
  declarations: [
    UsersViewComponent,
    AppUserCardComponent,
    RealEstateCreationPageComponent,
    AssignEstatePageComponent,
    UserRealEstatePageComponent,
    RealEstatesPageComponent,
    RealEstatePageComponent,
    LogsViewComponent,
    StackTraceComponent,
    AlarmsViewComponent,
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
