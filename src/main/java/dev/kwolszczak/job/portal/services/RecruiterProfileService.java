package dev.kwolszczak.job.portal.services;

import dev.kwolszczak.job.portal.entity.RecruiterProfile;
import dev.kwolszczak.job.portal.entity.User;
import dev.kwolszczak.job.portal.repository.RecruiterProfileRepository;
import dev.kwolszczak.job.portal.repository.UserRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class RecruiterProfileService {

  private final RecruiterProfileRepository recruiterProfileRepository;
  private final UserRepository userRepository;

  @Autowired
  public RecruiterProfileService(RecruiterProfileRepository recruiterProfileRepository, UserRepository userRepository) {
    this.recruiterProfileRepository = recruiterProfileRepository;
    this.userRepository = userRepository;
  }

  public Optional<RecruiterProfile> getOne(Integer id) {
    return recruiterProfileRepository.findById(id);
  }

  public RecruiterProfile addNew(RecruiterProfile recruiterProfile) {
    return recruiterProfileRepository.save(recruiterProfile);
  }

  public RecruiterProfile getCurrentRecruiterProfile() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if(!(authentication instanceof AnonymousAuthenticationToken)) {
      String currentUserName = authentication.getName();
      User user = userRepository.findByEmail(currentUserName).orElseThrow(() -> new UsernameNotFoundException("User not found"));
      Optional<RecruiterProfile> recruiterProfile = getOne(user.getUserId());
      return recruiterProfile.orElse(null);
    }
    else return null;
  }
}

