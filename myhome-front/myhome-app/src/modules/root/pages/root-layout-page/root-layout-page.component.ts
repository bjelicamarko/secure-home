import { Component, OnInit } from '@angular/core';
import { SocketService } from 'src/modules/shared/services/socket.service';
import { UtilService } from 'src/modules/shared/services/util/util.service';

@Component({
  selector: 'app-root-layout-page',
  templateUrl: './root-layout-page.component.html',
  styleUrls: ['./root-layout-page.component.scss']
})
export class RootLayoutPageComponent {

  constructor(public utilService: UtilService, private webSocketService: SocketService) {}

  connectSocket(): void {
    let username = this.utilService.getLoggedUsername();
    if (username !== "")
      this.webSocketService.connect(username);
  }

  isRole(role: string): boolean {
    return this.utilService.isRoleInUserRoles(role);
  }

}
