package com.group3.finalprojectbe.system.service.impl;

import com.group3.finalprojectbe.system.dto.CourseRegisterDTO;
import com.group3.finalprojectbe.system.entity.CourseEntity;
import com.group3.finalprojectbe.system.entity.User;
import com.group3.finalprojectbe.system.repo.CourseRepository;
import com.group3.finalprojectbe.system.repo.UserRepository;
import com.group3.finalprojectbe.system.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;
    private final UserRepository userRepository;


    @Override
    public CourseRegisterDTO addCourseRegister(Long userId, Long courseId) {

        if (courseRepository.findById(courseId).isPresent() && userRepository.findById(userId).isPresent()) {
            CourseEntity selectedCourse = courseRepository.findById(courseId).get();
            User user = userRepository.findById(userId).get();
            List<CourseEntity> courses = user.getCourses();
            if (!courses.contains(selectedCourse)) {
                courses.add(selectedCourse);
            }
            user.setCourses(courses);
            User save = userRepository.save(user);
            return courseRepository.getCourseRegister(userId, courseId);
        } else {
            throw new RuntimeException("Course or User not exist");
        }
    }
}
