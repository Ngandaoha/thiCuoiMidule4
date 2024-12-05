package com.example.demothimd4.services;


import com.example.demothimd4.models.Product;
import com.example.demothimd4.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


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

    public Page<Product> searchProducts(String name, Long price, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return productRepository.findAll((root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (name != null && !name.isEmpty()) {
                predicates.add(criteriaBuilder.like(root.get("name"), "%" + name + "%"));
            }
            if (price != null && price > 0) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("price"), price));
            }
            return predicates.isEmpty() ? null : criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        }, pageable);
    }

    public Product findById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    public void updateProduct(Product product) {
        productRepository.save(product);
    }

    public void deleteProducts(List<Long> productIds) {
        for (Long id : productIds) {
            productRepository.deleteById(id);
        }
    }
}
