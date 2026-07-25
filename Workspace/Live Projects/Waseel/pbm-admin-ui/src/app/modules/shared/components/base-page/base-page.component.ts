import { Component, Input } from '@angular/core';
import { AuthService } from 'src/app/modules/authentication/services/auth-service/auth.service';

@Component({
  selector: 'app-base-page',
  templateUrl: './base-page.component.html'
})
export class BasePageComponent {

  foldSideSection: boolean = false;

  @Input("headerTitle")
  headerTitle: string = "";

  @Input("onBackClick")
  onBackClick?: () => void | undefined;


  constructor(public authService: AuthService) {

  }

  onMenuIconClick() {
    this.foldSideSection = !this.foldSideSection;
    document.body.classList.toggle('toggle-sidebar');
  }

  onSignOutClick() {
    this.authService.signOut();
  }

}
