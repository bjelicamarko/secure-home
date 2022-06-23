import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { CertificateDialogComponent } from 'src/app/components/certificate-dialog/certificate-dialog.component';
import { CertificateDTO } from 'src/app/models/CertificateDTO';
import { CertificateService } from 'src/app/services/certificate.service';
import { SnackBarService } from 'src/app/services/snack-bar.service';

@Component({
  selector: 'app-all-certificates-view',
  templateUrl: './all-certificates-view.component.html',
  styleUrls: ['./all-certificates-view.component.scss']
})
export class AllCertificatesViewComponent implements OnInit {

  aliases: string[] = [];
  invalidAlias: string = "----------";
  selectedAlias: string = "----------";
  selectedCertificates: CertificateDTO[] = [];
  showModal: boolean = false;

  constructor(public dialog: MatDialog, private certificateService : CertificateService, 
    private snackBarService: SnackBarService) { }

  ngOnInit(): void {
    this.certificateService.getAliases()
    .subscribe((response) => {
      this.aliases = [this.invalidAlias];
      this.aliases = [...this.aliases, ...response.body as string[]];
    })
  }

  changeAlias() {
    this.certificateService.getCertificate(this.selectedAlias)
    .subscribe((response) => {
      this.selectedCertificates = response.body as CertificateDTO[];
    });
  }

  checkCertificate() { 
    if (this.selectedAlias === this.invalidAlias) {
      this.snackBarService.openSnackBar("Certificate not selected!");
    } else {
      this.certificateService.checkCertificate(this.selectedAlias)
      .subscribe((response) => {
        this.snackBarService.openSnackBar(response.body as string);
      })
    }
  }

  invalidateCertificate(reason: string) {
    if (this.selectedAlias === this.invalidAlias) {
      this.snackBarService.openSnackBar("Certificate not selected!");
    } else {
      let revokedCertificateDTO = {
        alias: this.selectedAlias,
        reason: reason,
      };
      this.certificateService.invalidateCertificate(revokedCertificateDTO)
      .subscribe((response) => {
        this.snackBarService.openSnackBar(response.body as string);
      })
    }
  }

  openModal() {
    if (this.selectedAlias !== this.invalidAlias) {
      this.dialog.open(CertificateDialogComponent, {
        width: '35%',
        data: this.selectedCertificates
      });
    } else {
      this.snackBarService.openSnackBar("Certificate not selected!");
    }
  }

  openModalInvalidateCert() {
    if (this.selectedAlias !== this.invalidAlias) {
      // otvori modal
    } else {
      this.snackBarService.openSnackBar("Certificate not selected!");
    }
  }
}
