import { Injectable, Type } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, CanActivateChild, CanLoad, Params, Route, Router, RouterStateSnapshot, UrlSegment, UrlTree } from '@angular/router';
import { map, mergeMap, Observable, of, take } from 'rxjs';
import { SignInComponent } from 'src/app/modules/authentication/pages/sign-in/sign-in.component';
import { AuthService } from 'src/app/modules/authentication/services/auth-service/auth.service';

@Injectable({
  providedIn: 'root'
})
export class MainRouteGuard implements CanActivate, CanActivateChild, CanLoad {

  constructor(private authService: AuthService, private router: Router) { }


  canLoad(route: Route, segments: UrlSegment[]): boolean | UrlTree | Observable<boolean | UrlTree> | Promise<boolean | UrlTree> {
    return this.canActivateAny(route.component);
  }

  canActivateChild(childRoute: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean | UrlTree | Observable<boolean | UrlTree> | Promise<boolean | UrlTree> {
    return this.canActivateAny(childRoute.component);
  }

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean | UrlTree | Observable<boolean | UrlTree> | Promise<boolean | UrlTree> {
    return this.canActivateAny(route.component);
  }

  private canActivateAny(component: Type<any> | null | undefined) {
    return this.authService.credentials$.pipe(
      take(1),
      mergeMap((credentials) => {
        if (!credentials.access_token) {
          return this.authService.refreshToken().pipe(
            map(refreshed => {
              if (refreshed) {
                if (component == SignInComponent) {
                  return this.navigate(["/"]);
                } else {
                  return true;
                }
              } else {
                if (component == SignInComponent) {
                  return true;
                } else {
                  return this.navigate(["auth", "signIn"]);
                }
              }

            })
          );
        } else {
          if (component == SignInComponent) {
            return of(this.navigate(["/"]));
          } else {
            return of(true);
          }
        }
      })
    );
  }

  private navigate(url: string[]): UrlTree {
    return this.router.createUrlTree(url);
  }
}
