package com.market.skin.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolationException;

import com.market.skin.controller.payload.request.ModifyTransactionRequest;
import com.market.skin.exception.ConstraintViolation;
import com.market.skin.exception.RecordNotFoundException;
import com.market.skin.exception.UnauthorizedOwnerException;
import com.market.skin.model.Items;
import com.market.skin.model.Transactions;
import com.market.skin.model.Users;
import com.market.skin.model.DTO.ItemsDTO;
import com.market.skin.model.DTO.TransactionDTO;
import com.market.skin.repository.TransRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Sort;

@Service
public class TransService {
    @Autowired
    private TransRepository repository;
    @Autowired
    ItemsService itemsService;

    public TransactionDTO toTransactionDTO(Transactions trans){
        List<ItemsDTO> result = new ArrayList<>();
        for(Items item : trans.getLikes()){
            result.add(itemsService.findById(item.getItem_id()));
        }
        return new TransactionDTO(
            trans.getUser().getUserName(), result, trans.getUpdate_time(), trans.getStatus());
    }

    public TransService(TransRepository repository){
        this.repository = repository;
    }

    public TransactionDTO findById(int id){
        Optional<Transactions> result = repository.findById(id);
        if(result.isEmpty()){
            throw new RecordNotFoundException();
        }
        return this.toTransactionDTO(result.get());
    }

    public void create(Transactions trans){
        try{
            repository.save(trans);
        }
        catch(ConstraintViolationException ex){
            throw new ConstraintViolation("Transaction is already existed.");
        }
    }

    public void delete(int id){
        Optional<Transactions> result = repository.findById(id);
        if(result.isEmpty()){
            throw new RecordNotFoundException("No record's id matched: " + id);
        }
        repository.deleteById(id);
    }

    public List<TransactionDTO> findByItemId(int id){
        List<Transactions> result = repository.findByItemId(id);
        if(result.isEmpty()){
            throw new RecordNotFoundException("No record's item_id matched: " + id);
        }
        return result.stream().map(tran ->{
            return this.toTransactionDTO(tran);
        }).collect(Collectors.toList());
    }

    public List<TransactionDTO> findByUserId(int id){
        List<Transactions> result = repository.findByUserId(id);
        if(result.isEmpty()){
            throw new RecordNotFoundException("No record's user_id matched: " + id);
        }
        return result.stream().map(tran ->{
            return this.toTransactionDTO(tran);
        }).collect(Collectors.toList());
    }

    public List<TransactionDTO> findByStatus(String status){
        List<Transactions> result = repository.findByStatus(status);
        if(result.isEmpty()){
            throw new RecordNotFoundException("No record's item_id matched: " + status);
        }
        return result.stream().map(tran ->{
            return this.toTransactionDTO(tran);
        }).collect(Collectors.toList());
    }

    public void modify(ModifyTransactionRequest request){
        Users user = request.getUser();
        Transactions trans = request.getTrans();
        if(user.getId() == trans.getUserId()){
            repository.findById(trans.getId()).map(tran->{
                tran.setItemId(trans.getItemId());
                tran.setStatus(trans.getStatus());
                tran.setUpdate_time(LocalDateTime.now());
                return repository.save(tran);
            }).orElseThrow(()-> new RecordNotFoundException("No record's id matched: "));
        }
        else{
            throw new UnauthorizedOwnerException("You are not the owner of this transaction?");
        }
    }

    public Page<TransactionDTO> showPage(int page, int size){
        PageRequest pageRequest = PageRequest.of(page, size);
        List<Transactions> lstTransactions = repository.findAll(pageRequest).getContent();
        List<TransactionDTO> result = new ArrayList<>();
        for(Transactions item : lstTransactions){
            result.add(this.toTransactionDTO(item));
        }
        return new PageImpl<>(result, pageRequest, repository.findAll().size());
    }

    public Page<TransactionDTO> sortByAttr(String attr, int page, int size, Boolean asc){
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(attr).descending());
        if(asc){pageRequest = PageRequest.of(page, size, Sort.by(attr).ascending());}
        List<Transactions> lstTransactions = repository.findAll(pageRequest).getContent();
        List<TransactionDTO> result = new ArrayList<>();
        for(Transactions item : lstTransactions){
            result.add(this.toTransactionDTO(item));
        }
        return new PageImpl<>(result, pageRequest, repository.findAll().size());
    }
}
