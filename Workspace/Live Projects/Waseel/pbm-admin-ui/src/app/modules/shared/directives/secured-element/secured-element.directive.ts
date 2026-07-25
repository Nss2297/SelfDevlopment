import { Directive, Input, TemplateRef, ViewContainerRef, OnInit, OnDestroy } from '@angular/core';
import { Subject, lastValueFrom, map, takeUntil, takeWhile } from 'rxjs';
import { AuthService } from 'src/app/modules/authentication/services/auth-service/auth.service';

@Directive({
  selector: '[ifHasAnyAllowedAuthority]'
})
export class SecuredElementDirective implements OnInit, OnDestroy {

  @Input()
  ifHasAnyAllowedAuthority: string[] = [];

  private onDestroy$ = new Subject<void>();

  constructor(private authService: AuthService, private _viewContainer: ViewContainerRef, private templateRef: TemplateRef<any>) { }


  ngOnInit(): void {
    this.authService.user$.pipe(
      takeWhile(user => user != null && user.authorities != null),
      map(user =>
        user.authorities?.some(userAuthority =>
          this.ifHasAnyAllowedAuthority.some(allowedAuthority =>
            (allowedAuthority.includes(';') && allowedAuthority == userAuthority.authority)
            || (!allowedAuthority.includes(';') && userAuthority.authority.endsWith(allowedAuthority))
          ))
        || false),
      takeUntil(this.onDestroy$)
    ).subscribe(isAuthorized => {
      if (isAuthorized) {
        this._viewContainer.createEmbeddedView(this.templateRef);
      }
      else {
        this._viewContainer.clear();
      }
    })
  }
  ngOnDestroy(): void {
    this.onDestroy$.next();
    this.onDestroy$.complete();
  }

}
