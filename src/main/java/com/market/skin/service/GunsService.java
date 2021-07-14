package com.market.skin.service;

import java.util.Optional;

import com.market.skin.model.Guns;
import com.market.skin.repository.GunsRepository;
import com.market.skin.exception.GunExistedById;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.data.domain.Sort;

@Service
public class GunsService {
    @Autowired
    private GunsRepository repository;

    public GunsService(GunsRepository repository){
        this.repository = repository;
    }

    public Optional<Guns> findById(int id){
        return repository.findById(id);
    }

    public void createGuns(Guns gun) throws GunExistedById{
        repository.save(gun);
    }
    public Optional<Guns> deleteGun(int id){
        Optional<Guns> res = repository.findById(id);
        repository.deleteById(id);
        return res;
    }
    
    public void modifyDetails(Guns newGun){
        repository.findById(newGun.getId()).map(gun->{
            gun.setGunName(newGun.getGunName());
            gun.setTypeId(newGun.getTypeId());
            return repository.save(gun);
        });
    }
    
    public Page<Guns> homePage(){
        return repository.findAll(PageRequest.of(0, 3));
    }

    public Page<Guns> showPage(@PathVariable int page){
        return repository.findAll(PageRequest.of(page, 12));
    }

    public Page<Guns> sortByAttr(String attr, int page, Boolean asc){
        if(asc){
            return repository.findAll(PageRequest.of(page, 9, Sort.by(attr).ascending()));
        }
        return repository.findAll(PageRequest.of(page, 9, Sort.by(attr).descending()));
    }
}
