package com.ai.openai.services;

import com.ai.openai.entities.Product;
import com.ai.openai.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private OpenAiService openAiService;

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));
    }

    public Product createProduct(Product product) {

        // Generate a description using OpenAiService if not provided
        if (product.getDescription() == null || product.getDescription().isEmpty()) {
            String prompt = "Generate a product description for: " + product.getTitle();
            String generatedDescription = openAiService.generateResponse(prompt);
            product.setDescription(generatedDescription);
        }
        return productRepository.save(product);
    }

    public Product updateProduct(Long id, Product productDetails) {
        Product product = getProductById(id);
        product.setTitle(productDetails.getTitle());
        product.setDescription(productDetails.getDescription());
        product.setPrice(productDetails.getPrice());
        product.setCategory(productDetails.getCategory());
        return productRepository.save(product);
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    public String generateProductDescription(String title) {
        String prompt = "Generate a product description for: " + title;
        return openAiService.generateResponse(prompt);
    }
}