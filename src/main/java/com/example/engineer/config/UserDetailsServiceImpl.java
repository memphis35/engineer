package com.example.engineer.config;

import com.example.engineer.repository.UserJpaRepository;
import com.example.engineer.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserJpaRepository userJpaRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (userJpaRepository.existsByEmail(username)) {
            log.info("User with email [{}] has been found", username);
            return new SecurityUser(userJpaRepository.findUserByEmail(username));
        } else {
            throw new UsernameNotFoundException("User with " + username + " doesn't exist");
        }
    }
}
