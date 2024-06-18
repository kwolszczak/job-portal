package dev.kwolszczak.job.portal.services;

import dev.kwolszczak.job.portal.entity.JobPostActivity;
import dev.kwolszczak.job.portal.entity.JobSeekerProfile;
import dev.kwolszczak.job.portal.entity.JobSeekerSave;
import dev.kwolszczak.job.portal.repository.JobSeekerSaveRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JobSeekerSaveService {

  private final JobSeekerSaveRepository jobSeekerSaveRepository;

  @Autowired
  public JobSeekerSaveService(JobSeekerSaveRepository jobSeekerSaveRepository) {
    this.jobSeekerSaveRepository = jobSeekerSaveRepository;
  }

  public List<JobSeekerSave> getCandidatesJob(JobSeekerProfile userProfile) {
    return jobSeekerSaveRepository.findByUserProfile(userProfile);
  }

  public List<JobSeekerSave> getJobCandidates(JobPostActivity job) {
    return jobSeekerSaveRepository.findByJob(job);

  }

  public void addNew(JobSeekerSave jobSeekerSave) {
    jobSeekerSaveRepository.save(jobSeekerSave);
  }
}
