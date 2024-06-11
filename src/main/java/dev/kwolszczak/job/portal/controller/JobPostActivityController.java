package dev.kwolszczak.job.portal.controller;

import dev.kwolszczak.job.portal.entity.JobPostActivity;
import dev.kwolszczak.job.portal.entity.RecruiterJobsDto;
import dev.kwolszczak.job.portal.entity.RecruiterProfile;
import dev.kwolszczak.job.portal.entity.User;
import dev.kwolszczak.job.portal.services.JobPostActivityService;
import dev.kwolszczak.job.portal.services.UserService;
import java.util.List;
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

import java.util.Date;

@Controller
public class JobPostActivityController {

  private final UserService userService;
  private final JobPostActivityService jobPostActivityService;

  @Autowired
  public JobPostActivityController(UserService userService, JobPostActivityService jobPostActivityService) {
    this.userService = userService;
    this.jobPostActivityService = jobPostActivityService;
  }

  @GetMapping("/dashboard/")
  public String searchJobs(Model model) {
    Object currentUserProfile = userService.getCurrentUserProfile();
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    if (!(authentication instanceof AnonymousAuthenticationToken)) {
      String currentUsername = authentication.getName();
      model.addAttribute("username", currentUsername);
      if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("Recruiter"))) {
        List<RecruiterJobsDto> recruiterJobs = jobPostActivityService.getRecruiterJobs(((RecruiterProfile) currentUserProfile).getUserAccountId());
        model.addAttribute("jobPost", recruiterJobs);
      }
    }
    model.addAttribute("user", currentUserProfile);
    return "dashboard";
  }

  @GetMapping("/dashboard/add")
  public String addJobs(Model model){
    model.addAttribute("jobPostActivity", new JobPostActivity());
    model.addAttribute("user",userService.getCurrentUserProfile());
    return "add-jobs";
  }

  @PostMapping("/dashboard/addNew")
  public String addNew(JobPostActivity jobPostActivity, Model model) {
    User user = userService.getCurrentUser();
    if (user != null) {
      jobPostActivity.setUser(user);
    }
    jobPostActivity.setPostedDate(new Date());
    model.addAttribute("jobPostActivity", jobPostActivity);
    JobPostActivity saved = jobPostActivityService.addNew(jobPostActivity);

    return "redirect:/dashboard/";
  }

  @PostMapping("dashboard/edit/{id}")
  public String editJob(@PathVariable("id") int id, Model model ){
    JobPostActivity jobPostActivity = jobPostActivityService.getOne(id);
    model.addAttribute("jobPostActivity", jobPostActivity);
    model.addAttribute("user", userService.getCurrentUserProfile());
    return "add-jobs";
  }


}
