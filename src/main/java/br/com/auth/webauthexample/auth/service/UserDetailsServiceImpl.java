package br.com.auth.webauthexample.auth.service;

import br.com.auth.webauthexample.model.UserModel;
import br.com.auth.webauthexample.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserModel user = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found with this email: " + email));
        return UserDetailsImpl.build(user);
    }

}