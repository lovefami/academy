package com.group3.finalprojectbe.system.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "course")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CourseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(nullable = false)
    @NotNull
    private LocalDate startDate;
    @Column
    private String duration;

    @ManyToOne
    @JoinColumn(name = "typeId", nullable = false)
    private CourseTypeEntity typeLinked;
}
