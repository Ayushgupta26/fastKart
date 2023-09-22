import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { ProductService } from 'src/app/services/product.service';
import { ProductResponse } from 'src/app/shared/models/ProductResponse';

@Component({
  selector: 'app-seller-add-product',
  templateUrl: './seller-add-product.component.html',
  styleUrls: ['./seller-add-product.component.css']
})
export class SellerAddProductComponent implements OnInit {

  addProductForm: FormGroup;
  submitted: boolean = false;
  errorMessage:string;
  errorBox:boolean = false;
  categories: any = ['Electronics', 'Clothes'];
  selectedFile: File | null = null;
  constructor(public activatedRoute: ActivatedRoute, private router:Router,private formBuilder: FormBuilder,private productService: ProductService, private toasterService: ToastrService) { 
    this.addProductForm = this.formBuilder.group({
      productName: ["", Validators.required],
      productCategory: ["", Validators.required],
      productDescription: ["", Validators.required],
      minBid:["", Validators.required]
    })
  }

  ngOnInit(): void {
  }

  addProduct() {
    this.submitted = true;
    this.errorBox = false;
    if (this.addProductForm.invalid) {
      return;
    }
    this.productService.addProduct(this.addProductForm.value).subscribe(
      (data: ProductResponse) => {
        this.router.navigate([`seller/dashboard`])
        this.toasterService.success(data.responseMessage);
      },
      err => {
        console.log(err.error.message);
        this.errorBox = true;
        this.errorMessage = err.error.message
        this.toasterService.error(this.errorMessage);
      }
    )
  }

  navigateToHomePage() {
    this.router.navigate([`seller/dashboard`])
  }

  changeCategory(e: any) {
    this.addProductForm.controls.productCategory.setValue(e.target.value, {
      onlySelf: true,
    });
  }

  onFileSelected(event: any): void {
    this.selectedFile = event.target.files[0];
  }

  onSubmit(): void {
    if (this.selectedFile) {
      const formData = new FormData();
      formData.append('file', this.selectedFile);

      this.productService.addProductByCsv(formData).subscribe(
        (data: ProductResponse) => {
          this.router.navigate([`seller/dashboard`])
          this.toasterService.success(data.responseMessage);
        },
        err => {
          console.log(err.error.message);
          this.errorBox = true;
          this.errorMessage = err.error.message
          this.toasterService.error(this.errorMessage);
        }
      )
    }
  }
}
