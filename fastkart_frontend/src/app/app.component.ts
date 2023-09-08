import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'fastkart_frontend';
  constructor(private router: Router){
    
  }
  logout() {
    sessionStorage.clear();
    //this.toasterService.success(NotificationsConstants.LOGOUT_SUCCESS);
    this.router.navigate(['/']);
  }
}
