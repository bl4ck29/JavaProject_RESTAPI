package com.market.skin.service;

import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolationException;

import java.util.ArrayList;
import java.util.List;

import com.market.skin.exception.ConstraintViolation;
import com.market.skin.exception.RecordNotFoundException;
import com.market.skin.model.Guns;
import com.market.skin.model.DTO.GunsDTO;
import com.market.skin.repository.GunsRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Sort;

@Service
public class GunsService {
    @Autowired
    GunsRepository repository;

    public GunsDTO toGunsDTO(Guns gun){
        return new GunsDTO(gun.getGun_id(), gun.getGunName(), gun.getGunsType().getTypeName());
    }

    public GunsService(GunsRepository repository){
        this.repository = repository;
    }

    public GunsDTO findById(int id){
        Optional<Guns> result = repository.findById(id);
        if (result.isEmpty()){
            throw new RecordNotFoundException();
        }
        return this.toGunsDTO(result.get());
    }

    public List<GunsDTO> findByGunName(String name){
        List<Guns> result = repository.findByGunName(name);
        if(result.isEmpty()){
            throw new RecordNotFoundException();
        }
        return repository.findByGunName(name).stream().map(gun -> {
            return this.toGunsDTO(gun);
        }).collect(Collectors.toList());
    }

    public List<GunsDTO> findByTypeId(int id){
        List<Guns> result = repository.findByTypeId(id);
        if(result.isEmpty()){
            throw new RecordNotFoundException();
        }
        return repository.findByTypeId(id).stream().map(gun -> {
            return this.toGunsDTO(gun);
        }).collect(Collectors.toList());
    }

    public void modify(Guns other){
        repository.findById(other.getGun_id()).map(gun ->{
            gun.setGunName(other.getGunName());
            gun.setTypeId(other.getTypeId());
            repository.save(gun);
            return repository.save(gun);
        }).orElseThrow(() -> new RecordNotFoundException("No record matched."));
    }

    public void createGuns(Guns gun){
        try{
            repository.save(gun);
        }
        catch(ConstraintViolationException ex){
            throw new ConstraintViolation("Gun already existed.");
        }
    }

    public void deleteGun(int id){
        if(repository.findById(id).isEmpty()){
            throw new RecordNotFoundException();
        }
        repository.deleteById(id);
    }
    
    public void modifyDetails(Guns newGun){
        repository.findById(newGun.getGun_id()).map(gun->{
            gun.setGunName(newGun.getGunName());
            gun.setTypeId(newGun.getTypeId());
            return repository.save(gun);
        }).orElseThrow(() -> new RecordNotFoundException("No record matched."));
    }
    
    public Page<GunsDTO> showPage(int page, int size){
        PageRequest pageRequest = PageRequest.of(page, size);
        List<Guns> lstGuns = repository.findAll(pageRequest).getContent();
        List<GunsDTO> result = new ArrayList<>();
        for(Guns item : lstGuns){
            result.add(this.toGunsDTO(item));
        }
        return new PageImpl<>(result, pageRequest, repository.findAll().size());
    }

    public Page<GunsDTO> sortByAttr(String attr, int page, int size, Boolean asc){
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(attr).descending());
        if(asc){pageRequest = PageRequest.of(page, size, Sort.by(attr).ascending());}
        List<Guns> lstGuns = repository.findAll(pageRequest).getContent();
        List<GunsDTO> result = new ArrayList<>();
        for(Guns item : lstGuns){
            result.add(this.toGunsDTO(item));
        }
        return new PageImpl<>(result, pageRequest, repository.findAll().size());
    }
}
