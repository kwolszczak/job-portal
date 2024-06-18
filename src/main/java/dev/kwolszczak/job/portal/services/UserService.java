package dev.kwolszczak.job.portal.services;

import dev.kwolszczak.job.portal.entity.JobSeekerProfile;
import dev.kwolszczak.job.portal.entity.RecruiterProfile;
import dev.kwolszczak.job.portal.entity.User;
import dev.kwolszczak.job.portal.repository.JobSeekerProfileRepository;
import dev.kwolszczak.job.portal.repository.RecruiterProfileRepository;
import dev.kwolszczak.job.portal.repository.UserRepository;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  @Autowired
  private final UserRepository userRepository;
  @Autowired
  private JobSeekerProfileRepository jobSeekerProfileRepository;
  @Autowired
  private RecruiterProfileRepository recruiterProfileRepository;
  @Autowired
  private PasswordEncoder passwordEncoder;


  public UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public User addNew(User user) {
    user.setActive(true);
    user.setRegistrationDate(new Date(System.currentTimeMillis()));
    user.setPassword(passwordEncoder.encode(user.getPassword()));

    User savedUser = userRepository.save(user);
    int userTypeId = user.getUserType().getUserTypeId();
    if (userTypeId == 1) {
      recruiterProfileRepository.save(new RecruiterProfile(savedUser));
    } else {
      jobSeekerProfileRepository.save(new JobSeekerProfile(savedUser));
    }
    return savedUser;
  }

  public Object getCurrentUserProfile() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (!(authentication instanceof AnonymousAuthenticationToken)) {
      String username = authentication.getName();
      User user = userRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("Could not found " + username));
      int userId = user.getUserId();
      if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("Recruiter"))) {
        RecruiterProfile recruiterProfile = recruiterProfileRepository.findById(userId).orElse(new RecruiterProfile());
        return recruiterProfile;
      } else {
        JobSeekerProfile jobSeekerProfile = jobSeekerProfileRepository.findById(userId).orElse(new JobSeekerProfile());
        return jobSeekerProfile;
      }
    }
    return null;
  }

  public User getCurrentUser() {
   Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
    if (!(authentication instanceof AnonymousAuthenticationToken)) {
      String username = authentication.getName();
      User user = userRepository.findByEmail(username).orElseThrow(()-> new UsernameNotFoundException("Could not found user"));
      return user;
    }
    return null;
  }

  public User findByEmail(String currentUsername) {
    return userRepository.findByEmail(currentUsername).orElseThrow(()-> new UsernameNotFoundException("Could not found " + currentUsername));
  }
}
