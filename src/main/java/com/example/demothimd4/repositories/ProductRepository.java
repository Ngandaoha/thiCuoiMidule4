package com.example.demothimd4.repositories;

import com.example.demothimd4.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> getProductsByCategoryName(String categoryName);

    // find, get

    Product getProductsByName(String productName);
}

