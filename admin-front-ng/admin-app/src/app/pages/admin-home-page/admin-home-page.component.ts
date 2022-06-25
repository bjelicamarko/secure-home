import { Component, OnInit } from '@angular/core';
import { CsrDTO } from 'src/app/models/CsrDTO';
import { CsrService } from 'src/app/services/csr.service';
import { SnackBarService } from 'src/app/services/snack-bar.service';

@Component({
  selector: 'app-admin-home-page',
  templateUrl: './admin-home-page.component.html',
  styleUrls: ['./admin-home-page.component.scss']
})
export class AdminHomePageComponent implements OnInit {

  csrs: CsrDTO[];

  constructor(private csrService: CsrService, private snackBarService: SnackBarService) {
    this.csrs = [];
   }

  ngOnInit(): void {

    this.csrService.getAllVerified().subscribe((response) => {
      this.csrs = response.body as CsrDTO[];
    }, 
    (error) => {
      this.snackBarService.openSnackBar("An error ocured while loading verified csrs")
    })

  }

  examine(id: number): void {
    alert(id);
  }

}
