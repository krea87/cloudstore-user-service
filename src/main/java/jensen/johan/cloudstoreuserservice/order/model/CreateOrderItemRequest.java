package jensen.johan.cloudstoreuserservice.order.model;

import jakarta.validation.constraints.Min;

public record CreateOrderItemRequest(
        @Min(1)
        long productId,
        @Min(1)
        int quantity
) {
}
