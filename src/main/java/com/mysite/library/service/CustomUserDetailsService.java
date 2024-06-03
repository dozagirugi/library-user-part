package com.mysite.library.service;

import com.mysite.library.entity.UserEntity;
import com.mysite.library.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        UserEntity user = userRepository.findByEmail(email).orElseThrow(() ->
                new UsernameNotFoundException(email + "해당 이메일의 유저가 없습니다 "));


        return new User(
                user.getEmail(),
                user.getPassword(),
                new ArrayList<>() //권한
        );
    }
}
