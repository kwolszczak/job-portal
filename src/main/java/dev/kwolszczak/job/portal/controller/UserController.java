package dev.kwolszczak.job.portal.controller;

import dev.kwolszczak.job.portal.entity.User;
import dev.kwolszczak.job.portal.entity.UserType;
import dev.kwolszczak.job.portal.services.UserService;
import dev.kwolszczak.job.portal.services.UserTypeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class UserController {

    @Autowired
    private UserTypeService userTypeService;
    @Autowired
    private UserService userService;

 /*   public UserController(UserTypeService userTypeService) {
        this.userTypeService = userTypeService;
    }*/

    @GetMapping("/register")
    public String register(Model model){
        List<UserType> userTypes = userTypeService.getAll();
        model.addAttribute("getAllTypes", userTypes);
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register/new")
    public String userRegistration(@Valid User user){
        System.out.println("User::"+user.getEmail());
        userService.addNew(user);
        return "dashboard";
    }
}
