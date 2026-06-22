package jensen.johan.cloudstoreuserservice.order.service;

import jensen.johan.cloudstoreuserservice.order.model.CreateOrderItemRequest;
import jensen.johan.cloudstoreuserservice.order.model.CreateOrderRequest;
import jensen.johan.cloudstoreuserservice.order.model.CustomerOrder;
import jensen.johan.cloudstoreuserservice.order.model.OrderItem;
import jensen.johan.cloudstoreuserservice.order.repository.OrderRepository;
import jensen.johan.cloudstoreuserservice.product.client.ProductClient;
import jensen.johan.cloudstoreuserservice.product.model.Product;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OrderServiceTest {


    @Test
    void getOrdersForUser() {
        // Arrange
        OrderRepository repository = mock(OrderRepository.class);
        ProductClient client = mock(ProductClient.class);
        OrderService service = new OrderService(repository, client);

        String email = "test@test.se";
        List<CustomerOrder> fakeOrders = List.of(new CustomerOrder(email));
        when(repository.findByUserEmail(email)).thenReturn(fakeOrders);

        //Act
        List<CustomerOrder> result = service.getOrdersForUser(email);

        //Assert
        assertEquals(1, result.size()); // we expect it to be one
        System.out.println("Email on first order: " + result.get(0).getUserEmail());
        assertEquals(email, result.get(0).getUserEmail()); // expect to get test@test.se
        verify(repository, times(1)).findByUserEmail(email);
    }

    @Test
    void createOrder_Success() {
        // Arrange
        OrderRepository repository = mock(OrderRepository.class);
        ProductClient client = mock(ProductClient.class);
        OrderService service = new OrderService(repository, client);

        // fake product
        Product fakeProduct = new Product(
                1L,
                "Nike t-shirt",
                99.88,
                "A black t-shirt with white Nike logo",
                "unisex",
                "img.png",
                null
        );
        when(client.getProducts()).thenReturn(List.of(fakeProduct));

        // create request
        String email = "test@test.se";
        CreateOrderItemRequest itemRequest = new CreateOrderItemRequest(1L, 1);
        CreateOrderRequest orderRequest = new CreateOrderRequest(List.of(itemRequest));

        // save returns the same order that was passed in
        when(repository.save(any(CustomerOrder.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        // Act

        CustomerOrder result = service.createOrder(email, orderRequest);

        //Assert
        assertNotNull(result);

        System.out.println("Email on first order: " + result.getUserEmail());
        assertEquals(email, result.getUserEmail());


        // Check if the first order has the same id as expected
        OrderItem firstOrderItem = result.getItems().get(0);
        assertEquals(1L, firstOrderItem.getProductId());

        // Check if price is returned correct
        assertEquals(99.88, firstOrderItem.getPrice(), 0);

        // Check if number of orders are correct
        assertEquals(1, result.getItems().size());

        }

    @Test
    void createOrder_Failure() {
        //Arrange
        OrderRepository repository = mock(OrderRepository.class);
        ProductClient client = mock(ProductClient.class);
        OrderService service = new OrderService(repository, client);

        //return empty list for it to be error
        when(client.getProducts()).thenReturn(List.of());

        // fake product to make the test fail
//        Product fakeProduct = new Product(
//                1L,
//                "Nike t-shirt",
//                99.88,
//                "A black t-shirt with white Nike logo",
//                "unisex",
//                "img.png",
//                null
//        );
//        when(client.getProducts()).thenReturn(List.of(fakeProduct));

        String email = "test@test.se";
        CreateOrderItemRequest itemRequest = new CreateOrderItemRequest(1L, 1);
        CreateOrderRequest orderRequest = new CreateOrderRequest(List.of(itemRequest));

        // Act & Assert expecting throw not found

        ResponseStatusException exception = assertThrows(
                ResponseStatusException.class, () ->
                        service.createOrder(email, orderRequest));


        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());

        assertEquals("Product not found", exception.getReason());

        verify(repository, never()).save(any(CustomerOrder.class));
    }
    }
