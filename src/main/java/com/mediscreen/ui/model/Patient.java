package com.mediscreen.ui.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Data
public class Patient {

    private Integer id;

    @NotBlank(message = "Firstname is mandatory")
    private String firstname;

    @NotBlank(message = "Lastname is mandatory")
    private String lastname;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "Birth Date is mandatory")
    private LocalDate birthDate;

    @NotBlank(message = "Address is mandatory")
    private String address;

    @NotBlank(message = "Phone is mandatory")
    private String phone;

    @NotBlank
    @Size(min = 1, max = 1, message = "Please type F or M for patient's sex")
    private String sex;
}
