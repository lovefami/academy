package com.group3.finalprojectbe.system.ctrl;

import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.ObjectUtil;
import com.group3.finalprojectbe.system.config.SecContext;
import com.group3.finalprojectbe.system.dto.RegisterRequest;
import com.group3.finalprojectbe.system.dto.UserDto;
import com.group3.finalprojectbe.system.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 *
 */
@RestController
@Slf4j
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> fetchUserByUserId(@PathVariable Long userId) {

        UserDto userDto = userService.getUserById(userId);
        return ResponseEntity.ok(userDto);
    }

    @GetMapping("/fetchUser")
    public ResponseEntity<UserDto> fetchUserByToken() {
        Long userId = SecContext.getUserId();
        UserDto userDto = userService.getUserById(userId);
        return ResponseEntity.ok(userDto);
    }

    @PutMapping("/editUser/{userId}")
    public ResponseEntity<Map<String, String>> editUser(@PathVariable("userId") Long userId, @Validated @RequestBody RegisterRequest user) {
        String newToken = userService.editUser(userId, user);
        return ResponseEntity.ok(MapUtil.of("token", newToken));
    }

    @PostMapping("/addCourseToUser/{userId}/{courseId}")
    public ResponseEntity<UserDto> addCourseToUser(@PathVariable("userId") Long userId, @PathVariable("courseId") Long courseId) {

        UserDto userDto = userService.addCourseToUser(userId, courseId);
        return ResponseEntity.ok(userDto);
    }

    @PostMapping("/removeCourseFromUser/{userId}/{courseId}")
    public ResponseEntity<UserDto> removeCourseFromUser(@PathVariable("userId") Long userId, @PathVariable("courseId") Long courseId) {
        userService.removeCourseFromUser(userId, courseId);
        return ResponseEntity.ok(null);
    }

}
