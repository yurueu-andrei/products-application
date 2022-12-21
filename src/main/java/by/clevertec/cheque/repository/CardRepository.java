package by.clevertec.cheque.repository;

import by.clevertec.cheque.model.entity.DiscountCard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardRepository extends JpaRepository<DiscountCard, Long> {

}
