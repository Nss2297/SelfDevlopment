import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { IBrand, ICategory, IProduct } from '../shared/model/product';
import { ShopService } from './shop.service';
import { ProductResponseListDTO } from '../shared/model/product-response-list-dto';
import { ProductResponseDTO } from '../shared/model/product-response-dto';
import { ProductItemComponent } from './product-item/product-item.component';


@Component({
  selector: 'app-shop',
  standalone: true,
  imports: [CommonModule, ProductItemComponent],
  templateUrl: './shop.component.html',
  styleUrls: ['./shop.component.scss']
})
export class ShopComponent implements OnInit {
  products: IProduct[] = [];
  // paginatedProducts: IProduct[] = [];
  productResponseListDTO: ProductResponseListDTO = {
    totalCount: 0,
    totalPages: 0,
    pageIndex: 0,
    dataList: []
  };
  brands: IBrand[] = [];
  categories: ICategory[] = [];

  constructor(private shopService: ShopService) { }

  ngOnInit(): void {
    this.fetchProducts();
    this.fetchCategories();
    this.fetchBrands();
  }

  fetchProducts() {
    this.shopService.getProductsTestDataWithPagination().subscribe({
      next: response => {
        const productList: any = response.dataList;
        // this.productResponseListDTO.dataList = productList;
        this.products = productList;
        // this.productResponseListDTO.pageIndex = response.pageIndex;
        // this.productResponseListDTO.totalCount = response.totalCount;
        // this.productResponseListDTO.totalPages = response.totalPages;
        console.log('Test data with pagination');
        console.log(response);
        // console.log(this.productResponseListDTO);
        console.log(this.products);
      },
      error: error => {
        console.log('Test data with pagination error');
        console.log(error);
      }
    });
    this.shopService.getProductsTestData().subscribe({
      next: response => {
        this.products = response;
        console.log('Product data  without pagination');
        console.log(this.products);
      },
      error: error => {
        console.log('Product data  without pagination error');
        console.log(error);
      }
    });
  }

  fetchCategories() {
    this.shopService.getCategories().subscribe({
      next: response => {
        console.log('Categories');
        console.log(response);
        const productCategories: any = response;
        this.categories = [{ categoryId: 0, categoryName: 'All' }, ...productCategories];
        console.log(this.categories);
      },
      error: error => {
        console.log(error);
      }
    });
  }

  fetchBrands() {
    this.shopService.getBrands().subscribe({
      next: response => {
        console.log('Brands');
        console.log(response);
        const productBrands: any = response;
        this.brands = [{ brandId: 0, brandName: 'All' }, ...productBrands];
        console.log(this.brands);
      }, error: error => { console.log(error); }
    });
  }
}
