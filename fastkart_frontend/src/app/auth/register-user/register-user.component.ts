import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { Observable} from 'rxjs';
import { AuthService } from 'src/app/services/auth.service';
import { CommonConstants } from 'src/app/shared/constants/common.constants';
import { UserInfo } from 'src/app/shared/models/UserInfo';

@Component({
  selector: 'app-register-user',
  templateUrl: './register-user.component.html',
  styleUrls: ['./register-user.component.css']
})
export class RegisterUserComponent implements OnInit {

  state$: Observable<String>;
  role: String;
  registerForm: FormGroup;
  submitted: boolean = false;
  errorMessage:String;
  errorBox:boolean = false;
  user:UserInfo
  constructor(public activatedRoute: ActivatedRoute, private router:Router,private formBuilder: FormBuilder,private authService: AuthService, private toasterService: ToastrService) { 
    this.registerForm = this.formBuilder.group({
      userName: ["", Validators.required],
      password: ["", Validators.required],
      name: ["", Validators.required],
      role:[""]
    })
  }

   ngOnInit() {
    this.role = (String)(this.activatedRoute.snapshot.paramMap?.get('role'));
    if(this.role != CommonConstants.SELLER && this.role != CommonConstants.BUYER){
      this.router.navigate(['page-not-found'])
    }
    this.registerForm.controls.role.setValue(this.role);
    console.log(this.role)
  }
  submitDetails() {
    this.submitted = true;
    this.errorBox = false;
    if (this.registerForm.invalid) {
      return;
    }
    this.user =this.registerForm.value;
    this.authService.save(this.user).subscribe(
      data => {
        console.log("user added")
        console.log(this.role)
        if (this.role.toUpperCase() === CommonConstants.SELLER) {
          console.log("seller move")
          this.router.navigate(['login']);
        }
        else if (this.role.toUpperCase() === CommonConstants.BUYER) {
          console.log("buyer move")
          this.router.navigate(['login']);
        }
      },
      err => {
        console.log(err.error.message);
        this.errorBox = true;
        this.errorMessage = err.error.message;
      }
    )
  }
}
