package com.group3.finalprojectbe.system.ctrl;

import com.group3.finalprojectbe.system.dto.CourseTypeDTO;
import com.group3.finalprojectbe.system.service.CourseTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController

@RequestMapping("/api/courseType")
@RequiredArgsConstructor
public class CourseTypeController {

    private final CourseTypeService courseTypeService;

    @GetMapping("/courseTypes")
    public ResponseEntity<List<CourseTypeDTO>> getCourseType() {
        return ResponseEntity.ok(courseTypeService.getAllTypes());
    }
}
