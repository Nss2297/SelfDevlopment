import { Injectable } from "@angular/core";
import { ActivatedRouteSnapshot, CanActivate, CanLoad, Data, Route, Router, RouterStateSnapshot, UrlSegment, UrlTree } from "@angular/router";
import { mergeMap, Observable, of, skip, skipWhile, take,  } from "rxjs";
import { AuthService } from "src/app/modules/authentication/services/auth-service/auth.service";

@Injectable({
    providedIn: 'root',
})
export class ModulesGuard implements CanLoad, CanActivate {

    constructor(private authService: AuthService, private router: Router) { }

    canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean | UrlTree | Observable<boolean | UrlTree> | Promise<boolean | UrlTree> {
        return this.canAccess(route.data);
    }

    canLoad(route: Route, segments: UrlSegment[]): boolean | UrlTree | Observable<boolean | UrlTree> | Promise<boolean | UrlTree> {
        return this.canAccess(route.data);
    }

    canAccess(routeData?: Data) {
        return this.authService.user$.pipe(
            skipWhile(user => user.accId == null),
            mergeMap((user) => {
                const isAllowed = routeData != null
                    && ((routeData['allowedAccountId'] != null && user.accId == routeData['allowedAccountId']) || routeData['allowedAccountId'] == null)
                    && (routeData['allowedAuthorities'] != null && this.authService.hasAnyAuthority(user, routeData['allowedAuthorities'])) as boolean;
                if (!isAllowed && routeData != null && routeData['onFailRedirectTo'] != null) {
                    return of(this.navigate(routeData['onFailRedirectTo']))
                }
                return of(isAllowed);
            })
        )
    }

    private navigate(url: string[]): UrlTree {
        return this.router.createUrlTree(url);
    }

}