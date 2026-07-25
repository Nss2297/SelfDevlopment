import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { ApmService } from '@elastic/apm-rum-angular';
import { BehaviorSubject, catchError, map, of } from 'rxjs';
import { DefaultHttpClient } from 'src/app/util/default-http-client';
import { environment } from 'src/environments/environment';
import { Credentials } from '../../models/credentials.model';
import { User } from '../../models/user.model';

@Injectable({
  providedIn: 'root'
})
export class AuthService extends DefaultHttpClient {

  credentials$: BehaviorSubject<Credentials> = new BehaviorSubject(new Credentials());
  user$: BehaviorSubject<User> = new BehaviorSubject<User>(new User());

  systemLoaderVisible = false;

  apm: any;
  corsHeaders: any;
  constructor(protected override httpClient: HttpClient, service: ApmService) {
    super(httpClient, environment.backend.authService);
    if (environment.name != 'Dev') {
      this.apm = service.init({
        serviceName: 'pbm-admin-ui',
        serverUrl: environment.apm,
        logLevel: 'debug',
        environment: environment.name
      });
    }

    this.credentials$.subscribe((credentials) => {
      if (credentials && credentials.access_token)
        this.getCurrentUser().subscribe(() => { });
    });
  }

  signIn(data: { username?: string | null, password?: string | null }) {
    this.apm.setUserContext({
      'username': data.username,
      'id': data.username
    });
    return this.post<Credentials>('/signIn', data, {
      subjectToUpdate: this.credentials$,
      onBadRequestErrorCode: 'INVALID_CREDENTIALS',
      onUnauthorizedErrorCode: 'INVALID_CREDENTIALS'
    });
  }
  patientSignIn(data: { username?: string | null, password?: string | null }) {
    this.apm.setUserContext({
      'username': data.username,
      'id': data.username
    });
    return this.post<Credentials>('/signIn', data, {
      subjectToUpdate: this.credentials$,
      onBadRequestErrorCode: 'INVALID_CREDENTIALS',
      onUnauthorizedErrorCode: 'INVALID_CREDENTIALS'
    });
  }

  getCurrentUser() {

    return this.get<User>('/users/current', {
      subjectToUpdate: this.user$
    });
  }
  

  refreshToken() {
    return this.get<Credentials>('/refresh', {
      subjectToUpdate: this.credentials$
    }).pipe(
      map(() => true),
      catchError(() => of(false))
    );
  }

  signOut() {
    this.get('/signOut').subscribe({
      next: () => location.reload(),
      error: () => location.reload()
    });
  }

  hasAnyAuthority(user: User | null, authorities: string[]): boolean {
    return user != null && user.authorities != null && user.authorities.some(userAuthority => authorities.some(allowedAuthority =>
      (allowedAuthority.includes(';') && allowedAuthority == userAuthority.authority)
      || (!allowedAuthority.includes(';') && userAuthority.authority.endsWith(allowedAuthority))
    ));
  }


  showSystemLoader() {
    this.systemLoaderVisible = true;
  }

  hideSystemLoader() {
    this.systemLoaderVisible = false;
  }


}
