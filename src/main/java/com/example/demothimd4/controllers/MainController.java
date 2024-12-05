package com.example.demothimd4.controllers;

import com.example.demothimd4.models.Category;
import com.example.demothimd4.models.Product;
import com.example.demothimd4.services.CategoryService;
import com.example.demothimd4.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;

@Controller
@RequestMapping("/")
public class MainController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    private static final int PAGE_SIZE = 5;

    @GetMapping
    public String showAllProducts(Model model, @RequestParam(defaultValue = "0") int page) {
        Page<Product> products = productService.findAllProducts(page, PAGE_SIZE);
        model.addAttribute("productList", products.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", products.getTotalPages());
        return "home";
    }

    @GetMapping("/products/add")
    public String showAddProductForm(Model model) {
        List<Category> categories = categoryService.getAllCategories();
        model.addAttribute("categories", categories);
        return "addProduct";
    }

    @PostMapping("/products/add")
    public String addProduct(@ModelAttribute Product product) {
        productService.saveProduct(product);
        return "redirect:/";
    }

    @DeleteMapping("/products/delete/{id}")
    public String deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return "redirect:/";
    }

    @PostMapping("/products/delete")
    public String deleteProducts(@RequestParam List<Long> productIds) {
        productService.deleteProducts(productIds);
        return "redirect:/";
    }

    @GetMapping("/products/search")
    public String searchProducts(@RequestParam(required = false) String name,
                                 @RequestParam(required = false) Long price,
                                 @RequestParam(defaultValue = "0") int page,
                                 Model model) {
        Page<Product> products = productService.searchProducts(name, price, page, PAGE_SIZE);
        model.addAttribute("productList", products.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", products.getTotalPages());
        model.addAttribute("categories", categoryService.getAllCategories());
        return "home";
    }

    @GetMapping("/products/edit/{id}")
    public String editProduct(@PathVariable Long id, Model model) {
        Product product = productService.findById(id);
        List<Category> categories = categoryService.getAllCategories();
        model.addAttribute("product", product);
        model.addAttribute("categories", categories);
        return "editProduct";
    }

    @PostMapping("/products/update")
    public String updateProduct(@ModelAttribute Product product) {
        productService.updateProduct(product);
        return "redirect:/";
    }

}
