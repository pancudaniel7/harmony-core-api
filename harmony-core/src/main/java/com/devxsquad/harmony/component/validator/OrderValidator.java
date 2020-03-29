package com.devxsquad.harmony.component.validator;

import java.util.HashSet;
import java.util.Set;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import com.devxsquad.harmony.component.validator.constraint.OrderConstraint;
import com.devxsquad.harmony.model.dto.OrderDto;
import lombok.RequiredArgsConstructor;

import static java.util.Objects.isNull;
import static org.springframework.util.CollectionUtils.isEmpty;


@RequiredArgsConstructor
public class OrderValidator implements ConstraintValidator<OrderConstraint, OrderDto> {

    private final Validator validator;

    @Override
    public boolean isValid(OrderDto value, ConstraintValidatorContext validationContext) {

        Set<ConstraintViolation<Object>> orderViolations = new HashSet<>();
        if (!isNull(validationContext)) {
            orderViolations.addAll(validator.validate(value));
        }
        if (!isNull(value.getOrderBy())) {
            orderViolations.addAll(validator.validate(value.getOrderBy()));
        }
        if (!isNull(value.getOrderItems())) {
            value.getOrderItems().forEach(orderItemDto -> orderViolations.addAll(validator.validate(orderItemDto)));
        }
        if (!isEmpty(orderViolations)) {
            validationContext.disableDefaultConstraintViolation();
            orderViolations.forEach(violation ->
                validationContext
                    .buildConstraintViolationWithTemplate(violation.getMessageTemplate())
                    .addConstraintViolation());
            return false;
        }

        return true;
    }
}
