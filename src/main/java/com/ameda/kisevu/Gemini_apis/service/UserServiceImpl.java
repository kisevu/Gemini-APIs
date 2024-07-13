package com.ameda.kisevu.Gemini_apis.service;/*
*
@author ameda
@project Gemini-apis
*
*/

import com.ameda.kisevu.Gemini_apis.repositories.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetailsService userDetailsService() {
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username){
                return  userRepository.findByEmail(username)
                        .orElseThrow(()-> new UsernameNotFoundException("user could not be found."));
            }
        };
    }
}
