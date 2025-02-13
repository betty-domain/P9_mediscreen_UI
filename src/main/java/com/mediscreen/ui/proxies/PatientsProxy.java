package com.mediscreen.ui.proxies;

import com.mediscreen.ui.model.Patient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;

@FeignClient(name="mediscreen-patient", url="http://localhost:9101")
public interface PatientsProxy {

    @GetMapping(value = "/patients")
    List<Patient> getAllPatients();

    @PostMapping(value="/patient/add")
    Patient addPatient(@RequestParam String family, @RequestParam  String given, @RequestParam @DateTimeFormat(pattern ="yyyy-MM-dd") LocalDate dob, @RequestParam String sex, @RequestParam String address, @RequestParam String phone);
}
