package ru.clevertec.cheque.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.clevertec.cheque.model.entity.DiscountCard;

/**
 * Repository class for cards with <b>CRUD</b> operations.
 *
 * @author Yuryeu Andrei
 */
public interface CardRepository extends JpaRepository<DiscountCard, Long> {

}
