import { AfterViewInit, Component } from '@angular/core';
import { AuthService } from 'src/modules/auth/services/auth.service';

@Component({
  selector: 'app-header-common',
  templateUrl: './header-common.component.html',
  styleUrls: ['./header-common.component.scss']
})
export class HeaderCommonComponent implements AfterViewInit {

  constructor(
    private authService: AuthService,
  ) { }

  ngAfterViewInit(): void {
  }

  logout(): void {
    this.authService.logout();
  }

  profile(): void {
    alert("Not implemented");
  }

}
