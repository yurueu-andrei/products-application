package by.clevertec.cheque.repository;

import by.clevertec.cheque.model.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {

}
