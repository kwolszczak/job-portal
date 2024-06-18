package dev.kwolszczak.job.portal.controller;

import dev.kwolszczak.job.portal.entity.JobPostActivity;
import dev.kwolszczak.job.portal.entity.JobSeekerApply;
import dev.kwolszczak.job.portal.entity.JobSeekerProfile;
import dev.kwolszczak.job.portal.entity.JobSeekerSave;
import dev.kwolszczak.job.portal.entity.RecruiterProfile;
import dev.kwolszczak.job.portal.entity.User;
import dev.kwolszczak.job.portal.services.JobPostActivityService;
import dev.kwolszczak.job.portal.services.JobSeekerApplyService;
import dev.kwolszczak.job.portal.services.JobSeekerProfileService;
import dev.kwolszczak.job.portal.services.JobSeekerSaveService;
import dev.kwolszczak.job.portal.services.RecruiterProfileService;
import dev.kwolszczak.job.portal.services.UserService;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
@Controller
public class JobSeekerApplyController {

  private final JobPostActivityService jobPostActivityService;
  private final UserService userService;
  private final JobSeekerApplyService jobSeekerApplyService;
  private final JobSeekerSaveService jobSeekerSaveService;
  private final RecruiterProfileService recruiterProfileService;
  private final JobSeekerProfileService jobSeekerProfileService;


  @Autowired
  public JobSeekerApplyController(JobPostActivityService jobPostActivityService, UserService userService, JobSeekerApplyService jobSeekerApplyService, JobSeekerSaveService jobSeekerSaveService, RecruiterProfileService recruiterProfileService, JobSeekerProfileService jobSeekerProfileService) {
    this.jobPostActivityService = jobPostActivityService;
    this.userService = userService;
    this.jobSeekerApplyService = jobSeekerApplyService;
    this.jobSeekerSaveService = jobSeekerSaveService;
    this.recruiterProfileService = recruiterProfileService;
    this.jobSeekerProfileService = jobSeekerProfileService;
  }

  @GetMapping("job-details-apply/{id}")
  public String display(@PathVariable("id") int id, Model model) {
    JobPostActivity jobDetails = jobPostActivityService.getOne(id);
    List<JobSeekerApply> jobSeekerApplyList = jobSeekerApplyService.getJobCandidates(jobDetails);
    List<JobSeekerSave> jobSeekerSaveList = jobSeekerSaveService.getJobCandidates(jobDetails);
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (!(authentication instanceof AnonymousAuthenticationToken)) {
      if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("Recruiter"))) {
        RecruiterProfile user = recruiterProfileService.getCurrentRecruiterProfile();
        if (user != null) {
          model.addAttribute("applyList", jobSeekerApplyList);
        }
      } else {
        JobSeekerProfile user = jobSeekerProfileService.getCurrentSeekerProfile();
        if (user != null) {
          boolean exists = false;
          boolean saved = false;
          for (JobSeekerApply jobSeekerApply : jobSeekerApplyList) {
            if (jobSeekerApply.getUserProfile().getUserAccountId() == user.getUserAccountId()) {
              exists = true;
              break;
            }
          }
          for (JobSeekerSave jobSeekerSave : jobSeekerSaveList) {
            if (jobSeekerSave.getUserProfile().getUserAccountId() == user.getUserAccountId()) {
              saved = true;
              break;
            }
          }
          model.addAttribute("alreadyApplied", exists);
          model.addAttribute("alreadySaved", saved);
        }
      }
    }

    JobSeekerApply jobSeekerApply = new JobSeekerApply();
    model.addAttribute("applyJob", jobSeekerApply);

    model.addAttribute("jobDetails", jobDetails);
    model.addAttribute("user", userService.getCurrentUserProfile());
    return "job-details";
  }

  @PostMapping("job-details/apply/{id}")
  public String apply(@PathVariable("id") int id, JobSeekerApply jobSeekerApply) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (!(authentication instanceof AnonymousAuthenticationToken)) {
      String currentUsername = authentication.getName();
      User user = userService.findByEmail(currentUsername);
      Optional<JobSeekerProfile> seekerProfile = jobSeekerProfileService.getOne(user.getUserId());
      JobPostActivity jobPostActivity = jobPostActivityService.getOne(id);
      if (seekerProfile.isPresent() && jobPostActivity != null) {
        jobSeekerApply.setUserProfile(seekerProfile.get());
        jobSeekerApply.setJob(jobPostActivity);
        jobSeekerApply.setApplyDate(new Date());
      } else {
        throw new RuntimeException("User not found");
      }
      jobSeekerApplyService.addNew(jobSeekerApply);
    }

    return "redirect:/dashboard/";
  }
}