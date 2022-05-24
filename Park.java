package com.example.firstspringboot;

import lombok.*;
import javax.validation.constraints.*;

@AllArgsConstructor
@Data
public class Park {
    @NotBlank
    @Size(min = 3, message = "Raid ID need to have length more than 2")
    private String  rideID;
    @NotBlank
    @Size(min = 5, message = "name need to have length more than 4")
    private String  rideName;
    @NotBlank
    @Pattern(regexp = "rollercoaster|thriller|water", message = "Not a valid ride type!")
    private String  rideType;
    @NotNull
    @Positive
    private Integer tickets;
    @NotNull
    @Positive
    private Integer price;
}
