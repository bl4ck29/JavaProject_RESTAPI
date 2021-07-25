package com.market.skin.controller;

import java.lang.IndexOutOfBoundsException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.stream.Collectors;


import com.market.skin.exception.ConstraintViolationException;
import com.market.skin.exception.MissingServletRequestParameterException;
import com.market.skin.exception.RecordNotFoundException;
import com.market.skin.exception.TransactionSystemException;
import com.market.skin.model.ClientItems;
import com.market.skin.model.Items;
import com.market.skin.model.Transactions;
import com.market.skin.service.GunsService;
import com.market.skin.service.ItemsService;
import com.market.skin.service.PatternsService;
import com.market.skin.service.TransService;
import com.market.skin.service.UsersService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.mapping.PropertyReferenceException;
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
@RequestMapping(value = "/showroom")
public class PublicController {
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

    public List<ClientItems> result(List<Items> dbItems){
        List<ClientItems> result = new ArrayList<ClientItems>();;
        for(Items item : dbItems){
            result.add(
                new ClientItems(
                    item.getId(),
                    gunsService.findById(((Items)item).getGunId()).get().getGunName(),
                    patternsService.findById(((Items)item).getPatternId()).get().getName(),
                    usersService.findById(item.getCreatorId()).get().getUserName()
                 ));
        }
        return result;
    }

    //ITEMS
    @GetMapping("/all/items")
    ResponseEntity<?> loadItems(@RequestBody(required = false) Hashtable<String, String> attr){
        if(attr.containsKey("gunname") && attr.containsKey("patternname")){
            String gunName = attr.get("gunname");
            String patternName = attr.get("patternname");
            int gunId, pattId;

            try{gunId = gunsService.findByGunName(gunName).get(0).getId();}
            catch (IndexOutOfBoundsException indexOutOfBoundsException){ throw new RecordNotFoundException("No gun named: " + gunName);}

            try{pattId = patternsService.findByPatternName(patternName).get(0).getId();}
            catch (IndexOutOfBoundsException indexOutOfBound) { throw new RecordNotFoundException("No pattern named: " + patternName);}

            Items dbItem = itemsService.findByGunIdAndPatternId(pattId, gunId);
            return ResponseEntity.ok(new ClientItems(dbItem.getId(), attr.get("gunname"), attr.get("patternname"), usersService.findById(dbItem.getCreatorId()).get().getUserName()));
        }
        else if(attr.size() == 1){
            if(attr.containsKey("gunname")){
                return ResponseEntity.ok(this.result(itemsService.findByGunId(
                    gunsService.findByGunName(attr.get("gunname")).get(0).getId())));
                }
            else if(attr.containsKey("patternname")){
                return ResponseEntity.ok(this.result(itemsService.findByPatternId(
                    patternsService.findByPatternName(attr.get("patternname")).get(0).getId())));
            }
            else{
                throw new ConstraintViolationException("Entity does not contain any field matched" + attr.keySet());
            }
        } else{ throw new RecordNotFoundException();}
    }

    //PATTERN
    @GetMapping("/all/patterns")
    ResponseEntity<?> loadPatternName(){
        return ResponseEntity.ok(
            patternsService.findAll().stream().map(patt -> patt.getName().strip()).collect(Collectors.toList()));
    }

    //GUNS
    @GetMapping("/all/guns")
    ResponseEntity<?> loadGunName(){
        return ResponseEntity.ok(
            gunsService.findAll().stream().map(gun -> gun.getGunName().strip()).collect(Collectors.toList()));
    }

    //PAGE
    @GetMapping("/items")
    ResponseEntity<?> pageRequest(@RequestParam(name = "page") int page){
        Page<?> result = null;
        if(page ==0){result = itemsService.homePage();}
        else{result = itemsService.showPage(page-1);}
        
        if (result.isEmpty()){ return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);}
        return ResponseEntity.ok(this.result((List<Items>) result.getContent()));
    }
    

    //SORT
    @GetMapping("/sort/items")
    ResponseEntity<?> sort(@RequestParam(name = "page") int page, @RequestParam(name = "sortby") String sortby, @RequestParam(name = "asc") String asc){
        Boolean sort = Boolean.parseBoolean(asc);
        try{return ResponseEntity.ok(itemsService.sortByAttr(sortby, page, sort));}
        catch(PropertyReferenceException ex){ throw new ConstraintViolationException("No such field name" + sortby);}
    }

    //TRANSACTIONS
    @PostMapping("/create/transactions")
    ResponseEntity<?> createTransaction(@RequestBody Hashtable<String, String> tran){
        if(tran.containsKey("gun") & tran.containsKey("pattern") & tran.containsKey("user")){
            try{
                int pattid, gunid, itemid;
                try{ pattid = patternsService.findByPatternName(tran.get("pattern")).get(0).getId();} 
                catch(IndexOutOfBoundsException indexOutOfBoundsException){throw new RecordNotFoundException("Cant found any pattern matched: " + tran.get("pattern"));}
                
                try{ gunid = gunsService.findByGunName(tran.get("gun")).get(0).getId();}
                catch(IndexOutOfBoundsException indexOutOfBoundsException){throw new RecordNotFoundException("Cant found any gun matched: " + tran.get("gun"));}

                try{ itemid = itemsService.findByGunIdAndPatternId(pattid, gunid).getId();}
                catch(IndexOutOfBoundsException indexOutOfBoundsException){throw new RecordNotFoundException("Cant found any items matched: " +tran.get("gun") + "-" + tran.get("pattern"));}
                
                transService.create(new Transactions(
                    Integer.parseInt(tran.get("user")),
                    itemsService.findByGunIdAndPatternId(pattid, gunid).getId(),
                    LocalDateTime.now(),
                    tran.get("status")));
                return ResponseEntity.ok("Create transaction successfully.");

            }catch(DataIntegrityViolationException dataIntegrityViolationException){throw new ConstraintViolationException("Transaction is already existed.");}
        }
        else{throw new MissingServletRequestParameterException();}
    }

    @DeleteMapping("/delete/transactions")
    ResponseEntity<?> deleteTransaction(@RequestBody Hashtable<String, String> trans){
        Transactions tran = transService.findById(Integer.parseInt(trans.get("id"))).get();
        if(Integer.parseInt(trans.get("userid")) == tran.getUserId()){
            transService.delete(tran.getUserId());
            return ResponseEntity.ok("Delete transaction successfully.");
        }
        else{
            throw new TransactionSystemException("Cant authorized transaction's owner.");
        }
    }
}
