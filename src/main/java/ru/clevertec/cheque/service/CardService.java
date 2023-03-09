package ru.clevertec.cheque.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.clevertec.cheque.dto.CardDto;
import ru.clevertec.cheque.exception.ServiceException;
import ru.clevertec.cheque.mapper.CardMapper;
import ru.clevertec.cheque.model.entity.DiscountCard;
import ru.clevertec.cheque.repository.CardRepository;

import java.util.List;

/**
 * Service class for discount cards with <b>CRUD</b> operations and wrapping into <b>DTO</b>.
 *
 * @author Yuryeu Andrei
 */
@RequiredArgsConstructor
@Service
public class CardService {
    /**
     * Card repository field
     *
     * @see CardRepository
     */
    private final CardRepository cardRepository;
    /**
     * Card mapper field
     *
     * @see CardMapper
     */
    private final CardMapper cardMapper;

    /**
     * Method for finding card by its ID
     *
     * @param id ID of target entity
     * @return returns a <b>DTO</b> made out of found Entity
     * @throws ServiceException in case of <b>null</b> ID
     */
    public CardDto findById(Long id) throws ServiceException {
        return cardRepository.findById(id).map(cardMapper::toDto)
                .orElseThrow(() -> new ServiceException(String.format("The card was not found. id = %d", id)));
    }

    /**
     * Method for finding all cards
     *
     * @return returns a <b>list of DTOs</b> made out of found Entities
     */
    @Transactional(readOnly = true)
    public List<CardDto> findAll() throws ServiceException {
        try {
            return cardMapper.toDto(cardRepository.findAll());
        } catch (Exception ex) {
            throw new ServiceException("The cards were not found", ex);
        }
    }

    /**
     * Method for adding a new card
     *
     * @param discount discount value for the new card
     * @return returns a DTO of added card<b>(contains generated ID)</b>
     * @throws ServiceException in case of <b>null</b> discount
     */
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

    /**
     * Method for updating the existing card
     *
     * @param cardDto DTO object with target ID and field(s) <b>to be updated</b>
     * @return <b>true</b> - in case of successful update
     * @throws ServiceException in case of update of nonexistent card
     */
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

    /**
     * Method for deleting a card
     *
     * @param id ID of target entity
     * @return <b>true</b> - in case of successful deletion
     * @throws ServiceException in case of deletion of <b>nonexistent</b> card or <b>null</b> ID
     */
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
