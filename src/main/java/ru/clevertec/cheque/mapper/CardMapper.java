package ru.clevertec.cheque.mapper;

import org.mapstruct.Mapper;
import ru.clevertec.cheque.dto.CardDto;
import ru.clevertec.cheque.dto.CardSaveDto;
import ru.clevertec.cheque.model.entity.DiscountCard;

import java.util.List;

/**
 * Mapper class for cards. Used to convert <b>Entities</b> into <b>DTO</b> operations.
 *
 * @author Yuryeu Andrei
 */
@Mapper(componentModel = "spring")
public interface CardMapper {
    CardDto toDto(DiscountCard card);

    DiscountCard fromSaveDto(CardSaveDto cardSaveDto);

    List<CardDto> toDto(List<DiscountCard> cards);
}
