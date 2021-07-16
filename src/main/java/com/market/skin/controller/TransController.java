package com.market.skin.controller;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import com.market.skin.model.Transactions;
import com.market.skin.service.TransService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TransController {
    @Autowired
    private final TransService service;

    public TransController(TransService service){
        this.service = service;
    }

    @GetMapping("/transactions")
    ResponseEntity<Optional<Transactions>> findOne(@RequestParam("id") int id){
        return ResponseEntity.status(HttpStatus.ACCEPTED)
            .body(service.findById(id));
    }

    @GetMapping("/transactions/find")
    ResponseEntity<List<Transactions>> findByAttr(@RequestParam(required=false, name = "attr") String attr, @RequestParam(required=false, name = "value") String value){
        if ("itemid".equals(attr)){
            return ResponseEntity.status(HttpStatus.OK).body(service.findByItemId(Integer.parseInt(value)));
        } else if ("userid".equals(attr)){
            return ResponseEntity.status(HttpStatus.OK).body(service.findByUserId(Integer.parseInt(value)));
        } else if ("status".equals(attr)){
            return ResponseEntity.status(HttpStatus.OK).body(service.findByStatus(value));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

    @PutMapping("/transactions")
    ResponseEntity<Transactions> createGun(@RequestBody Transactions newTrans){
        newTrans.setUpdateTime(Timestamp.valueOf(LocalDateTime.now()));
        service.create(newTrans);
        return ResponseEntity.status(HttpStatus.OK).body(newTrans);
    }
    
    @DeleteMapping("/transactions/{id}")
    ResponseEntity<Optional<Transactions>> deleteGun(@PathVariable int id){
        Optional<Transactions> res = service.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body(res);
    }

    @GetMapping("/transactions/all")
    Page<Transactions> pageRequest(@PathVariable int page){
        if (page == 0){
            return service.homePage();
        }
        return service.showPage(page-1);
    }

    @GetMapping("/transactions/all/sort")
    Page<Transactions> sort(@PathVariable int page, @RequestParam("attr") String attr, @RequestParam("asc") Boolean asc){
        return service.sortByAttr(attr, page, asc);
    }
}