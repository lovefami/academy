package com.group3.finalprojectbe.system.dto;

import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.time.LocalDate;


public record CourseDTO(Long id, @NotNull LocalDate startDate, String duration, CourseTypeDTO courseType
) implements Serializable {
}
