import { Component, OnInit } from '@angular/core';
import { JwtHelperService } from '@auth0/angular-jwt';
import { UtilService } from 'src/modules/shared/services/util/util.service';

@Component({
  selector: 'app-root-layout-page',
  templateUrl: './root-layout-page.component.html',
  styleUrls: ['./root-layout-page.component.scss']
})
export class RootLayoutPageComponent {

  constructor(public utilService: UtilService) { }

  isRole(role: string): boolean {
    return this.utilService.isRoleInUserRoles(role);
  }

}
