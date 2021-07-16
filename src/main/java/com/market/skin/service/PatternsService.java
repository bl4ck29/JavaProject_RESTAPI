package com.market.skin.service;

import java.util.List;
import java.util.Optional;

import com.market.skin.model.Patterns;
import com.market.skin.repository.PatternsRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.data.domain.Sort;

@Service
public class PatternsService {
    @Autowired
    private PatternsRepository repository;
    
    public PatternsService(PatternsRepository repository){
        this.repository = repository;
    }

    public Optional<Patterns> findById(int id){
        return repository.findById(id);
    }

    public Patterns create(Patterns newPatt){
        repository.save(newPatt);
        return newPatt;
    }

    public Optional<Patterns> delete(int id){
        Optional<Patterns> res = repository.findById(id);
        repository.deleteById(id);
        return res;
    }

    public List<Patterns> findByPatternName(String name){
        return repository.findByPatternName(name);
    }

    public void modify(Patterns newPatt){
        repository.findById(newPatt.getId()).map(patt->{
            patt.setName(newPatt.getName());
            return repository.save(patt);
        });
    }

    public Page<Patterns> homePage(){
        return repository.findAll(PageRequest.of(0, 3));
    }

    public Page<Patterns> showPage(@PathVariable int page){
        return repository.findAll(PageRequest.of(page, 12));
    }

    public Page<Patterns> sortByAttr(String attr, int page, Boolean asc){
        if(asc){
            return repository.findAll(PageRequest.of(page, 9, Sort.by(attr).ascending()));
        }
        return repository.findAll(PageRequest.of(page, 9, Sort.by(attr).descending()));
    }
}
