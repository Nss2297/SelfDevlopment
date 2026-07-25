import { Component, Input } from '@angular/core';
import { Observable, map, takeWhile, tap } from 'rxjs';
import { AuthService } from 'src/app/modules/authentication/services/auth-service/auth.service';

@Component({
  selector: 'secured-element',
  templateUrl: './secured-element.component.html'
})
export class SecuredElementComponent {

  @Input()
  allowedAuthorities: string[] = [];
  isAuthorized$: Observable<boolean>;

  constructor(private authService: AuthService) {
    this.isAuthorized$ = authService.user$.pipe(
      takeWhile(user => user != null && user.authorities != null),
      map(user =>
        user.authorities?.some(userAuthority =>
          this.allowedAuthorities.some(allowedAuthority =>
            (allowedAuthority.includes(';') && allowedAuthority == userAuthority.authority)
            || (!allowedAuthority.includes(';') && userAuthority.authority.endsWith(allowedAuthority))
          ))
        || false)
    );
  }

}
