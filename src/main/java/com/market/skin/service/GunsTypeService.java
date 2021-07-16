package com.market.skin.service;

import java.util.List;
import java.util.Optional;

import com.market.skin.model.GunsType;
import com.market.skin.repository.GunsTypeRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GunsTypeService {
    @Autowired
    private GunsTypeRepository repository;

    public GunsTypeService(GunsTypeRepository repository){
        this.repository = repository;
    }

    public Optional<GunsType> findById(int id){
        return repository.findById(id);
    }

    public void createGuns(GunsType gun){
        repository.save(gun);
    }

    public List<GunsType> findByTypeName(String name){
        return repository.findByTypeName(name);
    }
    
    public Optional<GunsType> deleteGun(int id){
        Optional<GunsType> res = repository.findById(id);
        repository.deleteById(id);
        return res;
    }
    
    public void modifyDetails(GunsType newType){
        repository.findById(newType.getId()).map(gt->{
            gt.setGunName(newType.getName());
            return repository.save(gt);
        });
    }
}
