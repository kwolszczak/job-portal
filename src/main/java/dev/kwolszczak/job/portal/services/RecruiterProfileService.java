package dev.kwolszczak.job.portal.services;

import dev.kwolszczak.job.portal.entity.RecruiterProfile;
import dev.kwolszczak.job.portal.repository.RecruiterProfileRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RecruiterProfileService {

  private final RecruiterProfileRepository recruiterProfileRepository;

  @Autowired
  public RecruiterProfileService(RecruiterProfileRepository recruiterProfileRepository) {
    this.recruiterProfileRepository = recruiterProfileRepository;
  }

  public Optional<RecruiterProfile> getOne(Integer id) {
    return recruiterProfileRepository.findById(id);
  }

  public RecruiterProfile addNew(RecruiterProfile recruiterProfile) {
    return recruiterProfileRepository.save(recruiterProfile);
  }
}
