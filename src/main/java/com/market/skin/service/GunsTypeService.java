package com.market.skin.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolationException;

import com.market.skin.exception.ConstraintViolation;
import com.market.skin.exception.RecordNotFoundException;
import com.market.skin.model.GunsType;
import com.market.skin.model.DTO.TypesDTO;
import com.market.skin.repository.GunsTypeRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Sort;

@Service
public class GunsTypeService {
    @Autowired
    private GunsTypeRepository repository;

    public TypesDTO toTypesDTO(GunsType type){
        return new TypesDTO(type.getTypeName(), type.getId());
    }

    public GunsTypeService(GunsTypeRepository repository){
        this.repository = repository;
    }

    public List<TypesDTO> findAll(){
        return repository.findAll().stream().map( gt ->{
            return this.toTypesDTO(gt);
        }).collect(Collectors.toList());
    }

    public TypesDTO findById(int id){
        Optional<GunsType> result = repository.findById(id);
        if (result.isEmpty()){
            throw new RecordNotFoundException("No record's id matched: " + id);
        }
        return this.toTypesDTO(repository.findById(id).get());
    }

    public void createGuns(GunsType gun){
        try{
            repository.save(gun);
        } catch(ConstraintViolationException ex){
            throw new ConstraintViolation("Type already existed");
        }
    }

    public TypesDTO findByTypeName(String name){
        Optional<GunsType> result = repository.findByTypeName(name);
        if(result.isEmpty()){
            throw new RecordNotFoundException();
        }
        return this.toTypesDTO(repository.findByTypeName(name).get());
    }
    
    public void deleteGun(int id){
        if(repository.findById(id).isEmpty()){
            throw new RecordNotFoundException("No record's id matched: " + id);
        }
        repository.deleteById(id);
    }
    
    public void modify(GunsType newType){
        repository.findById(newType.getId()).map(gt->{
            gt.setTypeName(newType.getTypeName());
            return repository.save(gt);
        }).orElseThrow(() -> new RecordNotFoundException("No record's id matched: " + newType.getId()));
    }

    public Page<TypesDTO> showPage(int page, int size){
        PageRequest pageRequest = PageRequest.of(page, size);
        List<GunsType> lstGunsType = repository.findAll(pageRequest).getContent();
        List<TypesDTO> result = new ArrayList<>();
        for(GunsType item : lstGunsType){
            result.add(this.toTypesDTO(item));
        }
        return new PageImpl<>(result, pageRequest, repository.findAll().size());
    }

    public Page<TypesDTO> sortByAttr(String attr, int page, int size, Boolean asc){
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(attr).descending());
        if(asc){pageRequest = PageRequest.of(page, size, Sort.by(attr).ascending());}
        List<GunsType> lstGunsType = repository.findAll(pageRequest).getContent();
        List<TypesDTO> result = new ArrayList<>();
        for(GunsType item : lstGunsType){
            result.add(this.toTypesDTO(item));
        }
        return new PageImpl<>(result, pageRequest, repository.findAll().size());
    }
}
