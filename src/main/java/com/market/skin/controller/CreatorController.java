package com.market.skin.controller;

import java.util.Hashtable;

import com.market.skin.controller.payload.response.MessageResponse;
import com.market.skin.exception.ConstraintViolationException;
import com.market.skin.exception.MissingServletRequestParameterException;
import com.market.skin.model.Items;
import com.market.skin.model.Patterns;
import com.market.skin.service.GunsService;
import com.market.skin.service.ItemsService;
import com.market.skin.service.PatternsService;
import com.market.skin.service.TransService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/creator")
@PreAuthorize("hasRole('CREATOR') or hasRole('ADMIN')")
public class CreatorController {
    @Autowired
    PatternsService patternsService;
    @Autowired
    ItemsService itemsService;
    @Autowired
    GunsService gunsService;
    @Autowired
    TransService transService;

    // PATTERNS
    @PostMapping("create/patterns")
    ResponseEntity<?> createPattern(@RequestBody Patterns newPatt){
        try{
        patternsService.create(newPatt);
        return ResponseEntity.ok(new MessageResponse("Created pattern successfully"));
        } catch(DataIntegrityViolationException dataIntegrityViolationException){
            throw new ConstraintViolationException("Pattern is already existed.");
        }    
    }


    //ITEMS
    @PostMapping("create/items")
    ResponseEntity<?> createItem(@RequestBody Hashtable<String, String> newItem) throws MissingServletRequestParameterException{
        try{
            if(newItem.containsKey("pattern") & newItem.containsKey("gun") & newItem.containsKey("image")){
                int gunid = gunsService.findByGunName(newItem.get("gun")).get(0).getId();
                int pattid = patternsService.findByPatternName(newItem.get("pattern")).get(0).getId();

                itemsService.create(
                new Items(gunid, pattid, Integer.parseInt(newItem.get("creator")), newItem.get("image")));
                return ResponseEntity.ok("Create items successfully.");
            } else{
                throw new ConstraintViolationException("Items is already existed.");}  
        
        } catch(IndexOutOfBoundsException ex){
            throw new ConstraintViolationException("Gun or pattern is not existed.");
        }
    }

    @DeleteMapping("/delete/items")
    ResponseEntity<?> deleteItem(@RequestBody Hashtable<String, String> item){
        Items dbItems = itemsService.findByGunIdAndPatternId(
            patternsService.findByPatternName(item.get("pattern")).get(0).getId(),
            gunsService.findByGunName(item.get("gun")).get(0).getId());

        if(transService.findByItemId(dbItems.getId()).isEmpty()){
            int cre_id = dbItems.getCreatorId();
            if (Integer.parseInt(item.get("user")) == cre_id){
                itemsService.deleteById(dbItems.getId());
                return ResponseEntity.ok(dbItems);
            }
        }
        else{throw new ConstraintViolationException("Item still have transactions.");}
        return null;
    }
}
