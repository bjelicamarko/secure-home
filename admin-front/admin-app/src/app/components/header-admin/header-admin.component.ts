import { Component, OnInit } from '@angular/core';
import { AuthService } from 'src/app/services/auth.service';

@Component({
  selector: 'app-header-admin',
  templateUrl: './header-admin.component.html',
  styleUrls: ['./header-admin.component.scss']
})
export class HeaderAdminComponent implements OnInit {

  notSeenCount: number;

  constructor(private authService: AuthService) {
    this.notSeenCount = 0;
  }

  ngOnInit(): void {
  }

  logout(): void {
    this.authService.logout().subscribe((result) => {
      console.log(result);
    });

    sessionStorage.removeItem("user");
  }
}
