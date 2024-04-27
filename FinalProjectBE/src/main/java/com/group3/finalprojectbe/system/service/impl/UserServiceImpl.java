package com.group3.finalprojectbe.system.service.impl;


import com.group3.finalprojectbe.system.config.JwtTokenProvider;
import com.group3.finalprojectbe.system.dto.CourseDTO;
import com.group3.finalprojectbe.system.dto.LoginRequest;
import com.group3.finalprojectbe.system.dto.RegisterRequest;
import com.group3.finalprojectbe.system.dto.UserDto;
import com.group3.finalprojectbe.system.entity.CourseEntity;
import com.group3.finalprojectbe.system.entity.User;
import com.group3.finalprojectbe.system.entity.UserPrincipal;
import com.group3.finalprojectbe.system.excption.BizExceptionKit;
import com.group3.finalprojectbe.system.excption.ExceptionString;
import com.group3.finalprojectbe.system.mapper.CourseMapper;
import com.group3.finalprojectbe.system.mapper.UserMapper;
import com.group3.finalprojectbe.system.repo.CourseRepository;
import com.group3.finalprojectbe.system.repo.UserRepository;
import com.group3.finalprojectbe.system.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    private final UserMapper userMapper;
    private final CourseMapper courseMapper;
    private final CourseRepository courseRepository;

    public String registerUser(RegisterRequest registerRequest) {
        Optional<User> optionalUser = userRepository.findByUsername(registerRequest.getUsername());
        if (optionalUser.isEmpty()) {
            User user = new User();
            user.setUsername(registerRequest.getUsername());
            user.setFirstName(registerRequest.getFirstName());
            user.setLastName(registerRequest.getLastName());
            user.setPhoneNumber(registerRequest.getPhoneNumber());
            user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
            user.setEmail(registerRequest.getEmail());
            userRepository.save(user);


            UserPrincipal userPrincipal = new UserPrincipal(user, null);

            return jwtTokenProvider.generateToken(userPrincipal);
        }
        throw BizExceptionKit.of(ExceptionString.USERNAME_ALREADY_EXIST);
    }

    @Override
    public String loginUser(LoginRequest loginRequest) {
        Optional<User> optionalUser = userRepository.findByEmail(loginRequest.getEmail());
        if (optionalUser.isPresent()) {
            String password = loginRequest.getPassword();
            if (passwordEncoder.matches(password, optionalUser.get().getPassword())) {

                UserPrincipal userPrincipal = new UserPrincipal(optionalUser.get(), null);
                return jwtTokenProvider.generateToken(userPrincipal);
            } else {

                BizExceptionKit.of(ExceptionString.PASSWORD_NOT_MATCH).throwIt();
            }
        }
        BizExceptionKit.of(ExceptionString.USER_NOT_FOUND + " by the email " + loginRequest.getEmail()).throwIt();
        return null;
    }

    @Override
    public UserDto getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> BizExceptionKit.of(ExceptionString.USER_NOT_FOUND + " by the userId " + id));
        return userMapper.apply(user);

    }

    @Override
    @Transactional
    public String editUser(Long userId, RegisterRequest user) {
        User oldUser = userRepository.findById(userId)
                .orElseThrow(() -> BizExceptionKit.of(ExceptionString.USER_NOT_FOUND + " by the userId " + userId));
//        oldUser.setUsername(user.getUsername());

        oldUser.setPhoneNumber(user.getPhoneNumber());
        oldUser.setPassword(passwordEncoder
                .encode(user.getPassword()));
        return jwtTokenProvider
                .generateToken(new UserPrincipal(oldUser, null));
    }

    @Override
    public List<CourseDTO> getCourseByUserId(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> BizExceptionKit.of(ExceptionString.USER_NOT_FOUND + " by the userId " + id));
        return user.getCourses().stream().map(courseMapper::apply).toList();
    }

    @Override
    @Transactional
    public UserDto addCourseToUser(Long userId, Long courseId) {
        User user = userRepository.findById(userId).orElseThrow(() -> BizExceptionKit.of(ExceptionString.USER_NOT_FOUND + " by the userId " + userId));
        CourseEntity courseEntity = courseRepository
                .findById(courseId).orElseThrow(() -> BizExceptionKit.of(ExceptionString.COURSE_NOT_FOUND + " by the courseId " + courseId));

        user.addCourse(courseEntity);


        User newUser = userRepository.save(user);
        return userMapper.apply(newUser);
    }

    @Override
    @Transactional
    public UserDto removeCourseFromUser(Long userId, Long courseId) {

        User user = userRepository.findById(userId).orElseThrow(() -> BizExceptionKit.of(ExceptionString.USER_NOT_FOUND + " by the userId " + userId));
        CourseEntity courseEntity = courseRepository
                .findById(courseId).orElseThrow(() -> BizExceptionKit.of(ExceptionString.COURSE_NOT_FOUND + " by the courseId " + courseId));
        if (user.getCourses().contains(courseEntity)) {

            user.removeCourse(courseEntity);
        } else {
            throw BizExceptionKit.of(ExceptionString.COURSE_NOT_FOUND + " in this user's course list");
        }
        User newUser = userRepository.save(user);


        return userMapper.apply(newUser);
    }


    @Override
    public List<User> getAllUser() {
        return userRepository.findAll();
    }


}
