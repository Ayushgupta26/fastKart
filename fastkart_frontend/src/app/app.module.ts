import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LoginComponent } from './auth/login/login.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { PageNotFoundComponent } from './shared/page-not-found/page-not-found.component';
import { HttpClientModule } from '@angular/common/http';
import { BuyerDashboardComponent } from './buyer/buyer-dashboard/buyer-dashboard.component';
import { SellerDashboardComponent } from './seller/seller-dashboard/seller-dashboard.component';
import { SellerProductDetailsComponent } from './seller/seller-product-details/seller-product-details.component';
import { SellerAddProductComponent } from './seller/seller-add-product/seller-add-product.component';
import { BuyerProductDetailsComponent } from './buyer/buyer-product-details/buyer-product-details.component';
import { RegisterUserComponent } from './auth/register-user/register-user.component';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    PageNotFoundComponent,
    BuyerDashboardComponent,
    SellerDashboardComponent,
    SellerProductDetailsComponent,
    SellerAddProductComponent,
    BuyerProductDetailsComponent,
    RegisterUserComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule,
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
