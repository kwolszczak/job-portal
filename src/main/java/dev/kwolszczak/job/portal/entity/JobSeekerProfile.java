package dev.kwolszczak.job.portal.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "job_seeker_profile")
public class JobSeekerProfile {

    @Id
    private int userAccountId;

    @OneToOne
    @JoinColumn(name = "user_account_id")
    @MapsId
    private  User user;

    @OneToMany(mappedBy = "jobSeekerProfile")
    List<Skill> skills;

    private String city;
    private String state;
    private String country;
    private String firstName;
    private String lastName;
    private String workAuthorization;
    private String employmentType;
    private String resume;
    private String profilePhoto;

    public JobSeekerProfile() {
    }

    public JobSeekerProfile(int userAccountId, User user, List<Skill> skills, String city, String state, String country, String firstName, String lastName, String workAuthorization, String employmentType, String resume, String profilePhoto) {
        this.userAccountId = userAccountId;
        this.user = user;
        this.skills = skills;
        this.city = city;
        this.state = state;
        this.country = country;
        this.firstName = firstName;
        this.lastName = lastName;
        this.workAuthorization = workAuthorization;
        this.employmentType = employmentType;
        this.resume = resume;
        this.profilePhoto = profilePhoto;
    }

    public JobSeekerProfile(User user) {
        this.user = user;
    }

    public int getUserAccountId() {
        return userAccountId;
    }

    public void setUserAccountId(int userAccountId) {
        this.userAccountId = userAccountId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Skill> getSkills() {
        return skills;
    }

    public void setSkills(List<Skill> skills) {
        this.skills = skills;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getWorkAuthorization() {
        return workAuthorization;
    }

    public void setWorkAuthorization(String workAuthorization) {
        this.workAuthorization = workAuthorization;
    }

    public String getEmploymentType() {
        return employmentType;
    }

    public void setEmploymentType(String employmentType) {
        this.employmentType = employmentType;
    }

    public String getResume() {
        return resume;
    }

    public void setResume(String resume) {
        this.resume = resume;
    }

    public String getProfilePhoto() {
        return profilePhoto;
    }

    public void setProfilePhoto(String profilePhoto) {
        this.profilePhoto = profilePhoto;
    }
}
