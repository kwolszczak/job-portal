package dev.kwolszczak.job.portal.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "job_seeker_save")
public class JobSeekerSave implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;



  @ManyToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "job")
  private JobPostActivity job;

  @ManyToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "user_id")
  private JobSeekerProfile userProfile;

  public JobSeekerSave() {
  }

  public JobSeekerSave(Integer id, JobPostActivity job, JobSeekerProfile userProfile) {
    this.id = id;
    this.job = job;
    this.userProfile = userProfile;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public JobSeekerProfile getUserProfile() {
    return userProfile;
  }

  public void setUserProfile(JobSeekerProfile userProfile) {
    this.userProfile = userProfile;
  }

  public JobPostActivity getJob() {
    return job;
  }

  public void setJob(JobPostActivity job) {
    this.job = job;
  }
}
