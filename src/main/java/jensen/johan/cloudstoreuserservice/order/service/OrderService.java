package jensen.johan.cloudstoreuserservice.order.service;

import jensen.johan.cloudstoreuserservice.order.model.CreateOrderItemRequest;
import jensen.johan.cloudstoreuserservice.order.model.CreateOrderRequest;
import jensen.johan.cloudstoreuserservice.order.model.CustomerOrder;
import jensen.johan.cloudstoreuserservice.order.model.OrderItem;
import jensen.johan.cloudstoreuserservice.order.repository.OrderRepository;
import jensen.johan.cloudstoreuserservice.product.client.ProductClient;
import jensen.johan.cloudstoreuserservice.product.model.Product;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class OrderService {






    private final OrderRepository orderRepository;
    private final ProductClient productClient;

    OrderService(OrderRepository orderRepository, ProductClient productClient) {
        this.orderRepository = orderRepository;
        this.productClient = productClient;
    }

    public CustomerOrder createOrder(String userEmail, CreateOrderRequest request) {
        List<Product> products = productClient.getProducts();

        CustomerOrder order = new CustomerOrder(userEmail);

        for (CreateOrderItemRequest itemRequest : request.items()) {
            Product product = findProduct(products, itemRequest.productId());

            OrderItem item = new OrderItem(
                    product.id(),
                    product.title(),
                    product.price(),
                    itemRequest.quantity()
            );

            order.addItem(item);

        }
        return orderRepository.save(order);
    }

    public List<CustomerOrder> getOrdersForUser(String userEmail){
        return orderRepository.findByUserEmail(userEmail);
    }


    private Product findProduct(List<Product> products, Long productId) {
        return products.stream()
                .filter(product -> product.id().equals(productId))
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Product not found"));
    }
}
