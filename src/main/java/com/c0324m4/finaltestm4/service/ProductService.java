package com.c0324m4.finaltestm4.service;

import com.c0324m4.finaltestm4.model.Product;
import com.c0324m4.finaltestm4.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.criteria.Predicate;


@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public Page<Product> findAllProducts(int page, int size) {
        return productRepository.findAll(PageRequest.of(page, size));
    }

    public void saveProduct(Product product) {
        productRepository.save(product);
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    public Page<Product> searchProducts(String name, Long price, Long categoryId, String status, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return productRepository.findAll((root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            
            if (name != null && !name.isEmpty()) {
                predicates.add(criteriaBuilder.like(root.get("name"), "%" + name + "%"));
            }
            
            if (price != null && price > 0) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("price"), price));
            }
            
            if (categoryId != null) {
                predicates.add(criteriaBuilder.equal(root.get("category").get("id"), categoryId));
            }
            
            if (status != null && !status.isEmpty()) {
                predicates.add(criteriaBuilder.equal(root.get("status"), status));
            }
            
            return predicates.isEmpty() ? null : criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        }, pageable);
    }

    public Product findById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    public void updateProduct(Product product) {
        // Lấy sản phẩm hiện tại từ database
        Product existingProduct = productRepository.findById(product.getId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy sản phẩm"));
        
        // Giữ lại category nếu không có category mới
        if (product.getCategory() == null) {
            product.setCategory(existingProduct.getCategory());
        }
        
        productRepository.save(product);
    }

    public void deleteProducts(List<Long> productIds) {
        for (Long id : productIds) {
            productRepository.deleteById(id);
        }
    }
}
