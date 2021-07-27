package com.market.skin.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import com.market.skin.controller.payload.response.MessageResponse;
import com.market.skin.model.SuccessCode;
import com.market.skin.model.Transactions;
import com.market.skin.service.TransService;
import com.nimbusds.oauth2.sdk.Message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transactions")
public class TransController {
    @Autowired
    private final TransService service;

    public TransController(TransService service){
        this.service = service;
    }

    @GetMapping("/")
    ResponseEntity<MessageResponse> find(@RequestParam("attr") String attr, @RequestParam("value") String value){
        switch (attr) {
            case "id":
                return ResponseEntity.ok(MessageResponse.builder().data(service.findById(Integer.parseInt(value))).success(SuccessCode.SUCCESS_GET).build());
            case "itemid":
                return ResponseEntity.ok(MessageResponse.builder().data(service.findByItemId(Integer.parseInt(value))).success(SuccessCode.SUCCESS_GET).build());
            case "userid":
                return ResponseEntity.ok(MessageResponse.builder().data(service.findByUserId(Integer.parseInt(value))).success(SuccessCode.SUCCESS_GET).build());
            case "status":
                return ResponseEntity.ok(MessageResponse.builder().data(service.findByStatus(value)).success(SuccessCode.SUCCESS_GET).build());
        }
        return null;
    }

    @PostMapping("/create")
    ResponseEntity<MessageResponse> createGun(@RequestBody Transactions newTrans){
        newTrans.setUpdate_time(LocalDateTime.now());
        service.create(newTrans);
        return ResponseEntity.ok(MessageResponse.builder().success(SuccessCode.SUCCESS_CREATE).build());
    }

    @PostMapping("/modify")
    ResponseEntity<MessageResponse> test(@RequestBody Transactions newTrans){
        newTrans.setUpdate_time(LocalDateTime.now());
        service.modify(newTrans);
        return ResponseEntity.ok(MessageResponse.builder().success(SuccessCode.SUCCESS_MODIFY).build());
    }
    
    @DeleteMapping("/{id}")
    ResponseEntity<MessageResponse> deleteGun(@PathVariable int id){
        service.delete(id);
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