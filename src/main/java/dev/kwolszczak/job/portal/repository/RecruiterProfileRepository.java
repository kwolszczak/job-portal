package dev.kwolszczak.job.portal.repository;

import dev.kwolszczak.job.portal.entity.RecruiterProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecruiterProfileRepository extends JpaRepository<RecruiterProfile,Integer> {
}
