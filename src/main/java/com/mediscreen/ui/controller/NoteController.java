package com.mediscreen.ui.controller;

import com.mediscreen.ui.exceptions.PatientNotFoundException;
import com.mediscreen.ui.model.Note;
import com.mediscreen.ui.model.Patient;
import com.mediscreen.ui.proxies.NoteProxy;
import com.mediscreen.ui.proxies.PatientsProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class NoteController {

    @Autowired
    private PatientController patientController;

    @Autowired
    private NoteProxy noteProxyService;

    @Autowired
    private PatientsProxy patientsProxyService;

    @GetMapping("/note/{patientId}/add")
    public String addNoteForm(@PathVariable("patientId") Integer patientId, Note note, Model model) {

        try {
            Patient patient = patientsProxyService.getPatient(patientId);
            model.addAttribute("patient", patient);
            return "note/add";
        } catch (PatientNotFoundException patientNotFoundException) {
            model.addAttribute("errorPatientNotFoundMessage", patientNotFoundException.getMessage());
            //TODO : voir avec Alexandre si on peut appeler un autre controller pour déléguer la gestion de l'affichage de la liste au controller qui en est responsable
            return patientController.list(model);
        }
    }

    @PostMapping("/note/{patientId}/validate")
    public String validate(@PathVariable("patientId") Integer patientId, @Valid Note note, BindingResult result, Model model) {

        Patient patient = patientsProxyService.getPatient(patientId);
        note.setPatientId(patientId);
        if (result.hasErrors()) {
            model.addAttribute("patient", patient);
            return "note/add";
        }
        try {
            noteProxyService.addNote(note);
            model.addAttribute("patient", patientsProxyService.getPatient(note.getPatientId()));

            return "redirect:/patient/list";
        } catch (Exception exception) {
            model.addAttribute("errorAddingNote", exception.getMessage());
            return "note/add";
        }

    }
}
