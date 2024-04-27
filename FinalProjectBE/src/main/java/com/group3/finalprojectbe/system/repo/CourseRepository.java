package com.group3.finalprojectbe.system.repo;

import com.group3.finalprojectbe.system.dto.CourseRegisterDTO;
import com.group3.finalprojectbe.system.entity.CourseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends JpaRepository<CourseEntity, Long> {


    @Query("select new com.group3.finalprojectbe.system.dto.CourseRegisterDTO (u.id, c.id) " +
            "from CourseEntity c ,User u  where c.id = :courseId and u.id = :userId")
    CourseRegisterDTO getCourseRegister(@Param("userId") Long userId, @Param("courseId") Long courseId);

}
