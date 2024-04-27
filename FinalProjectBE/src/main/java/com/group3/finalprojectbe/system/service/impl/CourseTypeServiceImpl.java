package com.group3.finalprojectbe.system.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.group3.finalprojectbe.system.dto.CourseDTO;
import com.group3.finalprojectbe.system.dto.CourseTypeDTO;
import com.group3.finalprojectbe.system.entity.CourseEntity;
import com.group3.finalprojectbe.system.entity.CourseTypeEntity;
import com.group3.finalprojectbe.system.excption.BizExceptionKit;
import com.group3.finalprojectbe.system.excption.ExceptionString;
import com.group3.finalprojectbe.system.mapper.CourseMapper;
import com.group3.finalprojectbe.system.mapper.CourseTypeMapperHelper;
import com.group3.finalprojectbe.system.repo.CourseTypeRepository;
import com.group3.finalprojectbe.system.service.CourseTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseTypeServiceImpl implements CourseTypeService {

    private final CourseTypeRepository courseTypeRepository;
    private final CourseTypeMapperHelper courseTypeMapperHelper;
    private final CourseMapper courseMapper;


    @Override
    public List<CourseTypeDTO> getAllTypes() {
        List<CourseTypeEntity> all = courseTypeRepository.findAll();
        boolean empty = CollUtil.isEmpty(all);
        if (empty) {
            throw BizExceptionKit.of(ExceptionString.COURSE_TYPE_NOT_FOUND);
        } else {
            return all.stream().map(courseTypeMapperHelper::apply).toList();
        }

    }

    @Override
    @Transactional
    public List<CourseDTO> getCoursesByTypeId(Long typeId) {
        //get the courseType from database according to the courseTypeId, throw exception when courseType can not be found
        CourseTypeEntity courseType = courseTypeRepository.findById(typeId).orElseThrow(() -> BizExceptionKit.of("Course Type can not be found by type id " + typeId));
        //get the list of course from this courseType
        List<CourseEntity> courses = courseType.getCourses();
        //convert courseEntity to courseDTO, if the list is not empty

        return CollUtil.isEmpty(courses) ? Collections.emptyList() :
                courses.stream().map(courseMapper::apply).toList();

    }
}
