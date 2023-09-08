import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-seller-dashboard',
  templateUrl: './seller-dashboard.component.html',
  styleUrls: ['./seller-dashboard.component.css']
})
export class SellerDashboardComponent implements OnInit {

  products: String[]= []
  constructor() { }

  ngOnInit(): void {
  }

}
