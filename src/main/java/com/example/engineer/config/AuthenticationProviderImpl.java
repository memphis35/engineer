package com.example.engineer.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationProviderImpl implements AuthenticationProvider {

    private final UserDetailsService detailsService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthenticationProviderImpl(@Qualifier("userDetailsServiceImpl") UserDetailsService detailsService,
                                      PasswordEncoder passwordEncoder) {
        this.detailsService = detailsService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        final String username = authentication.getName();
        final UserDetails secUser = detailsService.loadUserByUsername(username);
        final String password = (String) authentication.getCredentials();
        final boolean passwordIsMatched = passwordEncoder.matches(password, secUser.getPassword());
        if (passwordIsMatched && secUser.isEnabled() && secUser.isAccountNonExpired() && secUser.isAccountNonLocked()) {
            return new UsernamePasswordAuthenticationToken(
                    secUser.getUsername(), secUser.getPassword(), secUser.getAuthorities());
        } else if (!secUser.isEnabled()) {
            throw new DisabledException("Пользователь не активирован. Обратитесь к администратору");
        } else if (!secUser.isAccountNonExpired()) {
            throw new AccountExpiredException("Время действия вашего аккаунт истекло. Обратитесь к администратору.");
        } else if (!secUser.isAccountNonLocked()) {
            throw new LockedException("Ваш аккаунт заблокирован. Обратитесь к администратору.");
        } else {
            throw new BadCredentialsException("Username or password is incorrect");
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
