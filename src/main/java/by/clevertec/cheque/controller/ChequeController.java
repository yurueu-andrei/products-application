package by.clevertec.cheque.controller;

import by.clevertec.cheque.model.Cheque;
import by.clevertec.cheque.util.ChequeBuilder;
import lombok.RequiredArgsConstructor;
import org.hibernate.service.spi.ServiceException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/cheque")
public class ChequeController {
    private final ChequeBuilder chequeBuilder;

    @GetMapping
    public Cheque createCheque(@RequestParam Map<String, String> form) throws ServiceException {
        return chequeBuilder.buildCheque(form);
    }
}
