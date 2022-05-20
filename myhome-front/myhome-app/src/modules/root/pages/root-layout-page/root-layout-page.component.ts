import { Component, OnInit } from '@angular/core';
import { JwtHelperService } from '@auth0/angular-jwt';

@Component({
  selector: 'app-root-layout-page',
  templateUrl: './root-layout-page.component.html',
  styleUrls: ['./root-layout-page.component.scss']
})
export class RootLayoutPageComponent {

  // public showFiller: boolean = false;

  public role: string;

  constructor() { 
    this.role = "";
  }

  checkRole() {
    const item = sessionStorage.getItem("user");

    if (item) {
      const jwt: JwtHelperService = new JwtHelperService();
      this.role = jwt.decodeToken(item).role;
    }
  }

}
