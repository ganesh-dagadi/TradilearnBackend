package com.virtconf.paperTrader.controller;

import com.virtconf.paperTrader.dto.AddMoneyRequestDTO;
import com.virtconf.paperTrader.service.TransactionService;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.http.HttpHeaders;

@RestController
@RequestMapping("/papertrader/transaction")
public class TransactionController {
    @Autowired
    private TransactionService service;
    @PatchMapping("/addmoney")
    public void addMoney(HttpServletRequest request , @RequestBody String requestData){
        System.out.print(requestData);
        System.out.print(request.getHeader("AUTH_USERNAME"));
//        service.addMoneyToAccount(requestData);
    }
}
