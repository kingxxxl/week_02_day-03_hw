package com.example.firstspringboot;

import lombok.*;
import javax.validation.constraints.*;

@AllArgsConstructor
@Data
public class Employee {
    @NotBlank
    @Size(min = 3, message = "ID need to have length more than 2")
    private String  ID;
    @NotBlank
    @Size(min = 5, message = "name need to have length more than 4")
    private String  name;
    @NotNull
    @Positive
    @Min(value = 26, message = "age must be more than 25")
    private Integer age;
    private boolean onLeave = false;
    @NotNull
    @Pattern(regexp = "^(19|[2-9]\\d)\\d{2}$", message = "Not a valid year!")
    private String employmentYear;
    @NotNull
    private Integer annualLeave;
}
