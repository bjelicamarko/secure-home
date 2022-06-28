import { Component, EventEmitter, Input, Output } from '@angular/core';
import { DeviceDTO } from '../../models/deviceDTO';

@Component({
  selector: 'app-device-card',
  templateUrl: './device-card.component.html',
  styleUrls: ['./device-card.component.scss']
})
export class DeviceCardComponent {

  @Input() device: DeviceDTO = {
    name: '',
    photo: ''
  };

  @Output() clickOnDevice = new EventEmitter<string>();

  constructor() { }

  clickOnDeviceCard(name: string) {
    this.clickOnDevice.emit(name);
  }
}
