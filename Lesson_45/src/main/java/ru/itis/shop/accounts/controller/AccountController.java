package ru.itis.shop.accounts.controller;

import org.springframework.web.bind.annotation.RestController;
import ru.itis.shop.accounts.controller.api.AccountApi;
import ru.itis.shop.accounts.dto.AccountDto;
import ru.itis.shop.accounts.dto.NewAccountDto;
import ru.itis.shop.accounts.service.AccountService;
import ru.itis.shop.dto.PageDto;

@RestController
public class AccountController implements AccountApi {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    public PageDto<AccountDto> getAccounts(int page, int size, String sort) {
        return accountService.getAccounts(page, size, sort);
    }

    @Override
    public AccountDto addAccount(NewAccountDto newAccount) {
        return accountService.save(newAccount);
    }

    @Override
    public AccountDto getAccount(Long accountId) {
        return accountService.getAccount(accountId);
    }
}
