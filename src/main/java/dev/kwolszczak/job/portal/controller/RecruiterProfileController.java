package dev.kwolszczak.job.portal.controller;

import dev.kwolszczak.job.portal.entity.RecruiterProfile;
import dev.kwolszczak.job.portal.entity.User;
import dev.kwolszczak.job.portal.repository.UserRepository;
import dev.kwolszczak.job.portal.services.RecruiterProfileService;
import dev.kwolszczak.job.portal.util.FileUploadUtil;
import java.io.IOException;
import java.util.Objects;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/recruiter-profile")
public class RecruiterProfileController {

  private final UserRepository userRepository;
  private final RecruiterProfileService recruiterProfileService;

  @Autowired
  public RecruiterProfileController(UserRepository userRepository, RecruiterProfileService recruiterProfileService) {
    this.userRepository = userRepository;
    this.recruiterProfileService = recruiterProfileService;
  }

  @GetMapping("/")
  public String recruiterProfile(Model model) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    if (!(authentication instanceof AnonymousAuthenticationToken)) {
      String currentUsername = authentication.getName();
      User user = userRepository.findByEmail(currentUsername).orElseThrow(() -> new RuntimeException("User not found " + currentUsername));

      Optional<RecruiterProfile> recruiterProfile = recruiterProfileService.getOne(user.getUserId());

      recruiterProfile.ifPresent(profile -> model.addAttribute("profile", profile));
    }
    return "recruiter_profile";
  }

  @PostMapping("/addNew")
  public String addNew(Model model, RecruiterProfile recruiterProfile, @RequestParam("image") MultipartFile multipartFile) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    if (!(authentication instanceof AnonymousAuthenticationToken)) {
      String currentUsername = authentication.getName();
      User user = userRepository.findByEmail(currentUsername).orElseThrow(() -> new RuntimeException("User not found " + currentUsername));

      recruiterProfile.setUser(user);
      recruiterProfile.setUserAccountId(user.getUserId());

    }
    model.addAttribute("profile", recruiterProfile);
    String fileName = "";
    if (!multipartFile.getOriginalFilename().equals("")) {
      fileName = StringUtils.cleanPath(Objects.requireNonNull(multipartFile.getOriginalFilename()));
      recruiterProfile.setProfilePhoto(fileName);
    }
    RecruiterProfile savedUser = recruiterProfileService.addNew(recruiterProfile);
    String uploadDir = "photos/recruiter/" + savedUser.getUserAccountId();

    try {
      FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    return "redirect:/dashboard/";
  }

}
