import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { AuthService } from 'src/app/services/auth.service';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit {

  isLogin: boolean = false;
  constructor(private router: Router, private authService: AuthService, private toasterService:ToastrService){ 
  }
  ngOnInit(): void {
    console.log(this.router.url)
    if (sessionStorage.getItem("token")) {
      this.isLogin = true;
    }
    else {
      this.isLogin = false;
    }
    console.log(this.isLogin)
  }
  
  logout() {
    sessionStorage.clear();
    this.toasterService.success("LOGOUT_SUCCESS");
    this.router.navigate(['/']);
  }

  navigateToSeller(){
      this.router.navigate([`register/`, 'SELLER'])
    }
    navigateToBuyer(){
      this.router.navigate([`register/`, 'BUYER'])
    }
  

}
