package jensen.johan.cloudstoreuserservice.order.model;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public record CreateOrderRequest(
        @NotEmpty
        List<@Valid CreateOrderItemRequest> items
) {
}
