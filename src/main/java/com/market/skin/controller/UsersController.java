package com.market.skin.controller;

import com.market.skin.service.UsersService;
import com.market.skin.controller.payload.response.MessageResponse;
import com.market.skin.model.SuccessCode;

import org.springframework.web.bind.annotation.RestController;

// import io.swagger.annotations.Api;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetailsService;

@RestController
@RequestMapping("/users")
// @Api(value = "Users APIs")
public class UsersController {
    @Autowired
    UserDetailsService service;
    // UsersService service;

    public UsersController(UserDetailsService service){
        this.service = service;
    }

    @GetMapping("/{id}")
    ResponseEntity<MessageResponse> findOne(@PathVariable int id){
        return ResponseEntity.ok(MessageResponse.builder().success(SuccessCode.SUCCESS_GET).data(((UsersService) service).findById(id)).build());
    }
    
    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    ResponseEntity<MessageResponse> deleteUser(@PathVariable int id) {
        ((UsersService) service).deleteUser(id);
        return ResponseEntity.ok(MessageResponse.builder().success(SuccessCode.SUCCESS_MODIFY).build());
    }

    @GetMapping("/page")
    public ResponseEntity<MessageResponse> page(@RequestParam("page") int page, @RequestParam("size") int size){
        return ResponseEntity.ok(MessageResponse.builder().success(SuccessCode.SUCCESS_GET).data(((UsersService) service).showPage(page, size)).build());
    }

    @GetMapping("/sort/{page}")
    ResponseEntity<MessageResponse> sort(@PathVariable int page, @RequestParam("attr") String attr, @RequestParam("asc") Boolean asc, @RequestParam("size") int size){
        return ResponseEntity.ok(MessageResponse.builder().success(SuccessCode.SUCCESS_GET).data(((UsersService) service).sortByAttr(attr, page, size, asc)).build());
    }
}
