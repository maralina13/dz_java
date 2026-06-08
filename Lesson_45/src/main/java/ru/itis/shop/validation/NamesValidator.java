package ru.itis.shop.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import ru.itis.shop.accounts.dto.NewAccountDto;

public class NamesValidator implements ConstraintValidator<NotSameNames, NewAccountDto> {

    @Override
    public boolean isValid(NewAccountDto value, ConstraintValidatorContext context) {
        if (value == null || value.getFirstName() == null || value.getLastName() == null) {
            return true;
        }

        return !value.getFirstName().equals(value.getLastName());
    }
}
