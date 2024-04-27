package com.group3.finalprojectbe.system.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.sql.Blob;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "course_type")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CourseTypeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", unique = true)
    @NotNull
    private String name;

    @NotBlank
    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    @Lob
    @NotNull
    private Blob image;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "typeLinked")
    @Builder.Default
    @JsonIgnore
    private List<CourseEntity> courses = new ArrayList<>();


    public void registerCourseUnderThisType(CourseEntity course) {
        getCourses().add(course);
    }

    public void unRegisterCourseUnderThisType(CourseEntity course) {
        getCourses().remove(course);
    }

}
