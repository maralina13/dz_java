package ru.itis.shop.accounts.controller.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.itis.shop.accounts.dto.AccountDto;
import ru.itis.shop.accounts.dto.NewAccountDto;
import ru.itis.shop.dto.PageDto;

@RequestMapping("/api/v1/accounts")
public interface AccountApi {

    @Operation(
            summary = "Получить список аккаунтов",
            description = "Возвращает все аккаунты, которые были добавлены в базу данных."
    )
    @ApiResponse(
            description = "Страница с аккаунтами",
            responseCode = "200",
            content = @Content(schema = @Schema(implementation = PageDto.class))
    )
    @GetMapping
    PageDto<AccountDto> getAccounts(
            @Parameter(description = "Номер страницы") @RequestParam(value = "page", defaultValue = "0") int page,
            @Parameter(description = "Размер страницы") @RequestParam(value = "size", defaultValue = "10") int size,
            @Parameter(description = "Поле для сортировки") @RequestParam(value = "sort", defaultValue = "id") String sort);

    @Operation(
            summary = "Создать аккаунт",
            description = "Добавляет новый аккаунт магазина и возвращает сохраненные данные."
    )
    @ApiResponse(
            description = "Аккаунт создан",
            responseCode = "201",
            content = @Content(schema = @Schema(implementation = AccountDto.class))
    )
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    AccountDto addAccount(@RequestBody @Valid NewAccountDto newAccount);

    @Operation(
            summary = "Получить аккаунт по ID",
            description = "Возвращает один аккаунт по его идентификатору."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    description = "Аккаунт найден",
                    responseCode = "200",
                    content = @Content(schema = @Schema(implementation = AccountDto.class))
            ),
            @ApiResponse(description = "Аккаунт не найден", responseCode = "404", content = @Content)
    })
    @GetMapping("/{account-id}")
    AccountDto getAccount(
            @Parameter(description = "Идентификатор аккаунта") @PathVariable("account-id") Long accountId);
}
