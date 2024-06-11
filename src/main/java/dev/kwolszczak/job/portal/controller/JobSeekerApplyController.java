package dev.kwolszczak.job.portal.controller;

import dev.kwolszczak.job.portal.entity.JobPostActivity;
import dev.kwolszczak.job.portal.services.JobPostActivityService;
import dev.kwolszczak.job.portal.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class JobSeekerApplyController {

  private final JobPostActivityService jobPostActivityService;
  private final UserService userService;

  @Autowired
  public JobSeekerApplyController(JobPostActivityService jobPostActivityService, UserService userService) {
    this.jobPostActivityService = jobPostActivityService;
    this.userService = userService;
  }

  @GetMapping("/job-details-apply/{id}")
  public String display(@PathVariable("id") int id, Model model) {
    JobPostActivity jobDetails = jobPostActivityService.getOne(id);

    model.addAttribute("jobDetails", jobDetails);
    model.addAttribute("user", userService.getCurrentUserProfile());
    return "job-details";
  }


}
