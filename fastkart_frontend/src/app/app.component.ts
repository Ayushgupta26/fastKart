import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from './services/auth.service';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'fastkart_frontend';
  constructor(private router: Router, private authService: AuthService, private toasterService:ToastrService){ 
  }
  ngOnInit(): void {
  }
  
  
}
