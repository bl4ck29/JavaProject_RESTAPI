package com.market.skin.service;

import com.market.skin.repository.RolesRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.ConstraintViolationException;

import com.market.skin.exception.ConstraintViolation;
import com.market.skin.exception.RecordNotFoundException;
import com.market.skin.model.Roles;
import com.market.skin.model.RolesName;
import com.market.skin.model.DTO.RolesDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Sort;

@Service
public class RolesService {
    @Autowired
    private RolesRepository repository;

    public RolesDTO toRolesDTO(Roles role){
        return new RolesDTO(role.getName(), role.getRole_id());
    }

    public RolesService(RolesRepository repository){
        this.repository = repository;
    }

    public RolesDTO findById(int id){
        Optional<Roles> result = repository.findById(id);
        if(result.isEmpty()){
            throw new RecordNotFoundException("Not record's id matched: "+id);
        }
        return this.toRolesDTO(result.get());
    }

    public void createRole(Roles role){
        try{
            repository.save(role);
        } catch (ConstraintViolationException ex){
            throw new ConstraintViolation("Role is already existed.");
        }
    }
    
    public void deleteRole(int id){
        if(repository.findById(id).isEmpty()){
            throw new RecordNotFoundException("No record's id matched: " + id);
        }
        repository.deleteById(id);
    }

    public RolesDTO findByRoleName(RolesName name){
        Optional<Roles> result = repository.findByName(name);
        if(result == null){
            throw new RecordNotFoundException("No record's name matched: " + name);
        }
        return this.toRolesDTO(result.get());
    }
    
    public void modify(Roles newRole){
        repository.findById(newRole.getRole_id()).map(role->{
            role.setName(newRole.getName());
            return repository.save(role);
        }).orElseThrow(() -> new RecordNotFoundException());
    }

    public Page<RolesDTO> showPage(int page, int size){
        PageRequest pageRequest = PageRequest.of(page, size);
        List<Roles> lstRoles = repository.findAll(pageRequest).getContent();
        List<RolesDTO> result = new ArrayList<>();
        for(Roles item : lstRoles){
            result.add(this.toRolesDTO(item));
        }
        return new PageImpl<>(result, pageRequest, repository.findAll().size());
    }

    public Page<RolesDTO> sortByAttr(String attr, int page, int size, Boolean asc){
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(attr).descending());
        if(asc){pageRequest = PageRequest.of(page, size, Sort.by(attr).ascending());}
        List<Roles> lstRoles = repository.findAll(pageRequest).getContent();
        List<RolesDTO> result = new ArrayList<>();
        for(Roles item : lstRoles){
            result.add(this.toRolesDTO(item));
        }
        return new PageImpl<>(result, pageRequest, repository.findAll().size());
    }
}
