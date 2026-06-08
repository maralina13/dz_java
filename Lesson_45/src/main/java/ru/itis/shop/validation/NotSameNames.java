package ru.itis.shop.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = NamesValidator.class)
public @interface NotSameNames {

    String message() default "FirstName and LastName are same";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
