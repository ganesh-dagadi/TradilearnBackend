package com.virtconf.paperTrader.service;

import com.virtconf.paperTrader.dto.AddMoneyRequestDTO;
import com.virtconf.paperTrader.dto.BuySellStockRequestDTO;
import com.virtconf.paperTrader.dto.PersonDataDTO;
import com.virtconf.paperTrader.dto.StockLivePriceResponseDTO;
import com.virtconf.paperTrader.model.*;
import com.virtconf.paperTrader.repository.*;
import com.virtconf.paperTrader.utils.error.BadRequest;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Optional;
@Service
public class TransactionServiceImpl implements TransactionService{
    private static final Float HARD_TO_BORROW_RATE = 30.0F;
    @Autowired
    AccountRepository accountRepo;
    @Autowired
    AccountTransactionRepo accTransactionRepo;
    @Autowired
    StockTransactionRepo stockTransactionRepo;
    @Autowired
    DeductibleRepo deductibleRepo;

    @Autowired
    HoldingRepo holdingRepo;

    @Override
    public String addMoneyToAccount(PersonDataDTO user , AddMoneyRequestDTO requestData) throws Exception {
        Optional<Account> accountOp = accountRepo.findByPersonId(user.getId());
        Account account;
        if(accountOp.isEmpty()){
            account = new Account();
            account.setBalance(requestData.getAmount());
            account.setAmountAdded(requestData.getAmount());
            account.setPersonId(user.getId());
        }else{
            account = accountOp.get();
            account.setBalance(account.getBalance() + requestData.getAmount());
            account.setAmountAdded(account.getAmountAdded() + requestData.getAmount());
        }
        AccountTransaction transaction = new AccountTransaction();
        transaction.setAccount(account);
        transaction.setAmount(requestData.getAmount());
        transaction.setCredit(true);
        account.getAccountTransactions().add(transaction);
        saveAsTransaction(transaction , account);
        return "Amount Added";
    }

