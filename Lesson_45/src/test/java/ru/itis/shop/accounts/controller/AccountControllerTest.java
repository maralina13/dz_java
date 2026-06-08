package ru.itis.shop.accounts.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.itis.shop.accounts.dto.AccountDto;
import ru.itis.shop.accounts.entity.Account;
import ru.itis.shop.accounts.service.AccountService;
import ru.itis.shop.dto.PageDto;
import ru.itis.shop.exceptions.NotFoundException;

import java.util.List;

import static org.hamcrest.Matchers.hasKey;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AccountController.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class AccountControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockitoBean
    AccountService accountService;

    @Nested
    @DisplayName("GET /api/v1/accounts/{account-id} works ...")
    class GetAccount {

        @Test
        void get_account_returns_full_json() throws Exception {
            AccountDto account = AccountDto.from(new Account(1L, "Andrew", "Ganiyev", "andrew@gmail.com"));

            when(accountService.getAccount(1L)).thenReturn(account);

            mockMvc.perform(get("/api/v1/accounts/1"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id").value(1))
                    .andExpect(jsonPath("$.firstName").value("Andrew"))
                    .andExpect(jsonPath("$.lastName").value("Ganiyev"))
                    .andExpect(jsonPath("$.email").value("andrew@gmail.com"));
        }

        @Test
        void get_account_returns_not_found_when_wrong_id() throws Exception {
            when(accountService.getAccount(anyLong())).thenThrow(new NotFoundException("Account with id <99> not found."));

            mockMvc.perform(get("/api/v1/accounts/99"))
                    .andExpect(status().isNotFound())
                    .andExpect(jsonPath("$.message").value("Account with id <99> not found."))
                    .andExpect(jsonPath("$.timestamp").exists());
        }
    }

    @Nested
    @DisplayName("GET /api/v1/accounts works ...")
    class GetAccounts {

        @Test
        void get_accounts_returns_full_page_json() throws Exception {
            AccountDto account = AccountDto.from(new Account(1L, "Andrew", "Ganiyev", "andrew@gmail.com"));
            PageDto<AccountDto> page = PageDto.from(List.of(account), 1, 1);

            when(accountService.getAccounts(0, 10, "id")).thenReturn(page);

            mockMvc.perform(get("/api/v1/accounts"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.elements[0].id").value(1))
                    .andExpect(jsonPath("$.elements[0].firstName").value("Andrew"))
                    .andExpect(jsonPath("$.elements[0].lastName").value("Ganiyev"))
                    .andExpect(jsonPath("$.elements[0].email").value("andrew@gmail.com"))
                    .andExpect(jsonPath("$.totalPages").value(1))
                    .andExpect(jsonPath("$.totalElements").value(1));
        }
    }

    @Nested
    @DisplayName("POST /api/v1/accounts works ...")
    class AddAccount {

        @Test
        void add_account_returns_created_account_full_json() throws Exception {
            AccountDto account = AccountDto.from(new Account(1L, "Andrew", "Ganiyev", "andrew@gmail.com"));

            when(accountService.save(any())).thenReturn(account);

            mockMvc.perform(post("/api/v1/accounts")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("""
                                    {
                                      "firstName": "Andrew",
                                      "lastName": "Ganiyev",
                                      "email": "andrew@gmail.com",
                                      "password": "Qwerty008"
                                    }
                                    """))
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("$.id").value(1))
                    .andExpect(jsonPath("$.firstName").value("Andrew"))
                    .andExpect(jsonPath("$.lastName").value("Ganiyev"))
                    .andExpect(jsonPath("$.email").value("andrew@gmail.com"));
        }

        @Test
        void add_account_returns_validation_errors_when_body_is_invalid() throws Exception {
            mockMvc.perform(post("/api/v1/accounts")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("""
                                    {
                                      "firstName": "",
                                      "lastName": "Ganiyev",
                                      "email": "andrew",
                                      "password": "qwerty008"
                                    }
                                    """))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.message").value("Validation errors"))
                    .andExpect(jsonPath("$.errors", hasKey("firstName")))
                    .andExpect(jsonPath("$.errors", hasKey("email")))
                    .andExpect(jsonPath("$.errors", hasKey("password")))
                    .andExpect(jsonPath("$.timestamp").exists());
        }

        @Test
        void add_account_returns_validation_error_when_first_name_and_last_name_are_same() throws Exception {
            mockMvc.perform(post("/api/v1/accounts")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("""
                                    {
                                      "firstName": "Andrew",
                                      "lastName": "Andrew",
                                      "email": "andrew@gmail.com",
                                      "password": "Qwerty008"
                                    }
                                    """))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.message").value("Validation errors"))
                    .andExpect(jsonPath("$.errors.Object").value("Имя и фамилия не должны совпадать"))
                    .andExpect(jsonPath("$.timestamp").exists());
        }
    }
}
