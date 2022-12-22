package by.clevertec.cheque.mapper;

import by.clevertec.cheque.dto.CardDto;
import by.clevertec.cheque.dto.CardSaveDto;
import by.clevertec.cheque.model.entity.DiscountCard;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CardMapper {
    CardDto toDto(DiscountCard card);
    DiscountCard fromSaveDto(CardSaveDto cardSaveDto);
    List<CardDto> toDto(List<DiscountCard> cards);
}