    @Override
    public String buyStock(PersonDataDTO user, BuySellStockRequestDTO requestData) throws BadRequest {
        String symbol = requestData.getSymbol().toUpperCase();
        WebClient client = WebClient.create("http://localhost:8080");
        StockLivePriceResponseDTO priceResponse = client
                .get()
                .uri("/stockprice/liveprice?ticker="+symbol)
                .retrieve()
                .bodyToMono(StockLivePriceResponseDTO.class)
                .block();
        Float price = priceResponse.getLivePrice();
        Optional<Account> accountOp = accountRepo.findByPersonId(user.getId());
        if(accountOp.isEmpty()) throw new RuntimeException();
        Account account = accountOp.get();
        if(requestData.getIsLong() && account.getBalance() < price * requestData.getQty()) throw new BadRequest("Insufficient Balance");
        Holding existingHolding = null;
        for(int i = 0 ; i < account.getHoldings().size() ; i++){
            if(account.getHoldings().get(i).getSymbol().equals(symbol) && account.getHoldings().get(i).getIsLong().equals(requestData.getIsLong())){
                existingHolding = account.getHoldings().get(i);
            }
        }

        if(existingHolding != null){
            if(requestData.getIsLong()){
                AccountTransaction accountTransaction = new AccountTransaction();
                accountTransaction.setCredit(false);
                accountTransaction.setAmount(price * requestData.getQty());
                accountTransaction.setAccount(account);

                account.setBalance(account.getBalance() - (price * requestData.getQty()));

                StockTransaction stockTransaction = new StockTransaction();
                stockTransaction.setAccount(account);
                stockTransaction.setBuy(true);
                stockTransaction.setQty(requestData.getQty());
                stockTransaction.setLong(requestData.getIsLong());
                stockTransaction.setUnitValue(price);
                stockTransaction.setSymbol(symbol);
                Optional<Holding> holdingOp = holdingRepo.findByAccountIdSymbolAndLong(account ,symbol, requestData.getIsLong());
                if(holdingOp.isEmpty()) throw new RuntimeException();
                Float newUnitVal = ((existingHolding.getUnitVal() * existingHolding.getQty()) + (price * requestData.getQty()))/(existingHolding.getQty() + requestData.getQty());
                Holding holding  = holdingOp.get();
                holding.setUnitVal(newUnitVal);
                holding.setQty(existingHolding.getQty() + requestData.getQty());
                saveAsTransaction(accountTransaction , stockTransaction , account , holding);
            }else{
                //short buy
                StockTransaction stockTransaction = new StockTransaction();
                stockTransaction.setAccount(account);
                stockTransaction.setBuy(true);
                stockTransaction.setQty(requestData.getQty());
                stockTransaction.setLong(requestData.getIsLong());
                stockTransaction.setUnitValue(price);
                stockTransaction.setSymbol(symbol);
                Optional<Holding> holdingOp = holdingRepo.findByAccountIdSymbolAndLong(account , symbol, requestData.getIsLong());
                if(holdingOp.isEmpty()) throw new RuntimeException();
                Float newUnitVal = ((existingHolding.getUnitVal() * existingHolding.getQty()) + (price * requestData.getQty()))/(existingHolding.getQty() + requestData.getQty());
                Holding holding  = holdingOp.get();
                holding.setUnitVal(newUnitVal);
                holding.setQty(existingHolding.getQty() + requestData.getQty());
                Optional<Deductible> deductibleOp = deductibleRepo.findByAccount(account);
                if(deductibleOp.isEmpty()) throw new RuntimeException();
                Deductible deductible = deductibleOp.get();
                deductible.setAmount(deductible.getAmount() + (holding.getUnitVal() * holding.getQty()) * (HARD_TO_BORROW_RATE /100));
                saveAsTransaction(stockTransaction , deductible , holding);
            }
        }else{
            if(requestData.getIsLong()){
                StockTransaction stockTransaction = new StockTransaction();
                stockTransaction.setAccount(account);
                stockTransaction.setBuy(true);
                stockTransaction.setQty(requestData.getQty());
                stockTransaction.setLong(requestData.getIsLong());
                stockTransaction.setUnitValue(price);
                stockTransaction.setSymbol(symbol);

                AccountTransaction accountTransaction = new AccountTransaction();
                accountTransaction.setCredit(false);
                accountTransaction.setAmount(price * requestData.getQty());
                accountTransaction.setAccount(account);

                account.setBalance(account.getBalance() - (price * requestData.getQty()));
                Holding newHolding = new Holding();
                newHolding.setQty(requestData.getQty());
                newHolding.setUnitVal(price);
                newHolding.setAccountId(account);
                newHolding.setIsLong(requestData.getIsLong());
                newHolding.setSymbol(symbol);
                saveAsTransaction(accountTransaction , stockTransaction , account , newHolding);

            }else{
                StockTransaction stockTransaction = new StockTransaction();
                stockTransaction.setAccount(account);
                stockTransaction.setBuy(true);
                stockTransaction.setQty(requestData.getQty());
                stockTransaction.setLong(requestData.getIsLong());
                stockTransaction.setUnitValue(price);
                stockTransaction.setSymbol(symbol);

                Holding newHolding = new Holding();
                newHolding.setQty(requestData.getQty());
                newHolding.setUnitVal(price);
                newHolding.setAccountId(account);
                newHolding.setIsLong(requestData.getIsLong());
                newHolding.setSymbol(symbol);

                Deductible deductible;
                Optional<Deductible> deductibleOp = deductibleRepo.findByAccount(account);
                if(deductibleOp.isEmpty()){
                    deductible = new Deductible();
                    deductible.setAmount((newHolding.getUnitVal() * newHolding.getQty()) * HARD_TO_BORROW_RATE/100);
                    deductible.setAccount(account);
                }else{
                    deductible = deductibleOp.get();
                    deductible.setAmount(deductible.getAmount() + (newHolding.getUnitVal() * newHolding.getQty()) * HARD_TO_BORROW_RATE/100);
                }
                saveAsTransaction(stockTransaction , deductible , newHolding);
            }
        }
        return "brought";
    }

