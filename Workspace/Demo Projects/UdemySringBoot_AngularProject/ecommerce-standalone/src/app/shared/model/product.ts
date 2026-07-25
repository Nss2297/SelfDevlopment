export interface IProduct {
    productId: number;
    sku: string;
    title: string;
    description: string;
    unitPrice: number;
    imageUrl: string;
    isActive: boolean;
    categoryName: string;
    brandName: string;
    unitsInStock: number;
    dateCreated: Date;
    lastUpdated: Date;
}

export interface IBrand {
    brandId: number;
    brandName: string;
}

export interface ICategory {
    categoryId: number;
    categoryName: string;
}

