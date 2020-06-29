package com.asdev.springdata.entity.filter;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeFilter {

    private String firstName;
    private String secondName;
    private Integer age;

}
