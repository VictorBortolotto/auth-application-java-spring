package br.com.auth.webauthexample.controller;


import org.springframework.beans.factory.annotation.Autowired;
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

import br.com.auth.webauthexample.auth.service.UserDetailsImpl;
import br.com.auth.webauthexample.auth.jwt.JwtUtils;
import br.com.auth.webauthexample.model.UserModel;
import br.com.auth.webauthexample.payload.request.LoginRequest;
import br.com.auth.webauthexample.payload.request.SignupRequest;
import br.com.auth.webauthexample.payload.response.JwtResponse;
import br.com.auth.webauthexample.payload.response.MessageResponse;
import br.com.auth.webauthexample.repository.UserRepository;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    JwtUtils jwtUtils;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest){

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl)  authentication.getPrincipal();

        return ResponseEntity.ok(new JwtResponse(jwt, userDetails.getId(), userDetails.getEmail(), userDetails.getPassword()));

    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody SignupRequest signUpRequest){

        if(userRepository.existsByEmail(signUpRequest.getEmail())){
            return ResponseEntity.badRequest().body(new MessageResponse("Email already exists!", 400));
        }

        UserModel user = new UserModel(
                signUpRequest.getName(),
                signUpRequest.getEmail(),
                passwordEncoder.encode(signUpRequest.getPassword()));

        userRepository.save(user);

        return ResponseEntity.ok(new MessageResponse("Account created with success!", 200));

    }

}
