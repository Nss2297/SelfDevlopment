import { ProductResponseDTO } from "./product-response-dto";

export interface ProductResponseListDTO {
    totalPages: number;
    totalCount: number;
    pageIndex: number;
    dataList: ProductResponseDTO[];
}
