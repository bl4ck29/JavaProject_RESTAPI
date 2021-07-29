package com.market.skin.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolationException;

import com.market.skin.exception.ConstraintViolation;
import com.market.skin.exception.RecordNotFoundException;
import com.market.skin.model.Items;
import com.market.skin.model.DTO.ItemsDTO;
import com.market.skin.repository.ItemsRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Sort;

@Service
public class ItemsService {
    @Autowired
    ItemsRepository repository;

    public ItemsService(ItemsRepository repository){
        this.repository = repository;
    }

    public ItemsDTO toItemsDTO(Items item){
        return new ItemsDTO(
            item.getItem_id(), 
            item.getGuns().getGunName(), 
            item.getCre_id().getUserName(), 
            item.getPatterns().getPatternName(), 
            item.getItem_image(),
            item.getPrice());
    }

    public void create(Items item){
        try{
            repository.save(item);
        }catch(ConstraintViolationException ex){
            throw new ConstraintViolation("Item is already existed");
        }
    }

    public ItemsDTO findById(int id){
        Optional<Items> result = repository.findById(id);
        if(result.isEmpty()){
            throw new RecordNotFoundException("No record's id matched:" + id);
        }
        return this.toItemsDTO(result.get());
    }

    public List<ItemsDTO> findPatternId(int id){
        List<Items> result = repository.findByPatternId(id);
        if(result.isEmpty()){
            throw new RecordNotFoundException("No record's id matched:" + id);
        }
        return result.stream().map(item ->{
            return this.toItemsDTO(item);
        }).collect(Collectors.toList());
    }

    public List<ItemsDTO> findByGunId(int id){
        List<Items> result = repository.findByGunId(id);
        if(result.isEmpty()){
            throw new RecordNotFoundException("No record's id matched:" + id);
        }
        return result.stream().map(item ->{
            return this.toItemsDTO(item);
        }).collect(Collectors.toList());
    }

    public ItemsDTO findByGunIdAndPatternId(int patt_id, int gun_id){
        List<Items> gun = repository.findByGunId(gun_id);
        List<Items> patt = repository.findByPatternId(patt_id);
        if(gun.retainAll(patt)){
            Items item = gun.get(0);
            return this.toItemsDTO(item);
        }
        throw new RecordNotFoundException("No record matches id:%d, pattern:%d".formatted(gun_id, patt_id));
    }


    public void deleteById(int id){
        if(repository.findById(id) == null){
            throw new RecordNotFoundException();
        }
        repository.deleteById(id);
    }

    public void modifyDetails(Items item){
        repository.findById(item.getItem_id()).map( i->{
            i.setCreatorId(item.getCreatorId());
            i.setGunId(item.getGunId());
            i.setItem_image(item.getItem_image());;
            i.setPatternId(item.getPatternId());
            return repository.save(i);
        }).orElseThrow(() -> new RecordNotFoundException());
    }

    public Page<ItemsDTO> showPage(int page, int size){
        PageRequest pageRequest = PageRequest.of(page, size);
        List<Items> lstItems = repository.findAll(pageRequest).getContent();
        List<ItemsDTO> result = new ArrayList<>();
        for(Items item : lstItems){
            result.add(this.toItemsDTO(item));
        }
        return new PageImpl<>(result, pageRequest, repository.findAll().size());
    }

    public Page<ItemsDTO> sortByAttr(String attr, int page, int size, Boolean asc){
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(attr).descending());
        if(asc){pageRequest = PageRequest.of(page, size, Sort.by(attr).ascending());}
        List<Items> lstItems = repository.findAll(pageRequest).getContent();
        List<ItemsDTO> result = new ArrayList<>();
        for(Items item : lstItems){
            result.add(this.toItemsDTO(item));
        }
        return new PageImpl<>(result, pageRequest, repository.findAll().size());
    }
}
