package com.mediscreen.ui.controller;

import com.mediscreen.ui.model.Patient;
import com.mediscreen.ui.proxies.PatientsProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class PatientController {

    @Autowired
    private PatientsProxy patientsProxyService;

    @RequestMapping("/patient/list")
    public String list(Model model) {
        List<Patient> patientList = patientsProxyService.getAllPatients();

        model.addAttribute("patientsList",patientList);
        return "patient/list";
    }
}
