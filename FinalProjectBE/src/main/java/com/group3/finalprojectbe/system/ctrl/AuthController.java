package com.group3.finalprojectbe.system.ctrl;

import cn.hutool.core.map.MapUtil;
import com.group3.finalprojectbe.system.dto.LoginRequest;
import com.group3.finalprojectbe.system.dto.RegisterRequest;
import com.group3.finalprojectbe.system.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;


@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;


    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> registerUser(@RequestBody RegisterRequest registerRequest) {
        String jwt = userService.registerUser(registerRequest);
        return ResponseEntity.ok(MapUtil.of("token", jwt));
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> loginUser(@RequestBody LoginRequest loginRequest) {
        String jwt = userService.loginUser(loginRequest);
        return ResponseEntity.ok(MapUtil.of("token", jwt));
    }

}
