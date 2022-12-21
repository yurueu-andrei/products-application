package by.clevertec.cheque.service;

import by.clevertec.cheque.model.entity.DiscountCard;
import by.clevertec.cheque.repository.CardRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.service.spi.ServiceException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CardService {
    private final CardRepository cardRepository;

    public DiscountCard findById(Long id) throws ServiceException {
        return cardRepository.findById(id)
                .orElseThrow(() -> new ServiceException(String.format("The card was not found. id = %d", id)));
    }

    @Transactional(readOnly = true)
    public List<DiscountCard> findAll() throws ServiceException {
        try {
            return cardRepository.findAll();
        } catch (Exception ex) {
            throw new ServiceException("The cards were not found", ex);
        }
    }

    @Transactional
    public DiscountCard add(DiscountCard discountCard) throws ServiceException {
        try {
            return cardRepository.save(discountCard);
        } catch (Exception ex) {
            throw new ServiceException(String.format("The card was not saved. %s", discountCard), ex);
        }
    }

    @Transactional
    public boolean update(DiscountCard discountCard) throws ServiceException {
        DiscountCard foundDiscountCard = cardRepository.findById(discountCard.getId())
                .orElseThrow(() ->
                        new ServiceException(
                                String.format(
                                        "The card was not updated. The card was not found. id = %d",
                                        discountCard.getId())));
        try {
            if (discountCard.getDiscount() != null) {
                foundDiscountCard.setDiscount(discountCard.getDiscount());
            }
            cardRepository.flush();
            return true;
        } catch (Exception ex) {
            throw new ServiceException(String.format("The card was not updated. %s", discountCard), ex);
        }
    }

    @Transactional
    public boolean delete(Long id) throws ServiceException {
        DiscountCard discountCard = cardRepository.findById(id)
                .orElseThrow(() ->
                        new ServiceException(
                                String.format(
                                        "The card was not deleted. The card was not found. id = %d", id)));
        try {
            cardRepository.delete(discountCard);
            return true;
        } catch (Exception ex) {
            throw new ServiceException(String.format("The card was not deleted. id = %d", id), ex);
        }
    }
}
