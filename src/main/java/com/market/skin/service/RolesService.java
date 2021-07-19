package com.market.skin.service;

import com.market.skin.repository.RolesRepository;

import java.util.Optional;

import com.market.skin.model.Roles;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.data.domain.Sort;

@Service
public class RolesService {
    @Autowired
    private RolesRepository repository;

    public RolesService(RolesRepository repository){
        this.repository = repository;
    }

    public Optional<Roles> findById(int id){
        return repository.findById(id);
    }

    public void createRole(Roles role){
        repository.save(role);
    }
    public Optional<Roles> deleteRole(int id){
        Optional<Roles> res = repository.findById(id);
        repository.deleteById(id);
        return res;
    }

    // public List<Roles> findByRoleName(String name){
    //     return repository.findByRoleName(name);
    // }
    
    public void modifyDetails(Roles newRole){
        repository.findById(newRole.getId()).map(role->{
            role.setRoleName(newRole.getRoleName());
            return repository.save(role);
        });
    }
    
    public Page<Roles> homePage(){
        return repository.findAll(PageRequest.of(0, 3));
    }

    public Page<Roles> showPage(@PathVariable int page){
        return repository.findAll(PageRequest.of(page, 12));
    }

    public Page<Roles> sortByAttr(String attr, int page, Boolean asc){
        if(asc){
            return repository.findAll(PageRequest.of(page, 9, Sort.by(attr).ascending()));
        }
        return repository.findAll(PageRequest.of(page, 9, Sort.by(attr).descending()));
    }
}
