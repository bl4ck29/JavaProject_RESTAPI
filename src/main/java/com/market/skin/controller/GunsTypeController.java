package com.market.skin.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.market.skin.service.GunsTypeService;
import com.market.skin.controller.payload.response.MessageResponse;
import com.market.skin.model.GunsType;
import com.market.skin.model.SuccessCode;
// import io.swagger.annotations.Api;
@RestController
@RequestMapping("/types")
// @Api(value = "Type APIs")
public class GunsTypeController {
    private final GunsTypeService service;

    public GunsTypeController(GunsTypeService service){
        this.service = service;
    }

    @GetMapping("")
    ResponseEntity<MessageResponse> find(@RequestParam("attr") String attr, @RequestParam(name = "value", required = false) String value){
        switch (attr) {
            case "id":
                return ResponseEntity.ok(MessageResponse.builder().success(SuccessCode.SUCCESS_GET).data(service.findById(Integer.parseInt(value))).build());
            case "name":
                return ResponseEntity.ok(MessageResponse.builder().success(SuccessCode.SUCCESS_GET).data(service.findByTypeName(value)).build());
        }
        return null;
    }
    
    @GetMapping("/all")
    ResponseEntity<MessageResponse> findAll(){
        return ResponseEntity.ok(MessageResponse.builder().success(SuccessCode.SUCCESS_GET).data(service.findAll()).build());
    }

    @PostMapping("/create")
    @PreAuthorize("hadRole('ADMIN')")
    ResponseEntity<MessageResponse> createGunType(@RequestBody GunsType newType){
        service.createGuns(newType);
        return ResponseEntity.ok(MessageResponse.builder().success(SuccessCode.SUCCESS_CREATE).build());
    }
    
    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hadRole('ADMIN')")
    ResponseEntity<MessageResponse> deleteType(@PathVariable int id){
        service.deleteGun(id);
        return ResponseEntity.ok(MessageResponse.builder().success(SuccessCode.SUCCESS_MODIFY).build());
    }

    @GetMapping("/page")
    public ResponseEntity<MessageResponse> page(@RequestParam("page") int page, @RequestParam("size") int size){
        return ResponseEntity.ok(MessageResponse.builder().success(SuccessCode.SUCCESS_GET).data(service.showPage(page, size)).build());
    }

    @GetMapping("/sort/{page}")
    ResponseEntity<MessageResponse> sort(@PathVariable int page, @RequestParam("attr") String attr, @RequestParam("asc") Boolean asc, @RequestParam("size") int size){
        return ResponseEntity.ok(MessageResponse.builder().success(SuccessCode.SUCCESS_GET).data(service.sortByAttr(attr, page, size, asc)).build());
    }
}
