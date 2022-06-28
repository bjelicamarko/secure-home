import { Component, Inject, OnInit } from '@angular/core';
import { MatDialog, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { CertificateDTO } from 'src/app/models/CertificateDTO';
import { CertificateService } from 'src/app/services/certificate.service';
import { SnackBarService } from 'src/app/services/snack-bar.service';
import { CertificateDialogComponent } from '../certificate-dialog/certificate-dialog.component';

@Component({
  selector: 'app-invalidate-certificate-dialog',
  templateUrl: './invalidate-certificate-dialog.component.html',
  styleUrls: ['./invalidate-certificate-dialog.component.scss']
})
export class InvalidateCertificateDialogComponent implements OnInit {

  reasons: string[] = [
    "Loss of a private key corresponding to the public key on the certificate",
    "Unintentional issuance of a certificate as a result of an error or attack on a certification body",
    "Improper conduct of the owner of the issued certificate",
    "Detection of defects in the application for a certificate subsequently",
  ]

  reason: string = "";

  constructor(
    public dialog: MatDialog,
    public dialogRef: MatDialogRef<CertificateDialogComponent>,
    public certificateService: CertificateService,
    public snackBarService: SnackBarService,
    @Inject(MAT_DIALOG_DATA) public selectedAlias: string
  ) { }

  ngOnInit(): void { }

  invalidateCertificate() {
    if (this.reason === "") {
      this.snackBarService.openSnackBar("You need to enter reason of invalidation.")
      return;
    }
    let revokedCertificateDTO = {
      alias: this.selectedAlias,
      reason: this.reason,
    };
    this.certificateService.invalidateCertificate(revokedCertificateDTO).subscribe(
      (response) => {
        this.snackBarService.openSnackBar(response.body as string);
        this.dialogRef.close();
      },
      (error) => {
        this.snackBarService.openSnackBar(error.error as string);
        this.dialogRef.close();  
      }
    );
  }

  setReason(reason: string) {
    this.reason = reason;
  }

}
