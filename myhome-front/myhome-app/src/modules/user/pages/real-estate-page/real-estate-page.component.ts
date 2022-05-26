import { AfterViewInit, Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { RealEstateService } from 'src/modules/admin/services/real-estate.service';
import { RealEstateWithHouseholdAndDevicesDTO } from 'src/modules/shared/models/RealEstateDTO';
import { SnackBarService } from 'src/modules/shared/services/snack-bar.service';

@Component({
  selector: 'app-real-estate-page',
  templateUrl: './real-estate-page.component.html',
  styleUrls: ['./real-estate-page.component.scss']
})
export class RealEstatePageComponent implements AfterViewInit {

  name: string | null;
  realEstate: RealEstateWithHouseholdAndDevicesDTO;

  constructor(private route: ActivatedRoute, private router: Router, private realEstateService: RealEstateService, private snackBarService: SnackBarService) {
    this.name = route.snapshot.paramMap.get("name");
    this.realEstate = { household: [], devices: [] }
  }

  ngAfterViewInit(): void {
    if (!this.name) return;

    this.name = decodeURIComponent(this.name);
    this.realEstateService.getRealEstateByName(this.name)
      .subscribe((response) => {
        this.realEstate = response.body as RealEstateWithHouseholdAndDevicesDTO;
      },
        (error) => {
          if (error.status === 400) {
            this.snackBarService.openSnackBar("You can't access real estate where you don't belong.")
            this.router.navigate(["mh-app/user/user-home-page"]);
          }
        });
  }

  showMore(name: string | null) {
    this.router.navigate(["mh-app/user/real-estate-page/device-messages", name]);
  }

}
