package com.market.skin.service;

import java.util.Optional;

import com.market.skin.model.Transactions;
import com.market.skin.repository.TransRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.data.domain.Sort;

@Service
public class TransService {
    @Autowired
    private TransRepository repository;
    public TransService(TransRepository repository){
        this.repository = repository;
    }

    public Optional<Transactions> findById(int id){
        return repository.findById(id);
    }

    public void create(Transactions trans){
        repository.save(trans);
    }

    public Optional<Transactions> delete(int id){
        Optional<Transactions> res = repository.findById(id);
        repository.deleteById(id);
        return res;
    }

    public void modify(Transactions newTrans){
        repository.findById(newTrans.getId()).map(trans->{
            trans.setItemId(newTrans.getItemId());
            trans.setStatus(newTrans.getStatus());
            trans.setUpdateTime(newTrans.getUpdateTime());
            return repository.save(trans);
        });
    }

    public Page<Transactions> homePage(){
        return repository.findAll(PageRequest.of(0, 3));
    }

    public Page<Transactions> showPage(@PathVariable int page){
        return repository.findAll(PageRequest.of(page, 12));
    }

    public Page<Transactions> sortByAttr(String attr, int page, Boolean asc){
        if(asc){
            return repository.findAll(PageRequest.of(page, 9, Sort.by(attr).ascending()));
        }
        return repository.findAll(PageRequest.of(page, 9, Sort.by(attr).descending()));
    }
}
