package com.group3.finalprojectbe.system.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CourseRegisterDTO {

    @NotNull
    private Long userId;

    @NotNull
    private Long courseId;
}
