package com.virtconf.paperTrader.controller;

import com.virtconf.paperTrader.dto.*;
import com.virtconf.paperTrader.service.AccountInfoService;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/papertrader/account")
public class AccountInfoController {

    @Autowired
    AccountInfoService accountInfoService;
    @GetMapping("/balance")
    public BalanceResponseDTO getBalance(HttpServletRequest request){
        PersonDataDTO user = createUserFromHeaders(request);
        return accountInfoService.getBalance(user);
    }

    @GetMapping("/transaction")
    public TransactionResponseDTO getTransactions(HttpServletRequest request){
        PersonDataDTO user = createUserFromHeaders(request);
        return accountInfoService.getTransactions(user);
    }

    @GetMapping("/holdings")
    public HoldingResponseDTO getHoldings(HttpServletRequest request){
        PersonDataDTO user = createUserFromHeaders(request);
        return accountInfoService.getHoldings(user);
    }

    @GetMapping("/info")
    public AccountInfoDTO getAccountInfo(HttpServletRequest request){
        PersonDataDTO user = createUserFromHeaders(request);
        return accountInfoService.getAccountInfo(user);
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
