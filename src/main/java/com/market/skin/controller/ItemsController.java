package com.market.skin.controller;

import com.market.skin.service.ItemsService;
import com.market.skin.controller.payload.response.MessageResponse;
import com.market.skin.model.Items;
import com.market.skin.model.SuccessCode;

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
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.MissingServletRequestParameterException;
// import io.swagger.annotations.Api;

@RestController
@RequestMapping("/items")
// @Api(value = "Item APIs")
public class ItemsController {
    @Autowired
    ItemsService service;

    public ItemsController(ItemsService service){
        this.service = service;
    }

    @GetMapping("")
    public ResponseEntity<MessageResponse> find(@RequestParam("attr") String attr, @RequestParam("value") String value){
        switch (attr) {
            case "id":
                return ResponseEntity.ok(MessageResponse.builder().success(SuccessCode.SUCCESS_GET).data(service.findById(Integer.parseInt(value))).build());
            case "patternid":
                return ResponseEntity.ok(MessageResponse.builder().success(SuccessCode.SUCCESS_GET).data(service.findPatternId(Integer.parseInt(value))).build());
            case "gunid":
                return ResponseEntity.ok(MessageResponse.builder().success(SuccessCode.SUCCESS_GET).data(service.findByGunId(Integer.parseInt(value))).build());    
        }
        return null;
    }

    @PostMapping("/create")
    @PreAuthorize("hasRole('CREATOR')")
    public ResponseEntity<MessageResponse> createItems(@RequestBody Items newItem)throws MissingServletRequestParameterException{
        service.create(newItem);
        return ResponseEntity.ok(MessageResponse.builder().success(SuccessCode.SUCCESS_CREATE).build());
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hadRole('ADMIN')")
    public ResponseEntity<MessageResponse> deleteItem(@PathVariable int id){
        return ResponseEntity.ok(MessageResponse.builder().success(SuccessCode.SUCCESS_CREATE).build());
    }

    @GetMapping("/page")
    public ResponseEntity<MessageResponse> page(@RequestParam("page") int page, @RequestParam int size){
        return ResponseEntity.ok(MessageResponse.builder().success(SuccessCode.SUCCESS_GET).data(service.showPage(page, size)).build());
    }

    @GetMapping("/sort/{page}")
    public ResponseEntity<MessageResponse> sort(@PathVariable int page, @RequestParam("attr") String attr, @RequestParam("asc") Boolean asc, @RequestParam("size") int size){
        return ResponseEntity.ok(MessageResponse.builder().success(SuccessCode.SUCCESS_GET).data(service.sortByAttr(attr, page, size, asc)).build());
    }
}
