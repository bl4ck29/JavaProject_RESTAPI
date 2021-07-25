package com.market.skin.service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import com.market.skin.model.Items;
import com.market.skin.repository.ItemsRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.data.domain.Sort;

@Service
public class ItemsService {
    @Autowired
    private ItemsRepository repository;

    public ItemsService(ItemsRepository repository){
        this.repository = repository;
    }

    public Optional<Items> findById(int id){
        return repository.findById(id);
    }

    public Items create(Items newItem){
        repository.save(newItem);
        return newItem;
    }

    public Items findByGunIdAndPatternId(int patt_id, int gun_id){
        List<Items> gun = repository.findByGunId(gun_id);
        List<Items> patt = repository.findByPatternId(patt_id);
        gun.retainAll(patt);
        return gun.get(0);
    }

    public List<Items> findByGunId(int id){
        return repository.findByGunId(id);
    }

    public List<Items> findByPatternId(int id){
        return repository.findByPatternId(id);
    }

    public Optional<Items> deleteById(int id){
        Optional<Items> res = repository.findById(id);
        repository.deleteById(id);
        return res;
    }

    public void modifyDetails(Items item){
        repository.findById(item.getId()).map( i->{
            i.setCreatorId(item.getCreatorId());
            i.setGunId(item.getGunId());
            i.setImage(item.getImage());
            i.setPatternId(item.getPatternId());
            return repository.save(i);
        });
    }

    public Page<Items> homePage(){
        return repository.findAll(PageRequest.of(0, 3));
    }

    public Page<Items> showPage(@PathVariable int page){
        return repository.findAll(PageRequest.of(page, 12));
    }

    public Page<Items> sortByAttr(String attr, int page, Boolean asc){
        if(asc){
            return repository.findAll(PageRequest.of(page, 9, Sort.by(attr).ascending()));
        }
        return repository.findAll(PageRequest.of(page, 9, Sort.by(attr).descending()));
    }
}
