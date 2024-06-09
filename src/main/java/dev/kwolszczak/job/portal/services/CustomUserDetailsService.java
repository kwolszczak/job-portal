package dev.kwolszczak.job.portal.services;

import dev.kwolszczak.job.portal.entity.User;
import dev.kwolszczak.job.portal.repository.UserRepository;
import dev.kwolszczak.job.portal.util.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user=userRepository.findByEmail(username).orElseThrow(()-> new UsernameNotFoundException("Could not found user"));
        return new CustomUserDetails(user);
    }
}
