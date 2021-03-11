package com.mediscreen.ui.proxies;

import com.mediscreen.ui.model.Note;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name="mediscreen-note", url="http://localhost:9102")
public interface NoteProxy {
    @PostMapping(value="/patHistory/add")
    Note addNote(@RequestParam Integer patientId, @RequestParam String note);

}
