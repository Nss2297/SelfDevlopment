import { Component, Input } from '@angular/core';
import { CommonModule } from '@angular/common';
import { IProduct } from 'src/app/shared/model/product';
import { ProductResponseDTO } from 'src/app/shared/model/product-response-dto';

@Component({
  selector: 'app-product-item',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './product-item.component.html',
  styleUrls: ['./product-item.component.scss']
})
export class ProductItemComponent {
@Input() product?: IProduct;
}
