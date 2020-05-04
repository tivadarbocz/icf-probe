package com.icf.backend.service;

import com.icf.backend.model.User;
import com.icf.backend.model.UserPrincipal;
import com.icf.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Transactional
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        final User user = this.userRepository.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException(username);
        }

        // init lazy association
        user.getRoles().size();

        return new UserPrincipal(this.updateLoginInformationAndGet(user));
    }

    private User updateLoginInformationAndGet(User user) {
        user.setLastLoggedInAt(LocalDateTime.now());
        return this.userRepository.save(user);
    }

}
