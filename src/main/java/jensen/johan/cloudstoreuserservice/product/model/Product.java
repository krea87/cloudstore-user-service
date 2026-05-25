package jensen.johan.cloudstoreuserservice.product.model;

public record Product(
        int id,
        String title,
        double price,
        String description,
        String category,
        String image,
        Rating rating
) {
}
