package dev.kwolszczak.job.portal.controller;

import dev.kwolszczak.job.portal.entity.JobSeekerProfile;
import dev.kwolszczak.job.portal.entity.Skill;
import dev.kwolszczak.job.portal.entity.User;
import dev.kwolszczak.job.portal.repository.UserRepository;
import dev.kwolszczak.job.portal.services.JobSeekerProfileService;
import dev.kwolszczak.job.portal.util.FileUploadUtil;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/job-seeker-profile")
public class JobSeekerProfileController {

  private JobSeekerProfileService jobSeekerProfileService;
  private UserRepository userRepository;

  @Autowired
  public JobSeekerProfileController(JobSeekerProfileService jobSeekerProfileService, UserRepository userRepository) {
    this.jobSeekerProfileService = jobSeekerProfileService;
    this.userRepository = userRepository;
  }

  @GetMapping("/")
  public String JobSeekerProfile(Model model) {
    JobSeekerProfile jobSeekerProfile = new JobSeekerProfile();

    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    List<Skill> skills = new ArrayList<>();

    if (!(authentication instanceof AnonymousAuthenticationToken)) {
      User user = userRepository.findByEmail(authentication.getName()).orElseThrow(() -> new UsernameNotFoundException("User not found"));
      Optional<JobSeekerProfile> seekerProfile = jobSeekerProfileService.getOne(user.getUserId());
      if (seekerProfile.isPresent()) {
        jobSeekerProfile = seekerProfile.get();
        if (jobSeekerProfile.getSkills().isEmpty()) {
          skills.add(new Skill());
          jobSeekerProfile.setSkills(skills);
        }
      }
      model.addAttribute("skills", skills);
      model.addAttribute("profile", jobSeekerProfile);
    }
    return "job-seeker-profile";
  }

  @PostMapping("/addNew")
  public String addNew(JobSeekerProfile jobSeekerProfile,
      Model model,
      @RequestParam("image") MultipartFile image,
      @RequestParam("pdf") MultipartFile pdf) {

    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (!(authentication instanceof AnonymousAuthenticationToken)) {
      User user = userRepository.findByEmail(authentication.getName()).orElseThrow(() -> new UsernameNotFoundException("User not found"));
      jobSeekerProfile.setUser(user);
      jobSeekerProfile.setUserAccountId(user.getUserId());

    }
    List<Skill> skills = new ArrayList<>();
    model.addAttribute("profile", jobSeekerProfile);
    model.addAttribute("skills", skills);

    for (Skill skill : jobSeekerProfile.getSkills()) {
      skill.setJobSeekerProfile(jobSeekerProfile);
    }

    String imageName = "";
    String resumeName = "";

    if (!Objects.equals(image.getOriginalFilename(), "")) {
      imageName = StringUtils.cleanPath(Objects.requireNonNull(image.getOriginalFilename()));
      jobSeekerProfile.setProfilePhoto(imageName);
    }
    if (!Objects.equals(pdf.getOriginalFilename(), "")) {
      resumeName = StringUtils.cleanPath(Objects.requireNonNull(pdf.getOriginalFilename()));
      jobSeekerProfile.setResume(resumeName);
    }
    JobSeekerProfile seekerProfile = jobSeekerProfileService.addNew(jobSeekerProfile);

    try {
      String uploadDir = "photos/candidate/" + jobSeekerProfile.getUserAccountId();
      if (!Objects.equals(image.getOriginalFilename(), "")) {
        FileUploadUtil.saveFile(uploadDir,imageName,image);
      }
      if (!Objects.equals(pdf.getOriginalFilename(), "")) {
        FileUploadUtil.saveFile(uploadDir,resumeName,pdf);
      }
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    return "redirect:/dashboard/";

  }
}
