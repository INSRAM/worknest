package com.insram.worknest.controller.auth;

import com.insram.worknest.dto.auth.AuthRequest;
import com.insram.worknest.dto.auth.AuthResponse;
import com.insram.worknest.dto.register.RegisterRequest;
import com.insram.worknest.model.role.Role;
import com.insram.worknest.model.user.User;
import com.insram.worknest.repository.role.RoleRepository;
import com.insram.worknest.repository.user.UserRepository;
import com.insram.worknest.security.jwt.JwtUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder encoder;
    private final AuthenticationManager authManager;
    private final JwtUtil jwtUtil;


    public AuthController(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder encoder, AuthenticationManager authManager, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.encoder = encoder;
        this.authManager = authManager;
        this.jwtUtil = jwtUtil;
    }


    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest req){

        if(userRepository.existsByUsername(req.getUsername())){
            return ResponseEntity.badRequest().body("Username already exists!");
        }

        // Setting User detail in entity
        User user = new User();
        user.setUsername(req.getUsername());
       user.setPassword(encoder.encode(req.getPassword()));

       //Assign default role to user
        Role role = roleRepository.findByName("ROLE_USER")
                .orElseGet(()-> roleRepository.save(new Role(null,"ROLE_USER")));

        user.setRoles(Set.of(role));

        // saving user
        userRepository.save(user);

        return ResponseEntity.ok().body(user.getUsername() + " user has been successfully registered.");

    }


    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest req) {

        System.out.println("This is user name ==> " + req.getUsername() + " this is pass : " + req.getPassword());
//        Authentication auth = authManager.authenticate(
//                new UsernamePasswordAuthenticationToken(req.getUsername(), req.getPassword())
//        );
//        String token = jwtUtil.generateToken(auth);
//        return ResponseEntity.ok(new AuthResponse(token));
//        Authentication auth = authManager.authenticate(
//                new UsernamePasswordAuthenticationToken(req.getUsername(), req.getPassword())
//        );
////        SecurityContextHolder.getContext().setAuthentication(auth);
//        System.out.println("Auth success: " + auth.isAuthenticated());
//        String token = jwtUtil.generateToken(auth);
//        return ResponseEntity.ok(new AuthResponse(token));
//

        try {
            Authentication auth = authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(req.getUsername(), req.getPassword())
            );
            System.out.println("Auth success: " + auth.isAuthenticated());
            SecurityContextHolder.getContext().setAuthentication(auth);
            String token = jwtUtil.generateToken(auth);
            return ResponseEntity.ok(new AuthResponse(token));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new AuthResponse("Login failed: " + e.getMessage()));
        }
    }
}
