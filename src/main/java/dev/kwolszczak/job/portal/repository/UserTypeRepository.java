package dev.kwolszczak.job.portal.repository;

import dev.kwolszczak.job.portal.entity.UserType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserTypeRepository extends JpaRepository<UserType,Integer> {
}
