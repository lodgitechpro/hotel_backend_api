package com.lodgitechpro.hotelmanagement.auth.controller;

import com.lodgitechpro.hotelmanagement.auth.dtos.MenuDto;
import com.lodgitechpro.hotelmanagement.auth.entity.Menu;
import com.lodgitechpro.hotelmanagement.exception.EntityNotFoundException;
import com.lodgitechpro.hotelmanagement.auth.repository.MenuRepository;
import com.lodgitechpro.hotelmanagement.auth.service.MenuFormService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/menus")
@RequiredArgsConstructor
public class MenuController {
    @Autowired
    private MenuFormService menuFormService;

    @Autowired
    private MenuRepository menuRepository;

    @PostMapping
    public ResponseEntity<?> saveMenu(
            @RequestBody MenuDto menuDto
    ) {
        Menu menu = menuFormService.saveAndUpdateMenu(menuDto);
        return ResponseEntity.ok(menu);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getMenu(
            @PathVariable int id
    ) {
        var menu =  menuRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Menu not found with id: " + id));
        return ResponseEntity.ok(menu);
    }

    @DeleteMapping("/{id}")
    public void deleteMenu(@PathVariable int id) {
         menuFormService.deleteMenu(id);
    }

    @GetMapping
    public ResponseEntity<?> getMenus(@RequestBody MenuDto menuDto) {
        var menus =  menuFormService.searchMenu(menuDto);
        return ResponseEntity.ok(menus);
    }

    @GetMapping("/{id}/forms")
    public ResponseEntity<?> getMenuForms(@PathVariable int id) {
        var forms =  menuFormService.getMenuForms(id);
        return ResponseEntity.ok(forms);
    }

}
