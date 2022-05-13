import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SnackBarService } from './services/snack-bar.service';
import { UtilService } from './services/util/util.service';
import {MatSnackBarModule} from '@angular/material/snack-bar';
import { PaginationComponent } from './components/pagination/pagination.component';
import { HTTP_INTERCEPTORS } from '@angular/common/http';
import { Interceptor } from './interceptors/interceptor.interceptor';

@NgModule({
  declarations: [
    PaginationComponent
  ],
  imports: [
    CommonModule,
    MatSnackBarModule
  ],
  providers: [
    SnackBarService,
    UtilService,
    { provide: HTTP_INTERCEPTORS, useClass: Interceptor, multi: true },
  ]
})
export class SharedModule { }
