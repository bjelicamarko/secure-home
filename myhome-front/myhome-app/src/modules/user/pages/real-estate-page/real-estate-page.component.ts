import { AfterViewInit, Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { retry } from 'rxjs';
import { RealEstateService } from 'src/modules/admin/services/real-estate.service';
import { RealEstateWithHouseholdAndDevicesDTO } from 'src/modules/shared/models/RealEstateDTO';

@Component({
  selector: 'app-real-estate-page',
  templateUrl: './real-estate-page.component.html',
  styleUrls: ['./real-estate-page.component.scss']
})
export class RealEstatePageComponent implements AfterViewInit {

  name: string | null;
  realEstate: RealEstateWithHouseholdAndDevicesDTO;

  constructor(private route: ActivatedRoute, private realEstateService: RealEstateService) {
    this.name = route.snapshot.paramMap.get("name");
    this.realEstate = { household: [], devices: [] }
  }

  ngAfterViewInit(): void {
    if (!this.name) return;

    this.name = decodeURIComponent(this.name);
    this.realEstateService.getRealEstateByName(this.name)
      .subscribe((response) => {
        this.realEstate = response.body as RealEstateWithHouseholdAndDevicesDTO;
      });
  }

  randomNumber() {
    return Math.round(Math.random() * (25))
  }

}
