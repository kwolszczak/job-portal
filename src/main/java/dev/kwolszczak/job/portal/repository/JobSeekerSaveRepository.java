package dev.kwolszczak.job.portal.repository;

import dev.kwolszczak.job.portal.entity.JobPostActivity;
import dev.kwolszczak.job.portal.entity.JobSeekerProfile;
import dev.kwolszczak.job.portal.entity.JobSeekerSave;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobSeekerSaveRepository extends JpaRepository<JobSeekerSave, Integer> {

  public List<JobSeekerSave> findByUserProfile(JobSeekerProfile userAccountId);

  List<JobSeekerSave> findByJob(JobPostActivity jobPostActivity);
}
