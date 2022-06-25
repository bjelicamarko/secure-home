import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { CsrDTO } from 'src/app/models/CsrDTO';
import { CsrService } from 'src/app/services/csr.service';
import { SnackBarService } from 'src/app/services/snack-bar.service';
import { UtilService } from 'src/app/services/util.service';

@Component({
  selector: 'app-certificate-signing-page',
  templateUrl: './certificate-signing-page.component.html',
  styleUrls: ['./certificate-signing-page.component.scss']
})
export class CertificateSigningPageComponent implements OnInit {

  csr: CsrDTO;

  ca: boolean;
  sslServer: boolean;
  sslClient: boolean;
  codeSigning: boolean;

  constructor(private activatedRoute:ActivatedRoute, private csrService: CsrService,
              private snackBarService: SnackBarService, private utilService: UtilService) {
    this.csr = { 
      id: -1,
      email: "",
      commonName: "",
      organizationUnit: "",
      organization: "",
      city: "",
      state: "",
      country: "",
    };

    this.ca = true;
    this.sslServer = false;
    this.sslClient = false;
    this.codeSigning = false;
  }

  ngOnInit(): void {
    let idStr: string | null = this.activatedRoute.snapshot.paramMap.get("id"); 

    if(!this.utilService.isValidInt(idStr))
      this.snackBarService.openSnackBar("Invalid csr id");

    if(idStr === null) return; // This will never happen

    this.csrService.findOneById(parseInt(idStr)).subscribe((response) => {
      this.csr = response.body as CsrDTO;
    }, 
    (error) => {
      this.snackBarService.openSnackBar("An error ocured while loading csr with id: " + idStr);
    })    
  }

  selectCa(): void {
    this.deselectAll();
    this.ca = true;
  }

  selectSslServer(): void {
    this.deselectAll();
    this.sslServer = true;
  }

  selectSslClient(): void {
    this.deselectAll();
    this.sslClient = true;
  }

  selectCodeSigning(): void {
    this.deselectAll();
    this.codeSigning = true;
  }

  deselectAll(): void {
    this.ca = false;
    this.sslServer = false;
    this.sslClient = false;
    this.codeSigning = false;
  }
}
