package com.asdev.springdata.entity.filter;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AddressFilter {

    private String city;
    private String street;
    private Long employeeId;

}
