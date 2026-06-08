package ru.itis.shop.accounts.controller;

import org.springframework.web.bind.annotation.*;
import ru.itis.shop.accounts.dto.AccountDto;
import ru.itis.shop.accounts.dto.NewAccountDto;
import ru.itis.shop.accounts.service.AccountService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/accounts")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping
    public List<AccountDto> getAccounts() {
        return accountService.getAccounts();
    }

    @PostMapping
    public AccountDto addAccount(@RequestBody NewAccountDto newAccount) {
        return accountService.addAccount(newAccount);
    }
}
