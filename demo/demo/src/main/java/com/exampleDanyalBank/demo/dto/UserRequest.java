package com.exampleDanyalBank.demo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;



@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data

public class UserRequest {
    @Schema(
            name = "First Name of user"
    )
    private String firstName;
    @Schema(
            name = "Last name of user"
    )
    private String lastName;
    @Schema(
            name ="Other Name of user"
    )
    private String otherName;
    @Schema(
            name ="Gender if user"
    )
    private String gender;
    @Schema(
            name ="Address of user"
    )
    private String address;
    @Schema(
            name ="User state of origin "
    )
    private String stateOfOrigin;

    @Schema(
            name ="User email"
    )
    private String email;
    @Schema(
            name = "user's password"
    )
    private String Password;
    @Schema(
            name ="User phone number"
    )
    private String phoneNumber;
    @Schema(
            name ="alternative phone number"
    )
    private String alternativePhoneNumber;

    @Schema(
            name ="User's Date of birth"
    )
    private LocalDate dateOfBirth;

    @Schema(
            name = "user account number"
    )

    private String accountNumber;

}
