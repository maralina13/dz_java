package ru.itis.shop.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import ru.itis.shop.validation.NotSameNames;

@NotSameNames(message = "Имя и фамилия не должны совпадать")
public class NewAccountDto {
    @Schema(description = "Имя пользователя", example = "Andrew")
    @NotBlank(message = "Имя не должно быть пустым")
    private String firstName;

    @Schema(description = "Фамилия пользователя", example = "Ganiyev")
    @NotBlank(message = "Фамилия не должна быть пустой")
    private String lastName;

    @Schema(description = "Email пользователя", example = "andrew@gmail.com")
    @NotBlank(message = "Email не должен быть пустым")
    @Email(message = "Некорректный формат email")
    private String email;

    @Schema(description = "Пароль пользователя", example = "Qwerty008")
    @NotBlank(message = "Пароль не должен быть пустым")
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])[A-Za-z0-9]+$", message = "Пароль должен содержать латинские буквы в верхнем и нижнем регистре")
    private String password;

    public NewAccountDto(String firstName, String lastName, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }

    public NewAccountDto() {
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
