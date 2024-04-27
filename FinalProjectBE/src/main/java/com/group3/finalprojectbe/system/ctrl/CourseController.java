package com.group3.finalprojectbe.system.ctrl;

import com.group3.finalprojectbe.system.dto.CourseDTO;
import com.group3.finalprojectbe.system.dto.CourseRegisterDTO;
import com.group3.finalprojectbe.system.service.CourseService;
import com.group3.finalprojectbe.system.service.CourseTypeService;
import com.group3.finalprojectbe.system.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/course")
@RequiredArgsConstructor
public class CourseController {

    private final CourseService courseService;
    private final CourseTypeService courseTypeService;
    private final UserService userService;


    @GetMapping("/safe/allCoursesByTypeId/{courseTypeId}")
    public ResponseEntity<List<CourseDTO>> getAllCoursesByTypeId(@PathVariable("courseTypeId") Long courseTypeId) {

        List<CourseDTO> courseByTypeId = courseTypeService.getCoursesByTypeId(courseTypeId);

        return ResponseEntity.ok(courseByTypeId);
    }

    @PostMapping("/courseRegister")
    public ResponseEntity<CourseRegisterDTO> selectCourse(@RequestBody CourseRegisterDTO courseRegister) {
        try {
            CourseRegisterDTO result = courseService.addCourseRegister(courseRegister.getUserId(), courseRegister.getCourseId());
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/courseList/{userId}")
    public ResponseEntity<List<CourseDTO>> getCourse(@PathVariable("userId") Long userId) {
        ;
        return ResponseEntity.ok(userService.getCourseByUserId(userId));

    }

}
