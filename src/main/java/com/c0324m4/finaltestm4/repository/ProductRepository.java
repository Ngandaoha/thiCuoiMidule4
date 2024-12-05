package com.c0324m4.finaltestm4.repository;

import com.c0324m4.finaltestm4.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {
    // Các phương thức khác nếu cần
}
