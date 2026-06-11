package jensen.johan.cloudstoreuserservice.product.client;

import jensen.johan.cloudstoreuserservice.product.model.Product;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.Arrays;
import java.util.List;

@Component
public class ProductClient {

    private final RestClient restClient;

    public ProductClient(RestClient.Builder builder, @Value("${fakestore-base-url}") String baseUrl) {
        this.restClient = builder
                .baseUrl(baseUrl)
                .build();
    }

    public List<Product> getProducts() {
        Product[] products = restClient
                .get()
                .uri("/api/products")
                .retrieve()
                .body(Product[].class);

        if(products == null || products.length == 0) {
         return List.of();
        }
        return Arrays.asList(products);
    }
}

