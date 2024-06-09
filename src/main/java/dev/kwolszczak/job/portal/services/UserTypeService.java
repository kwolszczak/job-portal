package dev.kwolszczak.job.portal.services;

import dev.kwolszczak.job.portal.entity.UserType;
import dev.kwolszczak.job.portal.repository.UserTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserTypeService {

    @Autowired
    private final UserTypeRepository userTypeRepository;

    public UserTypeService(UserTypeRepository userTypeRepository) {
        this.userTypeRepository = userTypeRepository;
    }

    public List<UserType> getAll(){
        return userTypeRepository.findAll();
    }
}
