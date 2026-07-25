
import { Component } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { Subject } from 'rxjs';
import { HttpException } from 'src/app/util/default-http-client';
import { AuthService } from '../../services/auth-service/auth.service';

@Component({
  selector: 'app-patient-sign-in',
  templateUrl: './patient-sign-in.component.html',
  styleUrls: ['./patient-sign-in.component.css']
})
export class PatientSignInComponent {
  signInForm = new FormGroup<{ username: FormControl<string | null>, password: FormControl<string | null> }>({
    username: new FormControl('', { validators: [Validators.required] }),
    password: new FormControl('', { validators: [Validators.required] })
  });

  isLoading$: Subject<boolean> = new Subject();
  errorCode: string = "";

  constructor(private authService: AuthService, private router: Router) {
    this.isLoading$.next(false);
  }

  signIn() {
    this.errorCode = '';
    this.isLoading$.next(true);
    this.authService.patientSignIn(this.signInForm.value).subscribe({
      next: (credentials) => {
        this.isLoading$.next(false)
        this.router.navigate(['/patient-prescription']);
      },
      error: (exception) => {
        if (exception instanceof HttpException) {
          this.errorCode = exception.messageCode;
        }
        this.isLoading$.next(false)
      }
    })
  }
  getYear() {
    return new Date().getFullYear();
  }


}
