package com.group3.finalprojectbe.system.mapper;

import cn.hutool.core.codec.Base64;
import com.group3.finalprojectbe.system.dto.CourseTypeDTO;
import com.group3.finalprojectbe.system.entity.CourseTypeEntity;
import com.group3.finalprojectbe.system.excption.BizExceptionKit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.sql.Blob;
import java.sql.SQLException;
import java.util.function.Function;

@Component
@Slf4j
public class CourseTypeMapperHelper implements Function<CourseTypeEntity, CourseTypeDTO> {

    @Override
    public CourseTypeDTO apply(CourseTypeEntity courseTypeEntity) {
        Blob image = courseTypeEntity.getImage();
        String imageBase64Str = null;
        if (image != null) {
            try {
                byte[] bytes = image.getBytes(1, (int) image.length());
                imageBase64Str = Base64.encode(bytes);
            } catch (Exception e) {
                log.error("SQL error when retrieving image: {}", e.getMessage(), e);
                throw BizExceptionKit.of("SQL error when retrieving image: " + e.getMessage());
            }

        }
        return CourseTypeDTO.builder()
                .id(courseTypeEntity.getId())
                .name(courseTypeEntity.getName())
                .description(courseTypeEntity.getDescription())
                .image(imageBase64Str)
                .build();
    }
}
