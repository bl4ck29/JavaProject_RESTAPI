package com.market.skin.service;

import java.util.Optional;

import com.market.skin.model.Users;
import com.market.skin.repository.UsersRepository;
import com.market.skin.security.service.UserDetailsImp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

@Service
public class UsersService implements UserDetailsService{
    @Autowired
    private UsersRepository repository;

    public Optional<Users> findById(int id){
        return repository.findById(id);
    }

    public void createUser(Users user){
        repository.save(user);
    }
    
    public void deleteUser(int id){
        repository.deleteById(id);
    }

    public Optional<Users> findByUserName(String name){
        return repository.findByUserName(name);
    }

    public Optional<Users> findByEmail(String email){
        return repository.findByEmail(email);
    }

    public Optional<Users> findByLoginType(String login_type){
        return repository.findByloginType(login_type);
    }

    public void modify(Users newUser){
        repository.findById(newUser.getId()).map(user->{
            user.setUserName(newUser.getUserName());
            user.setEmail(newUser.getEmail());
            user.setLoginType(newUser.getLoginType());
            // user.setRoleId(newUser.getRoleId());
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

    @Override @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
        Users user = repository.findByUserName(username)
            .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " +username));
        return UserDetailsImp.build(user);
    }
}
