package com.mediscreen.ui.controller;

import com.mediscreen.ui.exceptions.ObjectNotFoundException;
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
import java.util.List;

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
        } catch (ObjectNotFoundException objectNotFoundException) {
            model.addAttribute("errorPatientNotFoundMessage", objectNotFoundException.getMessage());
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
            noteProxyService.addNote(note.getPatientId(), note.getNote());
            model.addAttribute("patient", patientsProxyService.getPatient(note.getPatientId()));

            return "redirect:/note/" + patientId + "/list";
        } catch (Exception exception) {
            model.addAttribute("errorAddingNote", exception.getMessage());
            return "note/add";
        }

    }

    @GetMapping("/note/{patientId}/list")
    public String listNoteForm(@PathVariable("patientId") Integer patientId, Model model) {
        try {
            Patient patient = patientsProxyService.getPatient(patientId);
            model.addAttribute("patient", patient);

            List<Note> noteList = noteProxyService.getNotesForPatient(patientId);
            model.addAttribute("notesList", noteList);
            return "note/list";
        } catch (ObjectNotFoundException objectNotFoundException) {
            model.addAttribute("errorPatientNotFoundMessage", objectNotFoundException.getMessage());

            return patientController.list(model);
        }
    }

    @GetMapping("/note/{patientId}/update/{id}")
    public String updateNoteForm(@PathVariable("patientId") Integer patientId, @PathVariable("id") String id, Model model) {
        try {
            Note note = noteProxyService.getNote(id);
            model.addAttribute("note", note);
        } catch (ObjectNotFoundException noteNotFoundException) {
            model.addAttribute("errorNoteNotFoundMessage", noteNotFoundException.getMessage());
            return this.listNoteForm(patientId, model);
        }

        try {
            Patient patient = patientsProxyService.getPatient(patientId);
            model.addAttribute("patient", patient);
            return "note/update";
        } catch (ObjectNotFoundException patientNotFoundException) {
            model.addAttribute("errorPatientNotFoundMessage", patientNotFoundException.getMessage());
            return patientController.list(model);
        }

    }

    @PostMapping("/note/{patientId}/update/{id}")
    public String updateNote(@PathVariable("patientId") Integer patientId, @PathVariable("id") String id, @Valid Note note, BindingResult result, Model model) {
        Patient patient = patientsProxyService.getPatient(patientId);
        note.setPatientId(patientId);

        if (result.hasErrors()) {
            model.addAttribute("patient", patient);
            return "note/update";
        }
        try {
            noteProxyService.updateNote(id, patientId, note.getNote());
            model.addAttribute("notesList", noteProxyService.getNotesForPatient(patientId));
            return "redirect:/note/" + patientId + "/list";
        } catch (Exception exception) {
            model.addAttribute("errorUpdatingNote", exception.getMessage());
            return "note/update";
        }
    }

}
