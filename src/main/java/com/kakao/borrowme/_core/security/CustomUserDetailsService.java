package com.kakao.borrowme._core.security;

import com.kakao.borrowme.user.User;
import com.kakao.borrowme.user.UserJPARepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UserJPARepository userJPARepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        System.out.println("email = " + email);
        Optional<User> userOP = userJPARepository.findByEmail(email);

        if (userOP.isPresent()) {
            return new CustomUserDetails(userOP.get());
        } else {
            return null;
        }
    }
}
