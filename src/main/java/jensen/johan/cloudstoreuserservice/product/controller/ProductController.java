package jensen.johan.cloudstoreuserservice.product.controller;

import jensen.johan.cloudstoreuserservice.product.client.ProductClient;
import jensen.johan.cloudstoreuserservice.product.model.Product;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {


    private final ProductClient productClient;

    public ProductController(ProductClient productClient) {
        this.productClient = productClient;
    }

    @GetMapping
    public List<Product> getProducts() {
        return productClient.getProducts();
    }
}
