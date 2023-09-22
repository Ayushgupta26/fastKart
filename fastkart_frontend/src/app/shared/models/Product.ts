import { ProductBid } from "./ProductBid";

export class Product{
    "productId": number;
    "productName": String;
    "productCategory": String;
    "productDescription": String;
    "sellerName": String;
    "minBid": number;
    "listedDateTime": Date;
    "minBidByBuyer": number;
    "maxBidByBuyer": number;
    "productBids": ProductBid[];
}