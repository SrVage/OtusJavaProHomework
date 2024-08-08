package org.example.dto;

import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;

@ToString
@Getter
@Setter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class StudentDto implements Serializable {
    private String firstName;
    private String lastName;
    private Integer educationCode;
    private LocalDate dateOfBirth;
    private Double averageMark;
}
