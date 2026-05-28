package jensen.johan.cloudstoreuserservice.order.controller;

import jakarta.validation.Valid;
import jensen.johan.cloudstoreuserservice.order.model.CreateOrderRequest;
import jensen.johan.cloudstoreuserservice.order.model.CustomerOrder;
import jensen.johan.cloudstoreuserservice.order.service.OrderService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {


    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }


    @PostMapping
    public CustomerOrder createOrder(Authentication authentication, @RequestBody @Valid CreateOrderRequest request) {

        if(authentication == null) {
            throw new RuntimeException("User is not authenticated");
        }

        String userEmail = authentication.getName();
        return orderService.createOrder(userEmail, request);
    }

    @GetMapping
    public List<CustomerOrder> getOrdersForUser(Authentication authentication) {
        String userEmail = authentication.getName();
        return orderService.getOrdersForUser(userEmail);
    }
}
