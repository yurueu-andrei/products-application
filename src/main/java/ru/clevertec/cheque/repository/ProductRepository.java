package ru.clevertec.cheque.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.clevertec.cheque.annotation.MyCacheable;
import ru.clevertec.cheque.model.entity.Product;

/**
 * Repository class for products with <b>CRUD</b> operations.
 *
 * @author Yuryeu Andrei
 */
@MyCacheable
public interface ProductRepository extends JpaRepository<Product, Long> {

}
