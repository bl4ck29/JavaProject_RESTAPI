package com.market.skin.controller;

import com.market.skin.controller.payload.response.MessageResponse;
import com.market.skin.model.Patterns;
import com.market.skin.service.PatternsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/creator")
@PreAuthorize("hasRole('CREATOR') or hasRole('ADMIN')")
public class CreatorController {
    @Autowired
    PatternsService patternsService;

    @PostMapping("/patterns")
    ResponseEntity<?> createPattern(@RequestBody Patterns newPatt){
        patternsService.create(newPatt);
        return ResponseEntity.ok(new MessageResponse("Created pattern successfully"));
    }

    @PutMapping("/patterns")
    ResponseEntity<?> modifyPattern(@RequestBody Patterns patt){
        patternsService.modify(patt);
        return ResponseEntity.ok("Pattern details have been changed");
    }
}
