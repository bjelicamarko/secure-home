import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SnackBarService } from './services/snack-bar.service';
import { UtilService } from './services/util/util.service';
import {MatSnackBarModule} from '@angular/material/snack-bar';
import { PaginationComponent } from './components/pagination/pagination.component';
import { HTTP_INTERCEPTORS } from '@angular/common/http';
import { Interceptor } from './interceptors/interceptor.interceptor';
import { ConformationDialogComponent } from './components/conformation-dialog/conformation-dialog.component';
import { DeviceService } from './services/device.service';
import { RealEstateCardComponent } from './components/real-estate-card/real-estate-card.component';
import { SharedDatePickerService } from './services/shared-data-picker.service';

@NgModule({
  declarations: [
    PaginationComponent,
    ConformationDialogComponent,
    RealEstateCardComponent
  ],
  imports: [
    CommonModule,
    MatSnackBarModule
  ],
  exports: [
    PaginationComponent,
    RealEstateCardComponent
  ],
  providers: [
    SnackBarService,
    UtilService,
    DeviceService,
    SharedDatePickerService,
    { provide: HTTP_INTERCEPTORS, useClass: Interceptor, multi: true },
  ]
})
export class SharedModule { }
