package com.market.skin.service;

import java.util.Optional;
import com.market.skin.exception.UserExistedById;
import com.market.skin.exception.UserIdNotFound;
import com.market.skin.model.Users;
import com.market.skin.repository.UsersRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

@Service
public class UsersService {
    @Autowired
    private UsersRepository repository;

    public Optional<Users> findById(int id) throws UserIdNotFound{
        return repository.findById(id);
    }

    public void createUser(Users user) throws UserExistedById{
        repository.save(user);
    }
    
    public void deleteUser(int id){
        repository.deleteById(id);
    }

    public void modifyDetails(Users newUser){
        repository.findById(newUser.getId()).map(user->{
            user.setUserName(newUser.getUserName());
            user.setEmail(newUser.getEmail());
            user.setLoginType(newUser.getLoginType());
            user.setRoleId(newUser.getRoleId());
            user.setProfile(newUser.getProfile());
            return repository.save(user);
        });
    }

    public Page<Users> homePage(){
        return repository.findAll(PageRequest.of(0, 3));
    }

    public Page<Users> showPage(@PathVariable int page){
        return repository.findAll(PageRequest.of(page, 12));
    }

    public Page<Users> sortByAttr(String attr, int page, Boolean asc){
        if(asc){
            return repository.findAll(PageRequest.of(page, 9, Sort.by(attr).ascending()));
        }
        return repository.findAll(PageRequest.of(page, 9, Sort.by(attr).descending()));
    }
}
