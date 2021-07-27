package com.market.skin.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import com.market.skin.exception.RecordNotFoundException;
import com.market.skin.model.Users;
import com.market.skin.model.DTO.UsersDTO;
import com.market.skin.repository.UsersRepository;
import com.market.skin.security.service.UserDetailsImp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UsersService implements UserDetailsService{
    @Autowired
    private UsersRepository repository;

    public UsersDTO toUsersDTO(Users user){
        return new UsersDTO(user.getUserName(), user.getLoginType(), user.getEmail(), user.getProfile());
    }

    public UsersDTO findById(int id){
        Optional<Users> result = repository.findById(id);
        if(result.isEmpty()){
            throw new RecordNotFoundException();
        }
        return this.toUsersDTO(result.get());
    }
    
    public void deleteUser(int id){
        if(repository.findById(id).isEmpty()){
            throw new RecordNotFoundException("No record's id match: "+ id);
        }
        repository.deleteById(id);
    }

    public UsersDTO findByUserName(String name){
        Optional<Users> result = repository.findByUserName(name);
        if(result.isEmpty()){
            throw new RecordNotFoundException();
        }
        return this.toUsersDTO(result.get());
    }

    public UsersDTO findByEmail(String email){
        Optional<Users> result = repository.findByEmail(email);
        if(result.isEmpty()){
            throw new RecordNotFoundException();
        }
        return this.toUsersDTO(result.get());
    }

    public List<UsersDTO> findByLoginType(String login_type){
        List<Users> lstUsers = repository.findByLoginType(login_type);
        if(lstUsers.isEmpty()){
            throw new RecordNotFoundException("No record's login_type matched: " + login_type);
        }
        List<UsersDTO> result = new ArrayList<>();
        for(Users user : lstUsers){
            result.add(this.toUsersDTO(user));
        }
        return result;
    }

    public void modify(Users newUser){
        repository.findById(newUser.getId()).map(user->{
            user.setUserName(newUser.getUserName());
            user.setEmail(newUser.getEmail());
            user.setLoginType(newUser.getLoginType());
            user.setProfile(newUser.getProfile());
            return repository.save(user);
        }).orElseThrow(()-> new RecordNotFoundException());
    }

    public Page<UsersDTO> showPage(int page, int size){
        PageRequest pageRequest = PageRequest.of(page, size);
        List<Users> lstUsers = repository.findAll(pageRequest).getContent();
        List<UsersDTO> result = new ArrayList<>();
        for(Users item : lstUsers){
            result.add(this.toUsersDTO(item));
        }
        return new PageImpl<>(result, pageRequest, repository.findAll().size());
    }

    public Page<UsersDTO> sortByAttr(String attr, int page, int size, Boolean asc){
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(attr).descending());
        if(asc){pageRequest = PageRequest.of(page, size, Sort.by(attr).ascending());}
        List<Users> lstUsers = repository.findAll(pageRequest).getContent();
        List<UsersDTO> result = new ArrayList<>();
        for(Users item : lstUsers){
            result.add(this.toUsersDTO(item));
        }
        return new PageImpl<>(result, pageRequest, repository.findAll().size());
    }

    @Override @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
        Users user = repository.findByUserName(username)
            .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " +username));
        return UserDetailsImp.build(user);
    }
}
