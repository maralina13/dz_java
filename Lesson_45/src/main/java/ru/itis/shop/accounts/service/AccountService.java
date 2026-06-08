package ru.itis.shop.accounts.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.itis.shop.accounts.dto.AccountDto;
import ru.itis.shop.accounts.dto.NewAccountDto;
import ru.itis.shop.accounts.entity.Account;
import ru.itis.shop.accounts.repository.AccountRepository;
import ru.itis.shop.dto.PageDto;
import ru.itis.shop.exceptions.NotFoundException;

import static ru.itis.shop.accounts.dto.AccountDto.from;
import static ru.itis.shop.dto.PageDto.from;

@Service
public class AccountService {

    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Transactional
    public AccountDto save(NewAccountDto newAccountDto) {
        Account newAccount = new Account();

        newAccount.setEmail(newAccountDto.getEmail());
        newAccount.setFirstName(newAccountDto.getFirstName());
        newAccount.setLastName(newAccountDto.getLastName());

        accountRepository.save(newAccount);

        return from(newAccount);
    }

    public PageDto<AccountDto> getAccounts(int page, int size, String sort) {
        Page<Account> accountPage = accountRepository.findAll(
                PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, sort)));

        return from(from(accountPage.getContent()), accountPage.getTotalPages(), accountPage.getTotalElements());
    }

    public AccountDto getAccount(Long accountId) {
        return from(getAccountEntity(accountId));
    }

    public Account getAccountEntity(Long accountId) {
        return accountRepository.findById(accountId)
                .orElseThrow(() -> new NotFoundException("Account with id <" + accountId + "> not found."));
    }

    public void checkAccountExists(Long accountId) {
        if (!accountRepository.existsById(accountId)) {
            throw new NotFoundException("Account with id <" + accountId + "> not found.");
        }
    }
}
