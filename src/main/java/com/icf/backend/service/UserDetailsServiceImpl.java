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

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@Service
@Transactional
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;
    private final LoginAttemptService loginAttemptService;
    private final HttpServletRequest request;

    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        final User user = this.userRepository.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException(username);
        }

        user.getRoles().size(); // init lazy association
        return new UserPrincipal(this.updateLoginInformationAndGet(user));
    }

    private User updateLoginInformationAndGet(User user) {
        user.setLastLoggedInAt(LocalDateTime.now());
        return this.userRepository.save(user);
    }

}
