package dev.kwolszczak.job.portal.entity;

import jakarta.persistence.*;

@Entity
@Table(name="skills")
public class Skill {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @ManyToOne
    @JoinColumn(name = "job_seeker_profile")
    private JobSeekerProfile jobSeekerProfile;

    private String name;
    private String experience;
    private String yearsOfExperience;
    private String experienceLevel;

}
