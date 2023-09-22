import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { HttpClient } from '@angular/common/http';
import { Product } from '../shared/models/Product';
import { AddProductRequest } from '../shared/models/AddProductRequest';
import { ProductResponse } from '../shared/models/ProductResponse';
import { AddBidRequest } from '../shared/models/AddBidRequest';
import { ProductsResponse } from '../shared/models/ProductsResponse';

@Injectable({
  providedIn: 'root'
})
export class ProductService {

  constructor(private http: HttpClient) { }

  getProductsBySeller(itemsPerPage:number, currentPage:number) {
    return this.http.get<ProductsResponse>(environment.baseURL + `/seller/products?pageNumber=`+ currentPage + `&size=`+itemsPerPage);
  }

  getProductById(productId: number){
    return this.http.get<Product>(environment.baseURL + `/seller/productById/` + productId);
  }

  addProduct(addProductRequest: AddProductRequest){
    return this.http.post<ProductResponse>(environment.baseURL + `/seller/add/product` ,addProductRequest);
  }

  getProductsBuyer(itemsPerPage:number, currentPage:number) {
    return this.http.get<ProductsResponse>(environment.baseURL + `/buyer/products?pageNumber=`+ currentPage + `&size=`+itemsPerPage);
  }

  addBidToProduct(addBidRequest: AddBidRequest){
    return this.http.post<ProductResponse>(environment.baseURL + `/buyer/add/product/bid` ,addBidRequest);
  }
  addProductByCsv(csvData:any){
    return this.http.post<ProductResponse>(environment.baseURL + `/seller/process/csv/data` ,csvData);
  }
}
