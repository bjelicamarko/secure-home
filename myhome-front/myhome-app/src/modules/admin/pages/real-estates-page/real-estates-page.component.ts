import { Component, OnInit, ViewChild } from '@angular/core';
import { Router } from '@angular/router';
import { PaginationComponent } from 'src/modules/shared/components/pagination/pagination.component';
import { RealEstateWithPhotoAndRoleDTO } from 'src/modules/shared/models/RealEstateDTO';
import { SnackBarService } from 'src/modules/shared/services/snack-bar.service';
import { UtilService } from 'src/modules/shared/services/util/util.service';
import { RealEstateService } from '../../services/real-estate.service';

@Component({
  selector: 'app-real-estates-page',
  templateUrl: './real-estates-page.component.html',
  styleUrls: ['./real-estates-page.component.scss']
})
export class RealEstatesPageComponent implements OnInit {

  @ViewChild(PaginationComponent) pagination?: PaginationComponent;
  pageSize: number;
  currentPage: number;
  totalSize: number;
  realEstates: RealEstateWithPhotoAndRoleDTO[];

  constructor(
    private router: Router,
    private snackBarService: SnackBarService,
    private realEstateService: RealEstateService) 
    {
      this.realEstates = [];
      this.pageSize = 4;
      this.currentPage = 1;
      this.totalSize = 1;
    }

  ngOnInit(): void {
    this.realEstateService.getAllRealEstates(this.currentPage - 1, this.pageSize)
      .subscribe((response: any) => {
        this.realEstates = response.body;
        this.setPagination(response.headers.get('total-elements'), response.headers.get('current-page'));
      });
  }

  setPagination(totalItemsHeader: string | null, currentPageHeader: string | null) {
    if (totalItemsHeader) {
      this.totalSize = parseInt(totalItemsHeader);
    }
    if (currentPageHeader) {
      this.currentPage = parseInt(currentPageHeader);
    }
  }

  changePage(newPage: number) {
    this.realEstateService.getAllRealEstates(newPage - 1, this.pageSize).subscribe((res: any) => {
      if (res.body != null) {
        this.realEstates = res.body;
        this.setPagination(res.headers.get('total-elements'), res.headers.get('current-page'));
      }
    }, (err) => {
      if (err.error)
        this.snackBarService.openSnackBar(String(err.console));
    });
  }

  showMore(name: string) {
    this.router.navigate(["mh-app/admin/real-estate/", name]);
  }

}
