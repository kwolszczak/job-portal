package dev.kwolszczak.job.portal.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "job_seeker_apply")
public class JobSeekerApply implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @DateTimeFormat(pattern = "dd-MM-yyyy")
  @Column(name = "apply_date")
  private Date applyDate;

  @Column(name = "cover_letter")
  private String coverLetter;


  @ManyToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "user_id")
  private JobSeekerProfile userProfile;


  @ManyToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "job")
  private JobPostActivity job;

  public JobSeekerApply() {
  }

  public JobSeekerApply(Integer id, Date applyDate, String coverLetter, JobSeekerProfile userProfile, JobPostActivity job) {
    this.id = id;
    this.applyDate = applyDate;
    this.coverLetter = coverLetter;
    this.userProfile = userProfile;
    this.job = job;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Date getApplyDate() {
    return applyDate;
  }

  public void setApplyDate(Date applyDate) {
    this.applyDate = applyDate;
  }

  public String getCoverLetter() {
    return coverLetter;
  }

  public void setCoverLetter(String coverLetter) {
    this.coverLetter = coverLetter;
  }

  public JobPostActivity getJob() {
    return job;
  }

  public void setJob(JobPostActivity job) {
    this.job = job;
  }

  public JobSeekerProfile getUserProfile() {
    return userProfile;
  }

  public void setUserProfile(JobSeekerProfile userProfile) {
    this.userProfile = userProfile;
  }
}
