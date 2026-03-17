package com.example.sms.controller.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = MaxYearValidator.class)
@Documented
public @interface MaxYear {
    String message() default "Date Of Birth Can't Be Greater Than {value}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    int value();
}
