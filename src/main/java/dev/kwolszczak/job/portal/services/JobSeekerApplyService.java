package dev.kwolszczak.job.portal.services;

import dev.kwolszczak.job.portal.entity.JobPostActivity;
import dev.kwolszczak.job.portal.entity.JobSeekerApply;
import dev.kwolszczak.job.portal.entity.JobSeekerProfile;
import dev.kwolszczak.job.portal.repository.JobSeekerApplyRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JobSeekerApplyService {

  private final JobSeekerApplyRepository jobSeekerApplyRepository;

  @Autowired
  public JobSeekerApplyService(JobSeekerApplyRepository jobSeekerApplyRepository) {
    this.jobSeekerApplyRepository = jobSeekerApplyRepository;
  }

  public List<JobSeekerApply> getCandidatesJobs(JobSeekerProfile userAccountId) {
    return jobSeekerApplyRepository.findByUserProfile(userAccountId);
  }

  public List<JobSeekerApply> getJobCandidates(JobPostActivity job) {
    return jobSeekerApplyRepository.findByJob(job);
  }

  public void addNew(JobSeekerApply jobSeekerApply) {
    jobSeekerApplyRepository.save(jobSeekerApply);
  }
}
