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
    private final String fakestoreServiceUrl = "http://fakestoreservice-env.eba-dnzki35b.eu-north-1.elasticbeanstalk.com/products";

    public ProductClient(RestClient.Builder builder) {
        this.restClient = builder
                .baseUrl(fakestoreServiceUrl)
                .build();
    }

    public List<Product> getProducts() {
        Product[] products = restClient
                .get()
                .uri(fakestoreServiceUrl)
                .retrieve()
                .body(Product[].class);

        if(products == null || products.length == 0) {
         return List.of();
        }
        return Arrays.asList(products);
    }
}

