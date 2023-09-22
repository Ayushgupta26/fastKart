import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ProductService } from 'src/app/services/product.service';
import { Product } from 'src/app/shared/models/Product';

@Component({
  selector: 'app-buyer-dashboard',
  templateUrl: './buyer-dashboard.component.html',
  styleUrls: ['./buyer-dashboard.component.css']
})
export class BuyerDashboardComponent implements OnInit {

  products: Product[]= [];
  page:number=1;
  totalItems:number=0;
  itemsPerPage:number = 4;
  constructor(private productService: ProductService, private router:Router) { }

  ngOnInit(): void {
    this.loadData();
  }

  loadData(){
    this.productService.getProductsBuyer(this.itemsPerPage, this.page-1).subscribe(
      res => {
        this.products = res.products;
        this.totalItems = res.totalItems;
        this.page = res.currentPage;
      }, err => {
      }
    )
  }

  /* Navigate to project details page */
  navigateToSpecificProject(projectId: number) {
    this.router.navigate([`buyer/product-details/`, projectId])
  }

  onPageChange($event): void {
    console.log($event)
    this.page = $event;
    console.log(this.page);
    this.loadData();
  }

}
