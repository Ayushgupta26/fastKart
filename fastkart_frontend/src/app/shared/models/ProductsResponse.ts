import { Product } from "./Product";

export class ProductsResponse{
    "products": Product[];
    "currentPage": number;
    "totalItems": number;
    "totalPages": number;
}