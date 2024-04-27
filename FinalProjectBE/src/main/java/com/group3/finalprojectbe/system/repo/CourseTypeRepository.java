package com.group3.finalprojectbe.system.repo;

import com.group3.finalprojectbe.system.entity.CourseTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseTypeRepository extends JpaRepository<CourseTypeEntity, Long> {
}