import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from 'src/environments/environment';
import { Pagination } from '../shared/model/pagination';
import { IProduct } from '../shared/model/product';
import { ProductResponseListDTO } from '../shared/model/product-response-list-dto';

@Injectable({
  providedIn: 'root'
})
export class ShopService {

  constructor(private http: HttpClient) { }

  getProductsTestData() {
    return this.http.get<IProduct[]>(environment.apiUrl + "/test");
  }

  getProductsTestDataWithPagination() {
    return this.http.get<Pagination<IProduct[]>>(environment.apiUrl + "?pageIndex=10");
    // return this.http.get<Pagination<ProductResponseListDTO>>(environment.apiUrl+"?pageIndex=1");
    // return this.http.get<ProductResponseListDTO>(environment.apiUrl);
  }

  getCategories() {
    return this.http.get(environment.apiUrl + '/categories');
  }

  getBrands() {
    return this.http.get(environment.apiUrl + '/brands');
  }
}
