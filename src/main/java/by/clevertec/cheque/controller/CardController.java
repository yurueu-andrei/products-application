package by.clevertec.cheque.controller;

import by.clevertec.cheque.dto.CardDto;
import by.clevertec.cheque.dto.CardSaveDto;
import by.clevertec.cheque.model.entity.DiscountCard;
import by.clevertec.cheque.service.CardService;
import lombok.RequiredArgsConstructor;
import org.hibernate.service.spi.ServiceException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/cards")
public class CardController {
    private final CardService cardService;

    @GetMapping("/{id}")
    public CardDto findById(@PathVariable Long id) {
        return cardService.findById(id);
    }

    @GetMapping
    public List<CardDto> findAll() {
        return cardService.findAll();
    }

    @PostMapping
    public CardDto add(
            @RequestParam int discountValue
    ) {
        return cardService.add(discountValue);
    }

    @PutMapping
    public boolean update(
            @RequestBody CardDto card
    ) {
        return cardService.update(card);
    }

    @DeleteMapping("/{id}")
    public boolean delete(@PathVariable Long id) {
        return cardService.delete(id);
    }
}
