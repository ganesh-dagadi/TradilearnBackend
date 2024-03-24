package com.virtconf.paperTrader.controller;

import com.virtconf.paperTrader.dto.AddMoneyRequestDTO;
import com.virtconf.paperTrader.dto.BuySellStockRequestDTO;
import com.virtconf.paperTrader.dto.PersonDataDTO;
import com.virtconf.paperTrader.service.TransactionService;
import com.virtconf.paperTrader.utils.error.BadRequest;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/papertrader/transaction")
public class TransactionController {
    @Autowired
    private TransactionService service;
    @PatchMapping("/addmoney")
    public String addMoney(HttpServletRequest request , @RequestBody AddMoneyRequestDTO requestData) throws Exception {
        PersonDataDTO user = createUserFromHeaders(request);
        return service.addMoneyToAccount(user , requestData);
    }
    @PostMapping("/buy")
    public String buyStock(HttpServletRequest request , @RequestBody BuySellStockRequestDTO requestData) throws BadRequest {
        PersonDataDTO user = createUserFromHeaders(request);
        return service.buyStock(user , requestData);
    }

    @PatchMapping("/sell")
    public String sellStock(HttpServletRequest request , @RequestBody BuySellStockRequestDTO requestData) throws BadRequest {
        PersonDataDTO user = createUserFromHeaders(request);
        return service.sellStock(user , requestData);
    }

    private PersonDataDTO createUserFromHeaders(HttpServletRequest request){
        PersonDataDTO user = new PersonDataDTO();
        user.setEmail(request.getHeader("AUTH_EMAIL"));
        user.setId(Integer.parseInt(request.getHeader("AUTH_USERID")));
        user.setUsername(request.getHeader("AUTH_USERNAME"));
        user.setEmailVerified(Boolean.getBoolean(request.getHeader("AUTH_VERIFIED")));
        user.setActive(Boolean.getBoolean(request.getHeader("AUTH_ACTIVE")));
        return user;
    }
}
