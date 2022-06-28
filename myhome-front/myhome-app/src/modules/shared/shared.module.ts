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
import { DateFormatPipe } from './pipes/date-format.pipe';
import { NotificationsPageComponent } from './components/notifications-page/notifications-page.component';
import { MatTableModule } from '@angular/material/table';
import { NotificationsContainerComponent } from './components/notifications-container/notifications-container.component';
import { NotificationCardComponent } from './components/notification-card/notification-card.component';

@NgModule({
  declarations: [
    PaginationComponent,
    ConformationDialogComponent,
    RealEstateCardComponent,
    DateFormatPipe,
    NotificationsPageComponent,
    NotificationsContainerComponent,
    NotificationCardComponent
  ],
  imports: [
    CommonModule,
    MatSnackBarModule,
    MatTableModule
  ],
  exports: [
    PaginationComponent,
    RealEstateCardComponent,
    DateFormatPipe,
    NotificationsPageComponent,
    NotificationsContainerComponent
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
