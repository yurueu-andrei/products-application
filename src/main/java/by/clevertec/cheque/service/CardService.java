package by.clevertec.cheque.service;

import by.clevertec.cheque.dto.CardDto;
import by.clevertec.cheque.dto.CardSaveDto;
import by.clevertec.cheque.mapper.CardMapper;
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
    private final CardMapper cardMapper;

    public CardDto findById(Long id) throws ServiceException {
        return cardRepository.findById(id).map(cardMapper::toDto)
                .orElseThrow(() -> new ServiceException(String.format("The card was not found. id = %d", id)));
    }

    @Transactional(readOnly = true)
    public List<CardDto> findAll() throws ServiceException {
        try {
            return cardMapper.toDto(cardRepository.findAll());
        } catch (Exception ex) {
            throw new ServiceException("The cards were not found", ex);
        }
    }

    @Transactional
    public CardDto add(int discount) throws ServiceException {
        try {
            DiscountCard card = new DiscountCard();
            card.setDiscount(discount);
            return cardMapper.toDto(cardRepository.save(card));
        } catch (Exception ex) {
            throw new ServiceException(String.format("The card was not saved. Discount value: %s", discount), ex);
        }
    }

    @Transactional
    public boolean update(CardDto cardDto) throws ServiceException {
        DiscountCard foundDiscountCard = cardRepository.findById(cardDto.getId())
                .orElseThrow(() ->
                        new ServiceException(
                                String.format(
                                        "The card was not updated. The card was not found. id = %d",
                                        cardDto.getId())));
        try {
            if (cardDto.getDiscount() != null) {
                foundDiscountCard.setDiscount(cardDto.getDiscount());
            }

            cardRepository.flush();
            return true;
        } catch (Exception ex) {
            throw new ServiceException(String.format("The card was not updated. %s", cardDto), ex);
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
