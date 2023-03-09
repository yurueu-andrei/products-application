package ru.clevertec.cheque.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.clevertec.cheque.dto.ProductDto;
import ru.clevertec.cheque.dto.ProductSaveDto;
import ru.clevertec.cheque.exception.ServiceException;
import ru.clevertec.cheque.mapper.ProductMapper;
import ru.clevertec.cheque.model.entity.Product;
import ru.clevertec.cheque.repository.ProductRepository;

import java.util.List;

/**
 * Service class for products with <b>CRUD</b> operations and wrapping into <b>DTO</b>.
 *
 * @author Yuryeu Andrei
 */
@RequiredArgsConstructor
@Service
public class ProductService {
    /**
     * Product repository field
     *
     * @see ProductRepository
     */
    private final ProductRepository productRepository;
    /**
     * Product mapper field
     *
     * @see ProductMapper
     */
    private final ProductMapper productMapper;

    /**
     * Method for finding product by its ID
     *
     * @param id ID of target entity
     * @return returns a <b>DTO</b> made out of found Entity
     * @throws ServiceException in case of <b>null</b> ID
     */
    public ProductDto findById(Long id) throws ServiceException {
        return productRepository.findById(id).map(productMapper::toDto)
                .orElseThrow(() -> new ServiceException(String.format("The item was not found. id = %d", id)));
    }

    /**
     * Method for finding all products
     *
     * @return returns a <b>list of DTOs</b> made out of found Entities
     */
    @Transactional
    public List<ProductDto> findAll() throws ServiceException {
        try {
            return productMapper.toListDto(productRepository.findAll());
        } catch (Exception ex) {
            throw new ServiceException("The items were not found", ex);
        }
    }

    /**
     * Method for adding a new product
     *
     * @param productSaveDto saveDTO with desirable fields
     * @return returns a DTO of added product<b>(contains generated ID)</b>
     * @throws ServiceException in case of <b>null</b> saveDTO
     */
    @Transactional
    public ProductDto add(ProductSaveDto productSaveDto) throws ServiceException {
        try {
            Product product = productMapper.fromSaveDto(productSaveDto);
            return productMapper.toDto(productRepository.save(product));
        } catch (Exception ex) {
            throw new ServiceException(String.format("The item was not saved. %s", productSaveDto), ex);
        }
    }

    /**
     * Method for updating the existing product
     *
     * @param productDto DTO object with target ID and field(s) <b>to be updated</b>
     * @return <b>true</b> - in case of successful update
     * @throws ServiceException in case of update of nonexistent product
     */
    @Transactional
    public boolean update(ProductDto productDto) throws ServiceException {
        String message = String.format("The item was not updated. The item was not found. id = %d", productDto.getId());
        Product foundProduct = productRepository.findById(productDto.getId())
                .orElseThrow(() -> new ServiceException(message));
        try {
            settingUpdatedFields(productDto, foundProduct);
            productRepository.save(foundProduct);
            return true;
        } catch (Exception ex) {
            throw new ServiceException(String.format("The item was not updated. %s", productDto), ex);
        }
    }

    /**
     * Private method for setting fields values of DTO into found Entity
     *
     * @param productDto   DTO object with field(s) to be inserted into Entity
     * @param foundProduct Entity to be updated
     * @see ProductService#update(ProductDto product DTO)
     */
    private void settingUpdatedFields(ProductDto productDto, Product foundProduct) {
        if (productDto.getName() != null) {
            foundProduct.setName(productDto.getName());
        }
        if (productDto.getPrice() != null) {
            foundProduct.setPrice(productDto.getPrice());
        }
        if (productDto.isOnSale() != foundProduct.isOnSale()) {
            foundProduct.setOnSale(!foundProduct.isOnSale());
        }
    }

    /**
     * Method for deleting a product
     *
     * @param id ID of target entity
     * @return <b>true</b> - in case of successful deletion
     * @throws ServiceException in case of <b>null</b> ID
     */
    @Transactional
    public boolean delete(Long id) {
        try {
            productRepository.deleteById(id);
            return true;
        } catch (Exception ex) {
            throw new ServiceException(String.format("The item was not deleted. id = %d", id), ex);
        }
    }
}
