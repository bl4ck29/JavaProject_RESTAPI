package com.market.skin.controller;

import java.util.Hashtable;

import javax.persistence.EntityNotFoundException;

import com.market.skin.controller.payload.response.MessageResponse;
import com.market.skin.exception.ConstraintViolationException;
import com.market.skin.exception.MissingServletRequestParameterException;
import com.market.skin.model.Guns;
import com.market.skin.model.GunsType;
import com.market.skin.model.Items;
import com.market.skin.model.Users;
import com.market.skin.service.GunsService;
import com.market.skin.service.GunsTypeService;
import com.market.skin.service.ItemsService;
import com.market.skin.service.PatternsService;
import com.market.skin.service.TransService;
import com.market.skin.service.UsersService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {
    @Autowired
    GunsService gunsService;
    @Autowired
    PatternsService patternsService;
    @Autowired
    ItemsService itemsService;
    @Autowired
    TransService transService;
    @Autowired
    UsersService usersService;
    @Autowired
    GunsTypeService typeService;

    // FIND
    @GetMapping("/all/{obj}")
    ResponseEntity<?> findByAttr(@PathVariable String obj, @RequestBody(required = false) Hashtable<String, String> attr){
        switch (obj) {
            case "guns":
                if(attr.contains("id")){
                    return ResponseEntity.ok(gunsService.findById(Integer.parseInt(attr.get("id"))));
                }
            case "items":
                if(attr.contains("id")){
                    return ResponseEntity.ok(itemsService.findById(Integer.parseInt(attr.get("id"))));
                }
            case "transactions":
                if(attr.contains("id")){
                    return ResponseEntity.ok(transService.findById(Integer.parseInt(attr.get("id"))));
                }
            case "users":
                return ResponseEntity.ok(transService.findByUserId(Integer.parseInt(attr.get("userid"))));
        }
        throw new EntityNotFoundException();
    }

    //ITEMS
    @DeleteMapping("/delete/items")
    ResponseEntity<?> deleteItem(@RequestBody Items item) throws MissingServletRequestParameterException{
        if (transService.findByItemId(item.getId()).isEmpty()){
            itemsService.deleteById(item.getId());
            return ResponseEntity.ok( new MessageResponse("Delete item successfully."));
        } else{
            throw new ConstraintViolationException("Cant not delete item cause it still have transaction.");
        }
    }

    //GUNS
    @PostMapping("/create/guns")
    ResponseEntity<?> createGun(@RequestBody Hashtable<String, String> newGun) throws MissingServletRequestParameterException{
        try {
            if(typeService.findByTypeName(newGun.get("typename")).isEmpty()){
                throw new ConstraintViolationException("Cant not found gunstype: " + newGun.get("typename"));
            }
            int typeid = typeService.findByTypeName(newGun.get("typename")).get().getId();
            gunsService.createGuns(new Guns(typeid, newGun.get("gunname")));
        } catch (DataIntegrityViolationException dataIntegrityViolationException) {
            throw new ConstraintViolationException("Gun is already existed.");
        }
        return null;
    }

    @DeleteMapping("/delete/guns")
    ResponseEntity<?> deleteGun(@RequestBody Guns gun) throws MissingServletRequestParameterException{
        if (transService.findByItemId(gun.getId()).isEmpty() && itemsService.findByGunId(gun.getId()).isEmpty()){
            gunsService.deleteGun(gun.getId());
            return ResponseEntity.ok( new MessageResponse("Delete gun successfully."));
        } else{
            throw new ConstraintViolationException("Cant not delete gun cause it still have items.");
        }
    }

    //GUNTYPE
    @PostMapping("/create/types")
    ResponseEntity<?> createType(@RequestBody GunsType newType) throws MissingServletRequestParameterException{
        if (typeService.findByTypeName(newType.getTypeName()).isEmpty()){
            typeService.createGuns(newType);
            return ResponseEntity.ok("Create gunstype successfully.");
        }
        throw new ConstraintViolationException("Gunstype already existed");
    }


    @DeleteMapping("/delete/types")
    ResponseEntity<?> deleteType(@RequestBody GunsType gun_type) throws MissingServletRequestParameterException{
        if (gunsService.findByTypeId(gun_type.getId()).isEmpty()){
            gunsService.deleteGun(gun_type.getId());
            return ResponseEntity.ok( new MessageResponse("Delete type successfully."));
        } else{
            throw new ConstraintViolationException("Cant not delete type cause it still have guns.");
        }
    }

    //USER
    @DeleteMapping("/delete/users")
    ResponseEntity<?> deleteUser(@RequestBody Users user) throws MissingServletRequestParameterException{
        if (transService.findByUserId(user.getId()).isEmpty()){
            usersService.deleteUser(user.getId());
            return ResponseEntity.ok( new MessageResponse("Delete user successfully."));
        } else{
            throw new ConstraintViolationException("Cant not delete user cause it still have transaction.");
        }
    }
}