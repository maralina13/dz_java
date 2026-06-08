package ru.itis.shop.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import ru.itis.shop.accounts.entity.Account;

import java.util.List;
import java.util.stream.Collectors;

public class AccountDto {

    @Schema(description = "Идентификатор пользователя", example = "1")
    private final Long id;
    @Schema(description = "Имя пользователя", example = "Марсель")
    private final String firstName;
    @Schema(description = "Фамилия пользователя", example = "Сидиков")
    private final String lastName;
    @Schema(description = "Email пользователя", example = "marsel@example.com")
    private final String email;

    private AccountDto(Long id, String firstName, String lastName, String email) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public static AccountDto from(Account account) {
        return new AccountDto(account.getId(),
                account.getFirstName(),
                account.getLastName(),
                account.getEmail());
    }

    public static List<AccountDto> from(List<Account> accounts) {
        return accounts
                .stream()
                .map(AccountDto::from)
                .collect(Collectors.toList());
    }

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }
}
