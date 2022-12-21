package by.clevertec.cheque.controller;

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
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/cards")
public class CardController {
    private final CardService cardService;

    @GetMapping("/{id}")
    public DiscountCard findById(@PathVariable Long id) throws ServiceException {
        return cardService.findById(id);
    }

    @GetMapping
    public List<DiscountCard> findAll() throws ServiceException {
        return cardService.findAll();
    }

    @PostMapping
    public DiscountCard add(
            @RequestBody DiscountCard card
    ) throws ServiceException {
        return cardService.add(card);
    }

    @PutMapping
    public DiscountCard update(
            @RequestBody DiscountCard card
    ) throws ServiceException {
        cardService.update(card);
        return card;
    }

    @DeleteMapping("/{id}")
    public boolean delete(@PathVariable Long id) throws ServiceException {
        return cardService.delete(id);
    }
}
