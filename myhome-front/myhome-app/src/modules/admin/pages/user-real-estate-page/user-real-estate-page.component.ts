import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { SnackBarService } from 'src/modules/shared/services/snack-bar.service';
import { UserRealEstateDTO, UserRealEstateToViewDTO } from '../../models/UserRealEstateDTO';
import { UserRealEstateService } from '../../services/user-real-estate.service';

@Component({
  selector: 'app-user-real-estate-page',
  templateUrl: './user-real-estate-page.component.html',
  styleUrls: ['./user-real-estate-page.component.scss']
})
export class UserRealEstatePageComponent implements OnInit {

  username: string;
  userEstates: UserRealEstateToViewDTO[];

  constructor(private route: ActivatedRoute, private userRealEstateService: UserRealEstateService,
              private snackBarService: SnackBarService) { 
    this.username = this.route.snapshot.params['username'];
    this.userEstates = [];
  }

  ngOnInit(): void {
    this.userRealEstateService.getUserRealEstatesFromUser(this.username).subscribe((res: any) => {
      this.userEstates = res.body;
    }, 
    (error) => {
      this.snackBarService.openSnackBar("An error ocured while loading real estates")
    })
  }

  changeToTenant(realEstateId: number): void {
    let userRealEstateDTO: UserRealEstateDTO = {
      username: this.username,
      realEstateId: realEstateId, 
      role: 'TENANT'
    };

    this.userRealEstateService.changeRoleInUserRealEstate(userRealEstateDTO).subscribe((res: any) => {
      this.snackBarService.openSnackBar(res.body);
      for(let i = 0; i < this.userEstates.length; i++) {
        if(this.userEstates[i].realEstateId === realEstateId)
          this.userEstates[i].role = 'TENANT'
      }
    },
    (error) => {
      this.snackBarService.openSnackBar(error.error);
    })
  }

  changeToOwner(realEstateId: number): void {
    let userRealEstateDTO: UserRealEstateDTO = {
      username: this.username,
      realEstateId: realEstateId, 
      role: 'OWNER'
    };

    this.userRealEstateService.changeRoleInUserRealEstate(userRealEstateDTO).subscribe((res: any) => {
      this.snackBarService.openSnackBar(res.body);
      for(let i = 0; i < this.userEstates.length; i++) {
        if(this.userEstates[i].realEstateId === realEstateId)
          this.userEstates[i].role = 'OWNER'
      }
    },
    (error) => {
      this.snackBarService.openSnackBar(error.error);
    })
  }

  removeUserRealEstate(realEstateId: number): void {
    let userRealEstateDTO: UserRealEstateDTO = {
      username: this.username,
      realEstateId: realEstateId, 
      role: ""
    };

    this.userRealEstateService.deleteUserRealEstate(userRealEstateDTO).subscribe((res: any) => {
      this.snackBarService.openSnackBar(res.body);
      for(let i = 0; i < this.userEstates.length; i++) {
        if(this.userEstates[i].realEstateId === realEstateId)
          this.userEstates.splice(i, 1);
      }
    },
    (error) => {
      this.snackBarService.openSnackBar(error.error);
    })
  }

}
