import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ProductService } from 'src/app/services/product.service';
import { Product } from 'src/app/shared/models/Product';

@Component({
  selector: 'app-seller-product-details',
  templateUrl: './seller-product-details.component.html',
  styleUrls: ['./seller-product-details.component.css']
})
export class SellerProductDetailsComponent implements OnInit {

  productDetails = new Product();
  productId:number;
  constructor(private productService: ProductService, private route: ActivatedRoute, private router:Router) { }

  ngOnInit(): void {
    this.productId = (Number)(this.route.snapshot.paramMap?.get('productId'));
    this.productService.getProductById(this.productId).subscribe(
      data => {
          this.productDetails = data;
      },err=>{

      }
    )
  }

  navigateToHomePage() {
    this.router.navigate([`seller/dashboard`])
  }

}
