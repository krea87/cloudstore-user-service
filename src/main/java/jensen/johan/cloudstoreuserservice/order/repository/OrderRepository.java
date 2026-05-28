package jensen.johan.cloudstoreuserservice.order.repository;

import jensen.johan.cloudstoreuserservice.order.model.CustomerOrder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<CustomerOrder, Long> {

    List<CustomerOrder> findByUserEmail(String userEmail);
}
