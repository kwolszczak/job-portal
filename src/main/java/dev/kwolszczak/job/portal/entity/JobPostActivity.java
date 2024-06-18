package dev.kwolszczak.job.portal.entity;

import jakarta.persistence.*;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Entity
@Table(name = "job_post_activity")
public class JobPostActivity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer jobPostId;

    @Transient
    private Boolean isActive;

    @Transient
    private Boolean isSaved;

    @Length(max = 10000)
    private String descriptionOfJob;
    @DateTimeFormat(pattern = "dd-mm-yyyy")
    private Date postedDate;

    private String jobTitle;
    private String jobType;
    private String remote;
    private String salary;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "job_company_id")
    private JobCompany jobCompany;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "job_location_id")
    private JobLocation jobLocation;

    @ManyToOne
    @JoinColumn(name = "posted_by_id")
    private User user;

    public JobPostActivity() {
    }

    public JobPostActivity(Integer jobPostId, Boolean isActive, Boolean isSaved, String descriptionOfJob, Date postedDate, String jobTitle, String jobType, String remote, String salary, JobCompany jobCompany, JobLocation jobLocation, User user) {
        this.jobPostId = jobPostId;
        this.isActive = isActive;
        this.isSaved = isSaved;
        this.descriptionOfJob = descriptionOfJob;
        this.postedDate = postedDate;
        this.jobTitle = jobTitle;
        this.jobType = jobType;
        this.remote = remote;
        this.salary = salary;
        this.jobCompany = jobCompany;
        this.jobLocation = jobLocation;
        this.user = user;
    }

    public Integer getJobPostId() {
        return jobPostId;
    }

    public void setJobPostId(Integer jobPostId) {
        this.jobPostId = jobPostId;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean active) {
        isActive = active;
    }

    public Boolean getIsSaved() {
        return isSaved;
    }

    public void setIsSaved(Boolean saved) {
        isSaved = saved;
    }

    public @Length(max = 10000) String getDescriptionOfJob() {
        return descriptionOfJob;
    }

    public void setDescriptionOfJob(@Length(max = 10000) String descriptionOfJob) {
        this.descriptionOfJob = descriptionOfJob;
    }

    public Date getPostedDate() {
        return postedDate;
    }

    public void setPostedDate(Date postedDate) {
        this.postedDate = postedDate;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getJobType() {
        return jobType;
    }

    public void setJobType(String jobType) {
        this.jobType = jobType;
    }

    public String getRemote() {
        return remote;
    }

    public void setRemote(String remote) {
        this.remote = remote;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public JobCompany getJobCompany() {
        return jobCompany;
    }

    public void setJobCompany(JobCompany jobCompany) {
        this.jobCompany = jobCompany;
    }

    public JobLocation getJobLocation() {
        return jobLocation;
    }

    public void setJobLocation(JobLocation jobLocation) {
        this.jobLocation = jobLocation;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