    @Override
    public String sellStock(PersonDataDTO user, BuySellStockRequestDTO requestData) throws BadRequest {
        String symbol = requestData.getSymbol().toUpperCase();
        Optional<Account> accountOp = accountRepo.findByPersonId(user.getId());
        if(accountOp.isEmpty()) throw new RuntimeException();
        Account account = accountOp.get();
        Optional<Holding> holdingOp = holdingRepo.findByAccountIdSymbolAndLong(account , symbol , requestData.getIsLong());
        if(holdingOp.isEmpty()) throw new BadRequest("Holding not found");
        Holding holding = holdingOp.get();
        if(holding.getQty() < requestData.getQty()) throw new BadRequest("Cant sell more than you own");
        WebClient client = WebClient.create("http://localhost:8080");
        StockLivePriceResponseDTO priceResponse = client
                .get()
                .uri("/stockprice/liveprice?ticker="+symbol)
                .retrieve()
                .bodyToMono(StockLivePriceResponseDTO.class)
                .block();
        Float price = priceResponse.getLivePrice();
        if(requestData.getIsLong()){
            AccountTransaction accountTransaction = new AccountTransaction();
            accountTransaction.setAccount(account);
            accountTransaction.setAmount(price * requestData.getQty());
            accountTransaction.setCredit(true);

            StockTransaction stockTransaction = new StockTransaction();
            stockTransaction.setAccount(account);
            stockTransaction.setBuy(false);
            stockTransaction.setQty(requestData.getQty());
            stockTransaction.setLong(requestData.getIsLong());
            stockTransaction.setUnitValue(price);
            stockTransaction.setSymbol(symbol);

            holding.setQty(holding.getQty() - requestData.getQty());
            account.setBalance(account.getBalance() + (price * requestData.getQty()));
            saveAsTransaction(accountTransaction , stockTransaction , account , holding);
        }else{
            AccountTransaction accountTransaction = new AccountTransaction();
            accountTransaction.setAccount(account);
            accountTransaction.setAmount((holding.getUnitVal() - price) * requestData.getQty());
            accountTransaction.setCredit(true);

            StockTransaction stockTransaction = new StockTransaction();
            stockTransaction.setAccount(account);
            stockTransaction.setBuy(false);
            stockTransaction.setQty(requestData.getQty());
            stockTransaction.setLong(requestData.getIsLong());
            stockTransaction.setUnitValue(price);
            stockTransaction.setSymbol(symbol);

            holding.setQty(holding.getQty() - requestData.getQty());
            Optional<Deductible> deductibleOp = deductibleRepo.findByAccount(account);
            if(deductibleOp.isEmpty()) throw new RuntimeException();
            Deductible deductible = deductibleOp.get();
            deductible.setAmount(deductible.getAmount() - (holding.getUnitVal() * requestData.getQty()) * HARD_TO_BORROW_RATE/100);
            saveAsTransaction(stockTransaction , deductible , holding);
        }
        return "Sold Stock";
    }

    @Transactional
    private void saveAsTransaction(AccountTransaction transaction , Account account) throws Exception{
        accountRepo.save(account);
        accTransactionRepo.save(transaction);
    }
    @Transactional
    private void saveAsTransaction(AccountTransaction accountTransaction , StockTransaction stockTransaction , Account account , Holding holding){
        accountRepo.save(account);
        accTransactionRepo.save(accountTransaction);
        stockTransactionRepo.save(stockTransaction);
        holdingRepo.save(holding);
    }
    @Transactional
    private void saveAsTransaction( StockTransaction stockTransaction , Deductible deductible , Holding holding){
        stockTransactionRepo.save(stockTransaction);
        holdingRepo.save(holding);
        deductibleRepo.save(deductible);
    }
}
