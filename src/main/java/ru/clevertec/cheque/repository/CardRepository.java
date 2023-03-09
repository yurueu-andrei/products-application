package ru.clevertec.cheque.repository;

import ru.clevertec.cheque.model.entity.DiscountCard;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository class for cards with <b>CRUD</b> operations.
 * @author Yuryeu Andrei
 */
public interface CardRepository extends JpaRepository<DiscountCard, Long> {

}
