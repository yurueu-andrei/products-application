package ru.clevertec.cheque.repository;

import ru.clevertec.cheque.annotation.MyCacheable;
import ru.clevertec.cheque.model.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository class for products with <b>CRUD</b> operations.
 * @author Yuryeu Andrei
 */
@MyCacheable
public interface ProductRepository extends JpaRepository<Product, Long> {

}
