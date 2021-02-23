package com.mediscreen.ui.proxies;

import com.mediscreen.ui.model.Patient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name="mediscreen-patient", url="http://localhost:9101")
public interface PatientsProxy {

    @GetMapping(value = "/patients")
    List<Patient> getAllPatients();
}
