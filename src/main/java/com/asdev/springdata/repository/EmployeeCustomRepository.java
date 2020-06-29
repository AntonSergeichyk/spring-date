package com.asdev.springdata.repository;

import com.asdev.springdata.entity.EmployeeEntity;
import com.asdev.springdata.entity.filter.EmployeeFilter;

import java.util.List;

public interface EmployeeCustomRepository {

    List<EmployeeEntity> findByFilter(EmployeeFilter filter);
}
