package com.lodgitechpro.hotelmanagement.auth.controller;

import com.lodgitechpro.hotelmanagement.auth.dtos.FormDto;
import com.lodgitechpro.hotelmanagement.auth.entity.Form;
import com.lodgitechpro.hotelmanagement.exception.EntityNotFoundException;
import com.lodgitechpro.hotelmanagement.auth.repository.FormRepository;
import com.lodgitechpro.hotelmanagement.auth.service.MenuFormService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/forms")
@RequiredArgsConstructor
public class FormController {
    @Autowired
    private MenuFormService menuFormService;

    @Autowired
    private FormRepository formRepository;

    @PostMapping
    public ResponseEntity<?> saveForm(@RequestBody FormDto formDto) {
        Form form = menuFormService.saveAndUpdateForm(formDto);
        return ResponseEntity.ok(form);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getForm(@PathVariable int id) {
        var form =  formRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Form not found with id: " + id));
        return ResponseEntity.ok(form);
    }

    @DeleteMapping("/{id}")
    public void deleteForm(@PathVariable int id) {
         menuFormService.deleteForm(id);
    }

    @GetMapping
    public ResponseEntity<?> getForms(@RequestBody FormDto formDto) {
        var forms =  menuFormService.searchForm(formDto);
        return ResponseEntity.ok(forms);
    }

    @GetMapping("/{id}/forms")
    public ResponseEntity<?> getMenuForms(@PathVariable int id) {
        var forms =  menuFormService.getMenuForms(id);
        return ResponseEntity.ok(forms);
    }

}
