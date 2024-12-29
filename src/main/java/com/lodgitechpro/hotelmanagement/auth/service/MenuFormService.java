package com.lodgitechpro.hotelmanagement.auth.service;

import com.lodgitechpro.hotelmanagement.auth.dtos.FormDto;
import com.lodgitechpro.hotelmanagement.auth.dtos.MenuDto;
import com.lodgitechpro.hotelmanagement.auth.entity.Form;
import com.lodgitechpro.hotelmanagement.auth.entity.Menu;
import com.lodgitechpro.hotelmanagement.auth.entity.Role;
import com.lodgitechpro.hotelmanagement.auth.entity.RoleForm;
import com.lodgitechpro.hotelmanagement.exception.EntityNotFoundException;
import com.lodgitechpro.hotelmanagement.mapper.EntityMapper;
import com.lodgitechpro.hotelmanagement.auth.repository.FormRepository;
import com.lodgitechpro.hotelmanagement.auth.repository.MenuRepository;
import com.lodgitechpro.hotelmanagement.auth.repository.RoleFormRepository;
import com.lodgitechpro.hotelmanagement.auth.repository.RoleRepository;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class MenuFormService {

    private final FormRepository formRepository;
    private final MenuRepository menuRepository;
    private final RoleFormRepository roleFormRepository;
    private final EntityMapper entityMapper;
    private final RoleRepository roleRepository;

    public Menu saveAndUpdateMenu(MenuDto menuDto) {
        // if id is given in menuDto then it will update else it will try to insert.
        Menu menu = entityMapper.mapDtoToEntity(menuDto, Menu.class);
        menu = menuRepository.save(menu);
        return menu;
    }

    public Form saveAndUpdateForm(FormDto formDto) {
        // if id is given in formDto then it will update else it will try to insert.
        Form form = entityMapper.mapDtoToEntity(formDto, Form.class);
        form = formRepository.save(form);
        return form;
    }

    public void deleteForm(int formId) {
        formRepository.findById(formId)
                        .orElseThrow(() -> new EntityNotFoundException("Form not found with id "+ formId + " for delete!"));
        formRepository.deleteById(formId);
    }

    public void deleteMenu(int menuId) {
        menuRepository.findById(menuId)
                .orElseThrow(() -> new EntityNotFoundException("Menu not found with id "+ menuId + " for delete!"));
        menuRepository.deleteById(menuId);
    }

    public List<Menu> searchMenu(MenuDto menuDto) {
        Specification<Menu> specification = (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (menuDto.getId() != null) {
                predicates.add(criteriaBuilder.equal(root.get("id"), menuDto.getId()));
            }

            if (StringUtils.isNotBlank(menuDto.getName())) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("name")),
                        "%" + menuDto.getName().toLowerCase() + "%"));
            }

            if (menuDto.getPosition() != null) {
                predicates.add(criteriaBuilder.equal(root.get("position"), menuDto.getPosition()));
            }

            if (StringUtils.isNotBlank(menuDto.getMenuName())) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("menuName")),
                        "%" + menuDto.getMenuName().toLowerCase() + "%"));
            }

            if (menuDto.getMnemonic() != null) {
                predicates.add(criteriaBuilder.equal(root.get("mnemonic"), menuDto.getMnemonic()));
            }

            if (menuDto.getActive() != null) {
                predicates.add(criteriaBuilder.equal(root.get("active"), menuDto.getActive()));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };

        return menuRepository.findAll(specification, Pageable.unpaged()).getContent();
    }

    public List<Form> searchForm(FormDto formDto) {
        Specification<Form> specification = (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (formDto.getId() != null) {
                predicates.add(criteriaBuilder.equal(root.get("id"), formDto.getId()));
            }

            if (StringUtils.isNotBlank(formDto.getName())) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("name")),
                        "%" + formDto.getName().toLowerCase() + "%"));
            }

            if (StringUtils.isNotBlank(formDto.getFormName())) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("formName")),
                        "%" + formDto.getFormName().toLowerCase() + "%"));
            }

            if (formDto.getPosition() != null) {
                predicates.add(criteriaBuilder.equal(root.get("position"), formDto.getPosition()));
            }

            if (formDto.getMenuId() != null) {
                predicates.add(criteriaBuilder.equal(root.get("menuId"), formDto.getMenuId()));
            }

            if (StringUtils.isNotBlank(formDto.getDescription())) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("description")),
                        "%" + formDto.getDescription().toLowerCase() + "%"));
            }

            if (StringUtils.isNotBlank(formDto.getWebUrl())) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("webUrl")),
                        "%" + formDto.getWebUrl().toLowerCase() + "%"));
            }

            if (formDto.getActive() != null) {
                predicates.add(criteriaBuilder.equal(root.get("active"), formDto.getActive()));
            }

            if (formDto.getWebEnabled() != null) {
                predicates.add(criteriaBuilder.equal(root.get("webEnabled"), formDto.getWebEnabled()));
            }

            if (formDto.getDesktopEnabled() != null) {
                predicates.add(criteriaBuilder.equal(root.get("desktopEnabled"), formDto.getDesktopEnabled()));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };

        return formRepository.findAll(specification, Pageable.unpaged()).getContent();
    }

    public void saveRoleForm(int roleId, List<Integer> formIds) {
        List<Form> forms = formRepository.findAllById(formIds);
        if(forms.isEmpty()) {
            log.error("Form not found with given formIds. No role forms will be saved! " + formIds);
            return;
        }

        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new EntityNotFoundException("Role not found with id: "+ roleId));
        List<RoleForm> roleForms = new ArrayList<>();
        for(Form form : forms) {
            RoleForm roleForm = new RoleForm();
            roleForm.setForm(form);
            roleForm.setRole(role);
            roleForms.add(roleForm);
        }
        roleFormRepository.saveAll(roleForms);
        log.info("New {} forms are added with roleId {}", forms.size(), role.getId());
    }

    public void copyRoleForms(int fromRoleId, int toRoleId) {

        Role fromRole = roleRepository.findById(fromRoleId)
                .orElseThrow(() -> new EntityNotFoundException("Role not found with id: "+ fromRoleId));

        Role toRole = roleRepository.findById(toRoleId)
                .orElseThrow(() -> new EntityNotFoundException("Role not found with id: "+ toRoleId));


        List<Form> fromForms = roleFormRepository.findByRole(fromRole).stream()
                .map(RoleForm::getForm)
                .toList();

        List<RoleForm> roleForms = new ArrayList<>();
        for(Form form : fromForms) {
            RoleForm roleForm = new RoleForm();
            roleForm.setForm(form);
            roleForm.setRole(toRole);
            roleForms.add(roleForm);
        }
        roleFormRepository.saveAll(roleForms);
        log.info("Forms Copy Successful! {} forms are copied fromRoleId {} toRoleId {}", fromForms.size(), fromRoleId, toRoleId);
    }

    public List<FormDto> getRoleForms(int roleId) {
        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new EntityNotFoundException("Role not found with id "+ roleId));

        List<RoleForm> roleForms = roleFormRepository.findByRole(role);
        return roleForms.stream()
                .map(RoleForm::getForm)
                .map(form -> entityMapper.mapDtoToEntity(form, FormDto.class))
                .collect(Collectors.toList());
    }

    public List<FormDto> getMenuForms(int menuId) {
        Menu menu = menuRepository.findById(menuId)
                .orElseThrow(() -> new EntityNotFoundException("Menu not found with id "+ menuId));

        List<Form> forms = formRepository.findByMenu(menu);
        return forms.stream()
                .map(form -> entityMapper.mapDtoToEntity(form, FormDto.class))
                .collect(Collectors.toList());
    }
}
