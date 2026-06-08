package ru.itis.shop.accounts.dto;

public class AccountDto {

    private final String id;
    private final String firstName;
    private final String lastName;
    private final String email;

    public AccountDto(String id, String firstName, String lastName, String email) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public String getId() {
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
