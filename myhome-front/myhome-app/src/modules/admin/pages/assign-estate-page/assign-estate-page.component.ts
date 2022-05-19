import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { SnackBarService } from 'src/modules/shared/services/snack-bar.service';
import { RealEstateToAssignDTO } from '../../models/RealEstateDTO';
import { UserRealEstateDTO } from '../../models/UserRealEstateDTO';
import { RealEstateService } from '../../services/real-estate.service';
import { UserRealEstateService } from '../../services/user-real-estate.service';

@Component({
  selector: 'app-assign-estate-page',
  templateUrl: './assign-estate-page.component.html',
  styleUrls: ['./assign-estate-page.component.scss']
})
export class AssignEstatePageComponent implements OnInit {

  username: string;
  estates: RealEstateToAssignDTO[];

  constructor(private route: ActivatedRoute, private userRealEstateService: UserRealEstateService,
              private snackBarSerice: SnackBarService, private realEstateService: RealEstateService) {
    this.username = this.route.snapshot.params['username'];
    this.estates = [];
   }

  ngOnInit(): void {
    this.realEstateService.getRealEstateForUserToAssign(this.username).subscribe((res: any) => {
      this.estates = res.body;
    },
    (error) => {
      this.snackBarSerice.openSnackBar("An error ocured while loading real estates")
    })
  }

  setOwner(estateId: number): void {
    let userRealEstateDTO: UserRealEstateDTO = {
      username: this.username,
      realEstateId: estateId,
      role: 'OWNER'
    }
    
    this.userRealEstateService.saveUserRealEstate(userRealEstateDTO).subscribe((result: any) => {
      this.snackBarSerice.openSnackBar(result.body);
      this.removeEstateFromTable(estateId);
    },
    (error) => {
      this.snackBarSerice.openSnackBar(error.error);
    });
  }

  setTenant(estateId: number): void {
    let userRealEstateDTO: UserRealEstateDTO = {
      username: this.username,
      realEstateId: estateId,
      role: 'TENANT'
    }

    this.userRealEstateService.saveUserRealEstate(userRealEstateDTO).subscribe((result: any) => {
      this.snackBarSerice.openSnackBar(result.body);
      this.removeEstateFromTable(estateId);
    },
    (error) => {
      this.snackBarSerice.openSnackBar(error.error);
    });
  }

  removeEstateFromTable(id: number) {
    for(let i = 0; i < this.estates.length; i++) {
      if(this.estates[i].id === id)
        this.estates.splice(i, 1);
    }
  }

}
