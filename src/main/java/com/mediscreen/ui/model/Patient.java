package com.mediscreen.ui.model;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Data
public class Patient {

    private Integer id;

    @Size(max=30)
    @NotBlank(message = "Firstname is mandatory")
    private String firstname;

    @Size(max=60)
    @NotBlank(message = "Lastname is mandatory")
    private String lastname;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "Birth Date is mandatory")
    private LocalDate birthDate;

    @Size(max=200)
    @NotBlank(message = "Address is mandatory")
    private String address;

    @Size(max=20)
    @NotBlank(message = "Phone is mandatory")
    private String phone;

    @NotBlank(message = "Sex is mandatory")
    @Size(min = 1, max = 1)
    private String sex;
}
