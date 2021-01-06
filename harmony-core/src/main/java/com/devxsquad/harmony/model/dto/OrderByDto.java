package com.devxsquad.harmony.model.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import lombok.Data;

@Data
public class OrderByDto {

    @Email
    @NotEmpty(message = "email field should not be empty")
    private String email;

    @NotEmpty(message = "phone field should not be empty")
    @Pattern(regexp = "[0-9]*", message = "phone field should be valid")
    private String phone;

    @NotEmpty(message = "address field should not be empty")
    private String address;

    private String name;
    private String discount;
}
