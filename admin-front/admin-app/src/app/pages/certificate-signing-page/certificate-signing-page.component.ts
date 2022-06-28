import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { CertificateDataDTO } from 'src/app/models/CertificateDataDTO';
import { CertificateSigningDTO } from 'src/app/models/CertificateSigningDTO';
import { CSRDTO } from 'src/app/models/CSRDTO';
import { ExtendedKeyUsageDTO } from 'src/app/models/ExtendedKeyUsageDTO';
import { KeyUsageDTO } from 'src/app/models/KeyUsageDTO';
import { CertificateService } from 'src/app/services/certificate.service';
import { CsrService } from 'src/app/services/csr.service';
import { SnackBarService } from 'src/app/services/snack-bar.service';
import { UtilService } from 'src/app/services/util.service';

@Component({
  selector: 'app-certificate-signing-page',
  templateUrl: './certificate-signing-page.component.html',
  styleUrls: ['./certificate-signing-page.component.scss']
})
export class CertificateSigningPageComponent implements OnInit {

  csr: CSRDTO;
  csrBack: CSRDTO;

  ca: boolean;
  sslServer: boolean;
  sslClient: boolean;
  codeSigning: boolean;

  keyUsage: KeyUsageDTO;
  extendedKeyUsage: ExtendedKeyUsageDTO;
  isCa: boolean;

  constructor(private activatedRoute:ActivatedRoute, private csrService: CsrService,
              private snackBarService: SnackBarService, private utilService: UtilService,
              private certificateService: CertificateService, private router: Router) {
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

    this.csrBack = { 
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
    
    this.keyUsage = {
      certificateSigning: true,
      crlSign: false,
      dataEncipherment: false,
      decipherOnly: false,
      digitalSignature: false,
      encipherOnly: false,
      keyAgreement: false,
      keyEncipherment: false,
      nonRepudiation: false,
    };

    this.extendedKeyUsage = {
      adobePdfSigning: false,
      documentSigning: false,
      ipSecurityEndSystem: false,
      ocspSigning: false,
      tlsWebClientAuthentication: false,
      timeStamping: false,
      tlsWebServerAuthentication: false,
      emailProtection: false,
      tslSigning: false,
    };

    this.isCa = false;
  }

  ngOnInit(): void {
    let idStr: string | null = this.activatedRoute.snapshot.paramMap.get("id"); 

    if(!this.utilService.isValidInt(idStr))
      this.snackBarService.openSnackBar("Invalid csr id");

    if(idStr === null) return; // This will never happen

    this.csrService.findOneById(parseInt(idStr)).subscribe((response) => {
      this.csr = response.body as CSRDTO;
      this.saveLoadedCsr();
    }, 
    (error) => {
      this.snackBarService.openSnackBar("An error ocured while loading csr with id: " + idStr);
    })    
  }

  submit(dto: CertificateDataDTO): void {
    let certDTO: CertificateSigningDTO = {
      certificateDataDTO: null,
      keyUsageDTO: null,
      extendedKeyUsageDTO: null,
      ca: null,
    }

    if(!this.ca) {
      certDTO = {
        certificateDataDTO: this.csr,
        keyUsageDTO: this.keyUsage,
        extendedKeyUsageDTO: this.extendedKeyUsage,
        ca: null
      };
    } 
    else {
      certDTO = {
        certificateDataDTO: this.csr,
        keyUsageDTO: this.keyUsage,
        extendedKeyUsageDTO: null,
        ca: this.isCa
      };
    }

    this.signCsr(certDTO);
  }

  signCsr(certDTO: CertificateSigningDTO): void {
    // console.log(certDTO);

    this.certificateService.save(certDTO).subscribe((response) => {
      this.snackBarService.openSnackBar(response.body as string);
      this.router.navigate(["adm-app/homepage"]);
    }, 
    (error) => {
      this.snackBarService.openSnackBar(error.error as string);
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

  saveLoadedCsr(): void {
    this.csrBack.email = this.csr.email;
    this.csrBack.commonName = this.csr.commonName;
    this.csrBack.organizationUnit = this.csr.email;
    this.csrBack.organization = this.csr.organization;
    this.csrBack.state = this.csr.state;
    this.csrBack.country = this.csr.email;
    this.csrBack.city = this.csr.city;
  }
}
