package com.market.skin.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.ConstraintViolationException;

import com.market.skin.exception.ConstraintViolation;
import com.market.skin.exception.RecordNotFoundException;
import com.market.skin.model.Patterns;
import com.market.skin.model.DTO.PatternsDTO;
import com.market.skin.repository.PatternsRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Sort;

@Service
public class PatternsService {
    @Autowired
    private PatternsRepository repository;

    public PatternsDTO toPatternsDTO(Patterns pattern){
        return new PatternsDTO(pattern.getPatternName(), pattern.getPattern_id());
    }
    
    public PatternsService(PatternsRepository repository){
        this.repository = repository;
    }

    public PatternsDTO findById(int id){
        Optional<Patterns> result = repository.findById(id);
        if(result.isEmpty()){
            throw new RecordNotFoundException("No record's id matched: " + id);
        }
        return this.toPatternsDTO(repository.findById(id).get());
    }

    public void create(Patterns newPatt){
        try{
            repository.save(newPatt);
        }
        catch(ConstraintViolationException ex){
            throw new ConstraintViolation("Patterns already existed.");
        }
    }

    public void delete(int id){
        if(repository.findById(id).isEmpty()){
            throw new RecordNotFoundException("No record's id matched: " + id);
        }
        repository.deleteById(id);
    }

    public PatternsDTO findByPatternName(String name){
        Optional<Patterns> result = repository.findByPatternName(name);
        if(result.isEmpty()){
            throw new RecordNotFoundException("No record's name matched: " + name);
        }
        return this.toPatternsDTO(repository.findByPatternName(name).get());
    }

    public void modify(Patterns newPatt) throws RecordNotFoundException{
        repository.findById(newPatt.getPattern_id()).map(patt->{
            patt.setPatternName(newPatt.getPatternName());
            return repository.save(patt);
        }).orElseThrow(() -> new RecordNotFoundException("No record's id matched: " + newPatt.getPattern_id()));
    }

    public Page<PatternsDTO> showPage(int page, int size){
        PageRequest pageRequest = PageRequest.of(page, size);
        List<Patterns> lstPatterns = repository.findAll(pageRequest).getContent();
        List<PatternsDTO> result = new ArrayList<>();
        for(Patterns item : lstPatterns){
            result.add(this.toPatternsDTO(item));
        }
        return new PageImpl<>(result, pageRequest, repository.findAll().size());
    }

    public Page<PatternsDTO> sortByAttr(String attr, int page, int size, Boolean asc){
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(attr).descending());
        if(asc){pageRequest = PageRequest.of(page, size, Sort.by(attr).ascending());}
        List<Patterns> lstPatterns = repository.findAll(pageRequest).getContent();
        List<PatternsDTO> result = new ArrayList<>();
        for(Patterns item : lstPatterns){
            result.add(this.toPatternsDTO(item));
        }
        return new PageImpl<>(result, pageRequest, repository.findAll().size());
    }
}
