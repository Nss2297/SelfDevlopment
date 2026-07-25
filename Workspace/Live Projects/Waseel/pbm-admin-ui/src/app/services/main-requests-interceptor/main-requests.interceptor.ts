import { Injectable } from '@angular/core';
import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor,
  HttpHeaders,
  HttpResponse
} from '@angular/common/http';
import { mergeMap, Observable, take, map, catchError, of, from } from 'rxjs';
import { AuthService } from 'src/app/modules/authentication/services/auth-service/auth.service';
import { environment } from 'src/environments/environment';

@Injectable()
export class MainRequestsInterceptor implements HttpInterceptor {

  constructor(private authService: AuthService) { }

  intercept(request: HttpRequest<unknown>, next: HttpHandler): Observable<HttpEvent<unknown>> {
    if (environment.name == 'dev' && request.url.startsWith('/mocks/')) {
      try {
        return from(import(`../../../${request.url.split('?')[0].replace('/', '')}.json`)).pipe(
          mergeMap(json => {
            return next.handle(request).pipe(
              map(event => {
                return new HttpResponse<unknown>({ body: json, status: 200, headers: new HttpHeaders().set('content-type', 'application/json') });
              }),
              catchError(errorEvent => {
                return of(new HttpResponse<unknown>({ body: json, status: 200, headers: new HttpHeaders().set('content-type', 'application/json') }));
              })
            )
          })
        )
      } catch (error) {
        console.log(error)
      }
    }
    return this.authService.credentials$.pipe(
      take(1),
      mergeMap(credentials => {
        if (credentials && credentials.access_token) {
          if (this.tokenIsExpiringSoon(credentials.expires_in)) {
            this.authService.refreshToken();
          }
          return next.handle(request.clone({
            headers: new HttpHeaders()
              .set('Authorization', `${credentials.token_type} ${credentials.access_token}`)
            //  .set('Authorization', ' Bearer eyJ0eXBlIjoibGltaXRlZF9hY2Nlc3NfdG9rZW4iLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxMjE2NTQ5ODcyIiwiZXhwIjoxNjg0NTg0MDk3LCJpYXQiOjE2ODM5NzkyOTcsInJvbCI6W3siYXV0aG9yaXR5IjoiMTIxNjU0OTg3MnwxMjM1NDY4OTcifV19.8z54aKV0vW0kjTm1_4XyLfF-8JQu_9qqBj6WrRHNTXdQQh333pJD0mLhzxE6x7zIUztcrH00S8S0XIayz8X0fQ')
          }));
        }
        return next.handle(request);
      })
    );
  }
  tokenIsExpiringSoon(expires_in: string | undefined): boolean {
    return expires_in !== undefined && (new Date(expires_in).getTime() - new Date().getTime()) < (1000 * 60 * 15);
  }
}
