package com.market.skin.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;

import com.market.skin.controller.payload.request.LoginRequest;
import com.market.skin.controller.payload.request.SignupRequest;
import com.market.skin.controller.payload.response.JwtResponse;
import com.market.skin.controller.payload.response.MessageResponse;
import com.market.skin.exception.RecordNotFoundException;
import com.market.skin.model.ErrorCode;
import com.market.skin.model.Roles;
import com.market.skin.model.RolesName;
import com.market.skin.model.SuccessCode;
import com.market.skin.model.Users;
import com.market.skin.repository.UsersRepository;
import com.market.skin.repository.RolesRepository;
import com.market.skin.security.jwt.JwtUtils;
import com.market.skin.security.service.UserDetailsImp;
import com.market.skin.service.UsersService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    final private AuthenticationManager authenticationManager;
    final private UsersRepository userRepository;
    final private RolesRepository roleRepository;
    final private PasswordEncoder encoder;
    final private JwtUtils jwtUtils;
    @Autowired
    UsersService usersService;

    public AuthController (AuthenticationManager authenticationManager, UsersRepository userRepository, RolesRepository roleRepository, PasswordEncoder encoder, JwtUtils jwtUtils) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.encoder = encoder;
        this.jwtUtils = jwtUtils;
    }

    @PostMapping(value = "/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        UserDetailsImp userDetails = (UserDetailsImp) usersService.loadUserByUsername(loginRequest.getUserName());
        if (userDetails != null){
            if(encoder.matches(loginRequest.getPassword().strip(), userDetails.getPassword().strip())){
                String jwt= jwtUtils.generateJwtToken(userDetails);
                List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority()).collect(Collectors.toList());
                return ResponseEntity.ok(new JwtResponse(jwt, userDetails.getId(), userDetails.getUsername(), userDetails.getEmail(), roles));
            }
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(MessageResponse.builder().message("Unauthorized").error(ErrorCode.ERR_UNAUTHORIZED).build());
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
            .body(MessageResponse.builder().message("User name not found: " + loginRequest.getUserName()).error(ErrorCode.ERR_NO_RECORD_FOUND).build());
        
        // Authentication authentication = authenticationManager.authenticate(
        //     new UsernamePasswordAuthenticationToken(loginRequest.getUserName(), loginRequest.getPassword().strip()));
        // SecurityContextHolder.getContext().setAuthentication(authentication);
        // String jwt = jwtUtils.generateJwtToken(authentication);
        // UserDetailsImp userDetails = (UserDetailsImp) authentication.getPrincipal();
        // List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority()).collect(Collectors.toList());
        // return ResponseEntity.ok(new JwtResponse(jwt, userDetails.getId(), userDetails.getUsername(), userDetails.getEmail(), roles));
    }

    @PostMapping(value = "/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest){
        if (userRepository.existsByUserName(signUpRequest.getUserName())) {
            return ResponseEntity.badRequest().body(MessageResponse.builder().message("Error: Username is already taken!").error(ErrorCode.ERR_DUPLICATE).build());
        }
        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity
                .badRequest()
                .body(MessageResponse.builder().message("Error: Email is already in use!").error(ErrorCode.ERR_DUPLICATE).build());
        }

        Users user = new Users(signUpRequest.getUserName(), signUpRequest.getLoginType(), signUpRequest.getEmail(), signUpRequest.getProfile(), encoder.encode(signUpRequest.getPassword()));
        Set<String> strRoles = signUpRequest.getRoles();
        Set<Roles> roles = new HashSet<>();
        if (strRoles == null) {
            Roles userRole = roleRepository.findByName(RolesName.ROLE_USER)
                .orElseThrow(() -> new RecordNotFoundException("Error: Role is not found."));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role.toLowerCase()) {
                    case "admin":
                        Roles adminRole = roleRepository.findByName(RolesName.ROLE_ADMIN)
                            .orElseThrow(() -> new RecordNotFoundException("Error: Role is not found."));
                        roles.add(adminRole);
                        break;
                    case "cre":
                        Roles creRole = roleRepository.findByName(RolesName.ROLE_CREATOR)
                            .orElseThrow(() -> new RecordNotFoundException("Error: Role is not found."));
                        roles.add(creRole);
                        break;
                    default:
                        Roles userRole = roleRepository.findByName(RolesName.ROLE_USER)
                            .orElseThrow(() -> new RecordNotFoundException("Error: Role is not found."));
                        roles.add(userRole);
                }
            });
        }
        user.setRoles(roles);
        userRepository.save(user);

        return ResponseEntity.ok(MessageResponse.builder().success(SuccessCode.SUCCESS_CREATE).message("User create successfully.").build());
    }
}