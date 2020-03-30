package com.devxsquad.harmony.component.validator.constraint;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;
import com.devxsquad.harmony.component.validator.OrderValidator;

import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Target(PARAMETER)
@Retention(RUNTIME)
@Constraint(validatedBy = OrderValidator.class)
public @interface OrderConstraint {

    String message() default "Invalid Order data structure";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
