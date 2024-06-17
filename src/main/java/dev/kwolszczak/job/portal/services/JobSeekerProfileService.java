package dev.kwolszczak.job.portal.services;

import dev.kwolszczak.job.portal.entity.JobSeekerProfile;
import dev.kwolszczak.job.portal.repository.JobSeekerProfileRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JobSeekerProfileService {

  private final JobSeekerProfileRepository jobSeekerProfileRepository;

  public JobSeekerProfileService(JobSeekerProfileRepository jobSeekerProfileRepository) {
    this.jobSeekerProfileRepository = jobSeekerProfileRepository;
  }


  public Optional<JobSeekerProfile> getOne(int id) {
    return jobSeekerProfileRepository.findById(id);
  }


  public JobSeekerProfile addNew(JobSeekerProfile jobSeekerProfile) {
    JobSeekerProfile seekerProfile = jobSeekerProfileRepository.save(jobSeekerProfile);
    return seekerProfile;
  }
}
