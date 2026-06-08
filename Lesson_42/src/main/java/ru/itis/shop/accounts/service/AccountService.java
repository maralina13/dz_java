package ru.itis.shop.accounts.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.itis.shop.accounts.dto.AccountDto;
import ru.itis.shop.accounts.dto.NewAccountDto;
import ru.itis.shop.accounts.entity.Account;
import ru.itis.shop.accounts.repository.AccountRepository;

import java.util.List;

@Service
public class AccountService {

    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Transactional(readOnly = true)
    public List<AccountDto> getAccounts() {
        return accountRepository.findAll()
                .stream()
                .map(this::toDto)
                .toList();
    }

    @Transactional
    public AccountDto addAccount(NewAccountDto newAccountDto) {
        Account account = new Account();
        account.setFirstName(newAccountDto.getFirstName());
        account.setLastName(newAccountDto.getLastName());
        account.setEmail(newAccountDto.getEmail());
        account.setPassword(newAccountDto.getPassword());

        return toDto(accountRepository.save(account));
    }

    private AccountDto toDto(Account account) {
        return new AccountDto(
                String.valueOf(account.getId()),
                account.getFirstName(),
                account.getLastName(),
                account.getEmail()
        );
    }
}
