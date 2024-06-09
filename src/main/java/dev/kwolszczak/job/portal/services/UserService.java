package dev.kwolszczak.job.portal.services;

import dev.kwolszczak.job.portal.entity.JobSeekerProfile;
import dev.kwolszczak.job.portal.entity.RecruiterProfile;
import dev.kwolszczak.job.portal.entity.User;
import dev.kwolszczak.job.portal.repository.JobSeekerProfileRepository;
import dev.kwolszczak.job.portal.repository.RecruiterProfileRepository;
import dev.kwolszczak.job.portal.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class UserService {

    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private JobSeekerProfileRepository jobSeekerProfileRepository;
    @Autowired
    private RecruiterProfileRepository recruiterProfileRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;


    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User addNew(User user){
        user.setActive(true);
        user.setRegistrationDate(new Date(System.currentTimeMillis()));
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        User savedUser = userRepository.save(user);
        int userTypeId= user.getUserType().getUserTypeId();
        if(userTypeId == 1){
            recruiterProfileRepository.save(new RecruiterProfile(savedUser));
        }else {
            jobSeekerProfileRepository.save(new JobSeekerProfile(savedUser));
        }
        return savedUser;
    }
}
