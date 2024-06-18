package dev.kwolszczak.job.portal.services;

import dev.kwolszczak.job.portal.entity.JobSeekerProfile;
import dev.kwolszczak.job.portal.entity.User;
import dev.kwolszczak.job.portal.repository.JobSeekerProfileRepository;
import dev.kwolszczak.job.portal.repository.UserRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class JobSeekerProfileService {

  private final JobSeekerProfileRepository jobSeekerProfileRepository;
  private final UserRepository userRepository;

  public JobSeekerProfileService(JobSeekerProfileRepository jobSeekerProfileRepository, UserRepository userRepository) {
    this.jobSeekerProfileRepository = jobSeekerProfileRepository;
    this.userRepository = userRepository;
  }


  public Optional<JobSeekerProfile> getOne(int id) {
    return jobSeekerProfileRepository.findById(id);
  }


  public JobSeekerProfile addNew(JobSeekerProfile jobSeekerProfile) {
    JobSeekerProfile seekerProfile = jobSeekerProfileRepository.save(jobSeekerProfile);
    return seekerProfile;
  }

  public JobSeekerProfile getCurrentSeekerProfile() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if(!(authentication instanceof AnonymousAuthenticationToken)) {
      String currentUserName = authentication.getName();
      User user= userRepository.findByEmail(currentUserName).orElseThrow(() -> new UsernameNotFoundException("User not found"));

      Optional<JobSeekerProfile> seekerProfile = getOne(user.getUserId());
      return seekerProfile.orElse(null);
     } else return null;
  }
}
