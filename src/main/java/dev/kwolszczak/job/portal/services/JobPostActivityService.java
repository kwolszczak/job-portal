package dev.kwolszczak.job.portal.services;

import dev.kwolszczak.job.portal.entity.IRecruiterJobs;
import dev.kwolszczak.job.portal.entity.JobCompany;
import dev.kwolszczak.job.portal.entity.JobLocation;
import dev.kwolszczak.job.portal.entity.JobPostActivity;
import dev.kwolszczak.job.portal.entity.RecruiterJobsDto;
import dev.kwolszczak.job.portal.repository.JobPostActivityRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JobPostActivityService {

  private final JobPostActivityRepository jobPostActivityRepository;

  @Autowired
  public JobPostActivityService(JobPostActivityRepository jobPostActivityRepository) {
    this.jobPostActivityRepository = jobPostActivityRepository;
  }

  public JobPostActivity addNew(JobPostActivity jobPostActivity) {
    return jobPostActivityRepository.save(jobPostActivity);
  }

  public List<RecruiterJobsDto> getRecruiterJobs(int recruiterId) {
    List<IRecruiterJobs> recruiterJobs = jobPostActivityRepository.getRecruiterJobs(recruiterId);
    List<RecruiterJobsDto> recruiterJobsDtos = new ArrayList<>();

    for (IRecruiterJobs rec : recruiterJobs) {
      JobLocation loc = new JobLocation(rec.getLocationId(), rec.getCity(),rec.getState(),rec.getCountry());
      JobCompany comp = new JobCompany(rec.getCompanyId(), rec.getName(), "");
      recruiterJobsDtos.add(new RecruiterJobsDto(rec.getTotalCandidates(),rec.getJob_post_id(),rec.getJob_title(),loc, comp));
    }
    return recruiterJobsDtos;
  }

}
