package by.clevertec.cheque.service;

import by.clevertec.cheque.model.entity.Product;
import by.clevertec.cheque.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.service.spi.ServiceException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ProductService {
    private final ProductRepository productRepository;

    public Product findById(Long id) throws ServiceException {
        return productRepository.findById(id)
                .orElseThrow(() -> new ServiceException(String.format("The item was not found. id = %d", id)));
    }

    @Transactional(readOnly = true)
    public List<Product> findAll() throws ServiceException {
        try {
            return productRepository.findAll();
        } catch (Exception ex) {
            throw new ServiceException("The items were not found", ex);
        }
    }

    @Transactional
    public Product add(Product product) throws ServiceException {
        try {
            return productRepository.save(product);
        } catch (Exception ex) {
            throw new ServiceException(String.format("The item was not saved. %s", product), ex);
        }
    }

    @Transactional
    public boolean update(Product product) throws ServiceException {
        Product foundProduct = productRepository.findById(product.getId())
                .orElseThrow(() ->
                        new ServiceException(
                                String.format(
                                        "The item was not updated. The item was not found. id = %d",
                                        product.getId())));
        try {
            settingUpdatedFields(product, foundProduct);
            productRepository.flush();
            return true;
        } catch (Exception ex) {
            throw new ServiceException(String.format("The item was not updated. %s", product), ex);
        }
    }

    private void settingUpdatedFields(Product product, Product foundProduct) {
        if (product.getName() != null) {
            foundProduct.setName(product.getName());
        }
        if (product.isOnSale() != foundProduct.isOnSale()) {
            foundProduct.setOnSale(!foundProduct.isOnSale());
        }
    }

    @Transactional
    public boolean delete(Long id) throws ServiceException {
        Product author = productRepository.findById(id)
                .orElseThrow(() ->
                        new ServiceException(
                                String.format(
                                        "The item was not deleted. The item was not found. id = %d", id)));
        try {
            productRepository.delete(author);
            return true;
        } catch (Exception ex) {
            throw new ServiceException(String.format("The item was not deleted. id = %d", id), ex);
        }
    }
}
