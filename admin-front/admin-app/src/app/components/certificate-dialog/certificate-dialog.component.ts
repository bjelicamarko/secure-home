import { Component, Inject, OnInit } from '@angular/core';
import { MatDialog, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { CertificateDTO } from 'src/app/models/CertificateDTO';
import { CertificateInfoDialogComponent } from '../certificate-info-dialog/certificate-info-dialog.component';

@Component({
  selector: 'app-certificate-dialog',
  templateUrl: './certificate-dialog.component.html',
  styleUrls: ['./certificate-dialog.component.scss']
})
export class CertificateDialogComponent implements OnInit {

  constructor(public dialog: MatDialog, public dialogRef: MatDialogRef<CertificateDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public certificates: CertificateDTO[]) { }

  ngOnInit(): void {
  }

  handleClick(issuedTo: string) {
    for (let i = 0; i < this.certificates.length; i++) {
      if (this.certificates[i].issuedTo === issuedTo) {
        this.dialog.open(CertificateInfoDialogComponent, {
          width: '50%',
          data: this.certificates[i]
        });
      }
    }
  }
}
