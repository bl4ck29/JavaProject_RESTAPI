package com.market.skin.controller;

import com.market.skin.controller.payload.response.MessageResponse;
import com.market.skin.model.Roles;
import com.market.skin.model.RolesName;
import com.market.skin.model.SuccessCode;
import com.market.skin.service.RolesService;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/roles")
@PreAuthorize("hasRole('ADMIN')")
public class RolesController {
    @Autowired
    private final RolesService service;

    public RolesController(RolesService service){
        this.service = service;
    }

    @GetMapping("/")
    ResponseEntity<MessageResponse> find(@RequestParam("attr") String attr, @RequestParam("value") String value){
        switch (attr) {
            case "id":
                return ResponseEntity.ok(MessageResponse.builder().success(SuccessCode.SUCCESS_GET).data(service.findById(Integer.parseInt(value))).build());
            case "name":
                return ResponseEntity.ok(MessageResponse.builder().success(SuccessCode.SUCCESS_GET).data(service.findByRoleName(RolesName.valueOf(value.toUpperCase()))).build());
        }
        return null;
    }

    @PostMapping("/create")
    ResponseEntity<MessageResponse> create(@RequestBody Roles role){
        service.createRole(role);
        return ResponseEntity.ok(MessageResponse.builder().success(SuccessCode.SUCCESS_CREATE).build());
    }

    @DeleteMapping("/delete/{id}")
    ResponseEntity<MessageResponse> delete(@PathVariable int id){
        service.deleteRole(id);
        return ResponseEntity.ok(MessageResponse.builder().success(SuccessCode.SUCCESS_MODIFY).build());
    }
}