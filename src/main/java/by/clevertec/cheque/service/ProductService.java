package by.clevertec.cheque.service;

import by.clevertec.cheque.dto.ProductDto;
import by.clevertec.cheque.dto.ProductSaveDto;
import by.clevertec.cheque.mapper.ProductMapper;
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
    private final ProductMapper productMapper;

    public ProductDto findById(Long id) throws ServiceException {
        return productRepository.findById(id).map(productMapper::toDto)
                .orElseThrow(() -> new ServiceException(String.format("The item was not found. id = %d", id)));
    }

    @Transactional(readOnly = true)
    public List<ProductDto> findAll() throws ServiceException {
        try {
            return productMapper.toDto(productRepository.findAll());
        } catch (Exception ex) {
            throw new ServiceException("The items were not found", ex);
        }
    }

    @Transactional
    public ProductDto add(ProductSaveDto productSaveDto) throws ServiceException {
        try {
            Product product = productMapper.fromSaveDto(productSaveDto);
            return productMapper.toDto(productRepository.save(product));
        } catch (Exception ex) {
            throw new ServiceException(String.format("The item was not saved. %s", productSaveDto), ex);
        }
    }

    @Transactional
    public boolean update(ProductDto productDto) throws ServiceException {
        Product foundProduct = productRepository.findById(productDto.getId())
                .orElseThrow(() ->
                        new ServiceException(
                                String.format(
                                        "The item was not updated. The item was not found. id = %d",
                                        productDto.getId())));
        try {
            settingUpdatedFields(productDto, foundProduct);
            productRepository.flush();
            return true;
        } catch (Exception ex) {
            throw new ServiceException(String.format("The item was not updated. %s", productDto), ex);
        }
    }

    private void settingUpdatedFields(ProductDto productDto, Product foundProduct) {
        if (productDto.getName() != null) {
            foundProduct.setName(productDto.getName());
        }
        if (productDto.isOnSale() != foundProduct.isOnSale()) {
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
