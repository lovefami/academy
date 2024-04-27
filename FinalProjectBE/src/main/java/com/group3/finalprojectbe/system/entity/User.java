package com.group3.finalprojectbe.system.entity;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.group3.finalprojectbe.system.excption.BizExceptionKit;
import jakarta.validation.constraints.Email;
import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.lang.Nullable;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Data
@Accessors(chain = true)
@Entity
@Table(name = "users")
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username", nullable = false, unique = true, length = 20)
    private String username;

    @Nullable
    @Column(name = "first_name", length = 20)
    private String firstName;

    @Nullable
    @Column(name = "last_name", length = 20)
    private String lastName;


    @Column(name = "phone_number", length = 10)
    private String phoneNumber;


    @Column(name = "email", unique = true, length = 50)
    @Email
    private String email;

    @JsonIgnore
    @Column(name = "password", length = 60)
    private String password;


    @Fetch(FetchMode.SELECT)
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "zz_user_roles",
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "id")}
    )
    private Set<Role> roles;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @ManyToMany
    @JoinTable(
            name = "user_course",
            joinColumns = @JoinColumn(name="user_id"),
            inverseJoinColumns = @JoinColumn(name = "course_id")
    )
    private List<CourseEntity> courses = new ArrayList<>();

    public void addCourse(CourseEntity course){
        boolean contain = CollUtil.anyMatch(courses, c -> c.getId().equals(course.getId()));
        if(contain){
            throw BizExceptionKit.of("User has registered this course, can not register the course repeatable");

        }else {
            getCourses().add(course);
        }
    }

    public void removeCourse(CourseEntity course){
        getCourses().remove(course);
    }
}
