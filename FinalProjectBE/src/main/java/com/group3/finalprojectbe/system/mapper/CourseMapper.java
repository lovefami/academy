package com.group3.finalprojectbe.system.mapper;

import com.group3.finalprojectbe.system.dto.CourseDTO;
import com.group3.finalprojectbe.system.entity.CourseEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.cglib.core.internal.Function;
import org.springframework.stereotype.Component;

/**
 *
 */
@Component
@RequiredArgsConstructor

public class CourseMapper implements Function<CourseEntity, CourseDTO> {
    private final UserMapper userMapper;
    private final CourseTypeMapperHelper courseTypeMapperHelper;

    @Override
    public CourseDTO apply(CourseEntity course) {
        return new CourseDTO(course.getId(), course.getStartDate(), course.getDuration(), courseTypeMapperHelper.apply(course.getTypeLinked()));
    }
}
