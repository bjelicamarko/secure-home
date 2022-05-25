import { Component, Input } from '@angular/core';
import { RealEstateWithPhotoAndRoleDTO } from 'src/modules/shared/models/RealEstateDTO';
@Component({
  selector: 'app-real-estate-card',
  templateUrl: './real-estate-card.component.html',
  styleUrls: ['./real-estate-card.component.scss']
})
export class RealEstateCardComponent {

  @Input() realEstate: RealEstateWithPhotoAndRoleDTO = {
    name: '',
    photo: '',
    role: ''
  };

  constructor() { }

}
