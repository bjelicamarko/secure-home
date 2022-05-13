import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HomePageComponent } from './pages/home-page/home-page.component';
import { RouterModule } from '@angular/router';
import { AdminRoutes } from './admin.routes';
import { UsersViewComponent } from './pages/users-view/users-view.component';
import { AppUserCardComponent } from './components/app-user-card/app-user-card.component';

@NgModule({
  declarations: [
    HomePageComponent,
    UsersViewComponent,
    AppUserCardComponent
  ],
  imports: [
    CommonModule,
    RouterModule.forChild(AdminRoutes),
  ]
})
export class AdminModule { }
