package com.c0324m4.finaltestm4.controller;

import com.c0324m4.finaltestm4.model.Product;
import com.c0324m4.finaltestm4.service.ProductService;
import com.c0324m4.finaltestm4.model.Category;
import com.c0324m4.finaltestm4.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import java.util.List;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
        model.addAttribute("categories", categoryService.getAllCategories());
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
    public String deleteProducts(@RequestParam(required = false) List<Long> productIds, RedirectAttributes redirectAttributes) {
        if (productIds == null || productIds.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Vui lòng chọn ít nhất một sản phẩm để xóa");
        } else {
            productService.deleteProducts(productIds);
            redirectAttributes.addFlashAttribute("success", "Xóa sản phẩm thành công");
        }
        return "redirect:/";
    }

    @GetMapping("/products/search")
    public String searchProducts(@RequestParam(required = false) String name,
                           @RequestParam(required = false) String price,
                           @RequestParam(required = false) Long categoryId,
                           @RequestParam(required = false) String status,
                           @RequestParam(defaultValue = "0") int page,
                           Model model) {
        Long priceValue = null;
        try {
            if (price != null && !price.isEmpty()) {
                priceValue = Long.parseLong(price);
            }
        } catch (NumberFormatException e) {
            // Xử lý khi giá trị price không hợp lệ
        }

        Page<Product> products = productService.searchProducts(name, priceValue, categoryId, status, page, PAGE_SIZE);
        model.addAttribute("productList", products.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", products.getTotalPages());
        model.addAttribute("categories", categoryService.getAllCategories());
        return "home";
    }
    

}
