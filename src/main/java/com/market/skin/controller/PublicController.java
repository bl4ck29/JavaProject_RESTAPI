package com.market.skin.controller;

import java.util.ArrayList;
import java.util.List;

import com.market.skin.exception.ConstraintViolationException;
import com.market.skin.exception.RecordNotFoundException;
import com.market.skin.model.ClientItems;
import com.market.skin.model.Items;
import com.market.skin.service.GunsService;
import com.market.skin.service.ItemsService;
import com.market.skin.service.PatternsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/showroom")
public class PublicController {
    @Autowired
    GunsService gunsService;

    @Autowired
    PatternsService patternsService;

    @Autowired
    ItemsService itemsService;

    public List<ClientItems> result(List<Items> dbItems){
        List<ClientItems> result = new ArrayList<ClientItems>();;
        for(Items item : dbItems){
            result.add(
                new ClientItems(
                    item.getId(),
                    gunsService.findById(((Items)item).getGunId()).get().getGunName(),
                    patternsService.findById(((Items)item).getPatternId()).get().getName()
                 ));
        }
        return result;
    }

    @GetMapping("/all/{obj}")
    ResponseEntity<?> findbyAttr(@PathVariable String obj, @RequestParam(required = false, name = "attr") String attr, 
        @RequestParam(required = false, name = "value") String value){
        switch (obj) {
            case "guns":
                if(attr.equals("id")){ 
                    return ResponseEntity.ok(gunsService.findById(Integer.parseInt(value)));}
                if(attr.equals("name")){ 
                    return ResponseEntity.ok(gunsService.findByGunName(value));}
                if(attr.equals("typeid")){ 
                    return ResponseEntity.ok(gunsService.findByTypeId(Integer.parseInt(value)));}
                else{
                    throw new ConstraintViolationException("No such field name: " + attr);
                }
            
            case "patterns":
                if(attr.equals("id")){ 
                    return ResponseEntity.ok(patternsService.findById(Integer.parseInt(value)));}
                if(attr.equals("name")){ 
                    return ResponseEntity.ok(patternsService.findByPatternName(value));}
                else{
                    throw new ConstraintViolationException("No such field name: " + attr);
                }
            
            case "items":
                if(attr.equals("id")){ 
                    return ResponseEntity.ok(itemsService.findById(Integer.parseInt(value)));}
                if(attr.equals("gunid")){
                    return ResponseEntity.ok(this.result(itemsService.findByGunId(Integer.parseInt(value))));}
                if(attr.equals("patternid")){
                    return ResponseEntity.ok(this.result(itemsService.findByPatternId(Integer.parseInt(value))));}
                else{
                    throw new ConstraintViolationException("No such field name: " + attr);
                }

            default:
                throw new RecordNotFoundException();   
        }
    }

    @GetMapping("/{attr}")
    ResponseEntity<?> pageRequest(@PathVariable String attr, @RequestParam(name = "page") int page){
        Page<?> result = null;
        switch (attr) {
            case "guns":
                if(page==0){ result = gunsService.homePage();}
                else{ result =  gunsService.showPage(page-1);}
                break;

            case "patterns":
                if(page==0){ result =  patternsService.homePage();}
                else{ result =  patternsService.showPage(page-1);}
                break;
        }

        if(result == null) { return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No such field named: " + attr);}
        else if (result.isEmpty()){ return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);}
        return ResponseEntity.ok(result);
    }
    
    @GetMapping("/sort/{obj}")
    ResponseEntity<?> sort(@RequestParam(name = "page") int page, @PathVariable String obj, @RequestParam(name = "sortby") String sortby, @RequestParam(name = "asc") String asc){
        Boolean sort = Boolean.parseBoolean(asc);
        Page<?> result = null;
        switch (obj) {
            case "guns":
                result =  gunsService.sortByAttr(sortby, page, sort);
                break;
            case "patterns":
                result =  patternsService.sortByAttr(sortby, page, sort);
                break;
        }
        if(result == null) { return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No such object named: " + obj);}
        else if (result.isEmpty()){ return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);}
        return ResponseEntity.ok(result);
    }
}
