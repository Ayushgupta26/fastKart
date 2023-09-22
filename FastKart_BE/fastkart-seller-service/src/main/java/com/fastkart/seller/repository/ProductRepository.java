package com.fastkart.seller.repository;

import com.fastkart.seller.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


@Repository
public interface ProductRepository extends JpaRepository<Product,Integer> {

    @Query(value = "SELECT p FROM Product p where p.sellerName = :sellerName")
    Page<Product> findBySellerName(@Param("sellerName") String  sellerName, final Pageable pageable);
}
