import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './auth/login/login.component';
import { PageNotFoundComponent } from './shared/page-not-found/page-not-found.component';
import { SellerDashboardComponent } from './seller/seller-dashboard/seller-dashboard.component';
import { SellerProductDetailsComponent } from './seller/seller-product-details/seller-product-details.component';
import { SellerAddProductComponent } from './seller/seller-add-product/seller-add-product.component';
import { BuyerDashboardComponent } from './buyer/buyer-dashboard/buyer-dashboard.component';
import { BuyerProductDetailsComponent } from './buyer/buyer-product-details/buyer-product-details.component';
import { AuthService } from './services/auth.service';
import { RegisterUserComponent } from './auth/register-user/register-user.component';

const routes: Routes = [
  {
    path: '', redirectTo: '/login', pathMatch: 'full'
  },
  {
    path: 'login', component: LoginComponent
  },
  {
    path: 'page-not-found', component: PageNotFoundComponent
  },
  {
    path: 'register/:role', component: RegisterUserComponent
  },
  {
    path: 'seller/dashboard', component: SellerDashboardComponent, canActivate: [AuthService]
  },
  {
    path: 'seller/product-details/:productId', component: SellerProductDetailsComponent, canActivate: [AuthService]
  },
  {
    path: 'seller/add-product', component: SellerAddProductComponent, canActivate: [AuthService]
  },
  {
    path: 'buyer/dashboard', component: BuyerDashboardComponent, canActivate: [AuthService]
  },
  {
    path: 'buyer/product-details/:productId', component: BuyerProductDetailsComponent, canActivate: [AuthService]
  },
  { path: '**', redirectTo: '/page-not-found' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
