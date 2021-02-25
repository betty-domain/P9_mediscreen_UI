package com.mediscreen.ui.controller;

import com.mediscreen.ui.model.Patient;
import com.mediscreen.ui.proxies.PatientsProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
public class PatientController {

    @Autowired
    private PatientsProxy patientsProxyService;

    @RequestMapping("/patient/list")
    public String list(Model model) {
        List<Patient> patientList = patientsProxyService.getAllPatients();

        model.addAttribute("patientsList", patientList);
        return "patient/list";
    }

    @GetMapping("/patient/add")
    public String addPatientForm(Patient patient) {
        return "patient/add";
    }

    @RequestMapping("/patient/validate")
    public String validate(@Valid Patient patient, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "patient/add";
        }

        try {
            patientsProxyService.addPatient(patient.getLastname(), patient.getFirstname(), patient.getBirthDate(), patient.getSex(), patient.getAddress(), patient.getPhone());
            model.addAttribute("patientsList", patientsProxyService.getAllPatients());
            return "redirect:/patient/list";
        } catch (Exception exception) {
            model.addAttribute("errorAddingPatient", exception.getMessage());
            return "patient/add";
        }
    }
}
