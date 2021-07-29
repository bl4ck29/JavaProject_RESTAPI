package com.market.skin.controller;

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

import com.market.skin.controller.payload.response.MessageResponse;
import com.market.skin.model.Patterns;
import com.market.skin.model.SuccessCode;
import com.market.skin.service.PatternsService;
// import io.swagger.annotations.Api;

@RestController
@RequestMapping("/patterns")
// @Api(value = "Patterns APIs")
public class PatternsController {
    @Autowired
    private PatternsService service;

    public PatternsController(PatternsService service){
        this.service = service;
    }

    @GetMapping("")
    ResponseEntity<MessageResponse> find(@RequestParam("attr") String attr, @RequestParam("value") String value){
        switch (attr) {
            case "id":
                return ResponseEntity.ok(MessageResponse.builder().success(SuccessCode.SUCCESS_GET).data(service.findById(Integer.parseInt(value))).build());
            case "name":
                return ResponseEntity.ok(MessageResponse.builder().success(SuccessCode.SUCCESS_GET).data(service.findByPatternName(value)).build());
        }
        return null;
    }

    @PostMapping("/create")
    @PreAuthorize("hadRole('CREATOR')")
    ResponseEntity<MessageResponse> createPattern(@RequestBody Patterns newPatt){
        Patterns patt = newPatt;
        service.create(patt);
        return ResponseEntity.ok(MessageResponse.builder().success(SuccessCode.SUCCESS_CREATE).build());
    }
    
    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hadRole('CREATOR')")
    ResponseEntity<MessageResponse> deleteGun(@PathVariable int id){
        service.delete(id);
        return ResponseEntity.ok(MessageResponse.builder().success(SuccessCode.SUCCESS_MODIFY).build());
    }

    @GetMapping("/page")
    public ResponseEntity<MessageResponse> page(@RequestParam("page") int page, @RequestParam("size") int size){
        return ResponseEntity.ok(MessageResponse.builder().success(SuccessCode.SUCCESS_GET).data(service.showPage(page, size)).build());
    }

    @GetMapping("/sort")
    ResponseEntity<MessageResponse> sort(@PathVariable int page, @RequestParam("attr") String attr, @RequestParam("asc") Boolean asc, @RequestParam("size") int size){
        return ResponseEntity.ok(MessageResponse.builder().success(SuccessCode.SUCCESS_GET).data(service.sortByAttr(attr, page, size, asc)).build());
    }
}
