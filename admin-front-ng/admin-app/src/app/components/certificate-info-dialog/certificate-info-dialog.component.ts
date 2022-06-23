import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { CertificateDTO } from 'src/app/models/CertificateDTO';

@Component({
  selector: 'app-certificate-info-dialog',
  templateUrl: './certificate-info-dialog.component.html',
  styleUrls: ['./certificate-info-dialog.component.scss']
})
export class CertificateInfoDialogComponent implements OnInit {

  constructor(public dialogRef: MatDialogRef<CertificateInfoDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public chainCertificate: CertificateDTO) { }

  ngOnInit(): void {
  }

}
