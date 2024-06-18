package dev.kwolszczak.job.portal.repository;

import dev.kwolszczak.job.portal.entity.JobPostActivity;
import dev.kwolszczak.job.portal.entity.JobSeekerApply;
import dev.kwolszczak.job.portal.entity.JobSeekerProfile;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobSeekerApplyRepository extends JpaRepository<JobSeekerApply,Integer> {

  List<JobSeekerApply> findByUserProfile(JobSeekerProfile userProfile);

  List<JobSeekerApply> findByJob(JobPostActivity job);

}
