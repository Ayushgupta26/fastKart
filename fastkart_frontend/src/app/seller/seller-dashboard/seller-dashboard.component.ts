import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ProductService } from 'src/app/services/product.service';
import { Product } from 'src/app/shared/models/Product';

@Component({
  selector: 'app-seller-dashboard',
  templateUrl: './seller-dashboard.component.html',
  styleUrls: ['./seller-dashboard.component.css']
})
export class SellerDashboardComponent implements OnInit {

  products: Product[]= [];
  page:number=1;
  totalItems:number=0;
  itemsPerPage:number = 4;
  constructor(private productService: ProductService, private router:Router) { }

  ngOnInit(): void {
    this.loadData();
  }

  loadData(){
    this.productService.getProductsBySeller(this.itemsPerPage, this.page-1).subscribe(
      res => {
        this.products = res.products;
        this.totalItems = res.totalItems;
        this.page = res.currentPage;
        console.log(this.products)
      }, err => {
      }
    )
  }

  /* Navigate to project details page */
  navigateToSpecificProject(projectId: number) {
    this.router.navigate([`seller/product-details/`, projectId])
  }
  navigateToAddNewProject(){
    this.router.navigate([`seller/add-product`])
  }

  onPageChange($event): void {
    this.page = $event;
    this.loadData();
  }
}
