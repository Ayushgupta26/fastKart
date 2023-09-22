import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/services/auth.service';
import { TokenStorageService } from 'src/app/services/token-storage.service';
import { CommonConstants } from 'src/app/shared/constants/common.constants';
import { LoginResponse } from 'src/app/shared/models/LoginResponse';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  loginForm: FormGroup;
  role:String;
  submitted: boolean = false;
  errorMessage:String;
  errorBox:boolean = false;

  constructor(private formBuilder: FormBuilder,private authService: AuthService,
    private router: Router, private tokenStorageService: TokenStorageService) {
    this.loginForm = this.formBuilder.group({
      userName: ["", Validators.required],
      password: ["", Validators.required]
    })
  }
  ngOnInit(): void {
  }
  submitDetails() {
    this.submitted = true;
    this.errorBox = false;
    if (this.loginForm.invalid) {
      return;
    }
    this.authService.login(this.loginForm.value).subscribe(
      (data: LoginResponse) => {
        this.tokenStorageService.saveToken(data.accessToken.toString());
        //sessionStorage.setItem('token', JSON.stringify(data.accessToken));
        let decodedJWT = JSON.parse(window.atob(data.accessToken.split('.')[1]));
        this.role = decodedJWT.role;
        this.authService.isUserLoggedIn = true;
        if (this.role.toUpperCase() === CommonConstants.SELLER) {
          this.router.navigate(['/seller/dashboard']);
        }
        else if (this.role.toUpperCase() === CommonConstants.BUYER) {
          this.router.navigate(['/buyer/dashboard']);
        }
        //this.toaster.success(NotificationsConstants.LOGIN_SUCCESS);
      },
      err => {
        console.log(err.error.message);
        this.errorBox = true;
        this.errorMessage = err.error.message
        //this.toaster.error(NotificationsConstants.LOGIN_FAIL);
      }
    )
  }

}
