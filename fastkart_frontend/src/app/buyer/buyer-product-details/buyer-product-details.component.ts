import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { ProductService } from 'src/app/services/product.service';
import { Product } from 'src/app/shared/models/Product';
import { ProductResponse } from 'src/app/shared/models/ProductResponse';

@Component({
  selector: 'app-buyer-product-details',
  templateUrl: './buyer-product-details.component.html',
  styleUrls: ['./buyer-product-details.component.css']
})
export class BuyerProductDetailsComponent implements OnInit {

  productDetails = new Product();
  productId:number;
  productBidForm: FormGroup;
  submitted: boolean = false;
  errorMessage:String;
  errorBox:boolean = false;
  constructor(public route: ActivatedRoute, private router:Router,private formBuilder: FormBuilder,private productService: ProductService, private toasterService: ToastrService) { 
    this.productBidForm = this.formBuilder.group({
      bidAmount: ["", Validators.required],
      productId: [""]
    })
  }

  ngOnInit(): void {
    this.productId = (Number)(this.route.snapshot.paramMap?.get('productId'));
    this.getProductDetails();
  }

  getProductDetails(){
    this.productService.getProductById(this.productId).subscribe(
      data => {
          this.productDetails = data;
      },err=>{

      }
    )
  }

  addBid(){
    this.submitted = true;
    this.errorBox = false;
    if (this.productBidForm.invalid) {
      return;
    }
    this.productBidForm.controls.productId.setValue(this.productId);
    this.productService.addBidToProduct(this.productBidForm.value).subscribe(
      (data: ProductResponse) => {
        this.getProductDetails();
        this.toasterService.success("PRODUCT BID ADDED SUCCESSFULLY");
      },
      err => {
        console.log(err.error.message);
        this.errorBox = true;
        this.productBidForm.controls.clear;
        this.errorMessage = err.error.message
        this.toasterService.error("ERROR IN ADDING BID TO PRODUCT");
      }
    )
  }

  navigateToHomePage() {
    this.router.navigate([`buyer/dashboard`])
  }

}
