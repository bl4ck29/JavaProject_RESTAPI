package com.market.skin.controller;

import com.market.skin.service.UsersService;
import com.market.skin.controller.payload.response.MessageResponse;
import com.market.skin.model.SuccessCode;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/users")
public class UsersController {
    private final UsersService service;

    public UsersController(UsersService service){
        this.service = service;
    }

    @GetMapping("/{id}")
    ResponseEntity<MessageResponse> findOne(@RequestParam(required=false, name = "id") int id){
        return ResponseEntity.ok(MessageResponse.builder().success(SuccessCode.SUCCESS_GET).data(service.findById(id)).build());
    }
    
    @DeleteMapping("/{id}")
    ResponseEntity<MessageResponse> deleteUser(@PathVariable int id) {
        service.deleteUser(id);
        return ResponseEntity.ok(MessageResponse.builder().success(SuccessCode.SUCCESS_MODIFY).build());
    }

    @GetMapping("/page")
    public ResponseEntity<MessageResponse> page(@RequestParam("page") int page, @RequestParam("size") int size){
        return ResponseEntity.ok(MessageResponse.builder().success(SuccessCode.SUCCESS_GET).build());
    }

    @GetMapping("/sort")
    ResponseEntity<MessageResponse> sort(@PathVariable int page, @RequestParam("attr") String attr, @RequestParam("asc") Boolean asc, @RequestParam("size") int size){
        return ResponseEntity.ok(MessageResponse.builder().success(SuccessCode.SUCCESS_GET).data(service.sortByAttr(attr, page, size, asc)).build());
    }
}
