package ru.itis.shop.accounts.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import ru.itis.shop.accounts.dto.AccountDto;
import ru.itis.shop.accounts.dto.NewAccountDto;
import ru.itis.shop.accounts.entity.Account;
import ru.itis.shop.accounts.repository.AccountRepository;
import ru.itis.shop.dto.PageDto;
import ru.itis.shop.exceptions.NotFoundException;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class AccountServiceTest {

    @Mock
    AccountRepository accountRepository;

    @InjectMocks
    AccountService accountService;

    @Nested
    @DisplayName("save() works ...")
    class Save {

        @Test
        void save_returns_created_account_with_id() {
            NewAccountDto newAccount = new NewAccountDto("Andrew", "Ganiyev", "andrew@gmail.com", "Qwerty008");

            when(accountRepository.save(any(Account.class))).thenAnswer(invocation -> {
                Account account = invocation.getArgument(0);
                account.setId(1L);
                return account;
            });

            AccountDto actualAccount = accountService.save(newAccount);

            ArgumentCaptor<Account> accountCaptor = ArgumentCaptor.forClass(Account.class);
            verify(accountRepository).save(accountCaptor.capture());

            assertAll(
                    () -> assertEquals(1L, actualAccount.getId()),
                    () -> assertEquals("Andrew", actualAccount.getFirstName()),
                    () -> assertEquals("Ganiyev", actualAccount.getLastName()),
                    () -> assertEquals("andrew@gmail.com", actualAccount.getEmail()),
                    () -> assertEquals("Andrew", accountCaptor.getValue().getFirstName()),
                    () -> assertEquals("Ganiyev", accountCaptor.getValue().getLastName()),
                    () -> assertEquals("andrew@gmail.com", accountCaptor.getValue().getEmail())
            );
        }
    }

    @Nested
    @DisplayName("getAccount() works ...")
    class GetAccount {

        @Test
        void get_account_returns_correct_account() {
            Account account = new Account(1L, "Andrew", "Ganiyev", "andrew@gmail.com");

            when(accountRepository.findById(1L)).thenReturn(Optional.of(account));

            AccountDto actualAccount = accountService.getAccount(1L);

            assertAll(
                    () -> assertEquals(1L, actualAccount.getId()),
                    () -> assertEquals("Andrew", actualAccount.getFirstName()),
                    () -> assertEquals("Ganiyev", actualAccount.getLastName()),
                    () -> assertEquals("andrew@gmail.com", actualAccount.getEmail())
            );
        }

        @Test
        void get_account_throws_not_found_when_wrong_id() {
            when(accountRepository.findById(99L)).thenReturn(Optional.empty());

            NotFoundException exception = assertThrows(NotFoundException.class, () -> accountService.getAccount(99L));

            assertEquals("Account with id <99> not found.", exception.getMessage());
        }
    }

    @Nested
    @DisplayName("getAccounts() works ...")
    class GetAccounts {

        @Test
        void get_accounts_returns_page_with_accounts() {
            Account account = new Account(1L, "Andrew", "Ganiyev", "andrew@gmail.com");
            PageRequest pageRequest = PageRequest.of(0, 10, Sort.by(Sort.Direction.ASC, "id"));

            when(accountRepository.findAll(pageRequest)).thenReturn(new PageImpl<>(List.of(account), pageRequest, 1));

            PageDto<AccountDto> actualPage = accountService.getAccounts(0, 10, "id");

            assertAll(
                    () -> assertEquals(1, actualPage.getElements().size()),
                    () -> assertEquals(1, actualPage.getTotalPages()),
                    () -> assertEquals(1L, actualPage.getTotalElements()),
                    () -> assertEquals("Andrew", actualPage.getElements().get(0).getFirstName())
            );
        }
    }
}
