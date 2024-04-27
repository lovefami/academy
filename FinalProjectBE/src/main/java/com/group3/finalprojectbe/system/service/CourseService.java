package com.group3.finalprojectbe.system.service;

import com.group3.finalprojectbe.system.dto.CourseDTO;
import com.group3.finalprojectbe.system.dto.CourseRegisterDTO;

import java.util.List;

public interface CourseService {
    //add a new function in courseType service instead of this one, delete
//    List<CourseDTO> getCoursesByTypeId(Long typeId);

    CourseRegisterDTO addCourseRegister(Long userId, Long courseId);
}
