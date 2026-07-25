import { Component } from '@angular/core';
import { AuthService } from 'src/app/modules/authentication/services/auth-service/auth.service';
import { environment } from 'src/environments/environment';

@Component({
  selector: 'app-nav-menu',
  templateUrl: './nav-menu.component.html',
  styleUrls: ['./nav-menu.component.css']
})
export class NavMenuComponent {

  environmentName: string = environment.name;

  constructor(public authService: AuthService) {}

}
