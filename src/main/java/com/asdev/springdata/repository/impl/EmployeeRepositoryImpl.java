package com.asdev.springdata.repository.impl;

import com.asdev.springdata.entity.EmployeeEntity;
import com.asdev.springdata.entity.QEmployeeEntity;
import com.asdev.springdata.entity.filter.EmployeeFilter;
import com.asdev.springdata.repository.EmployeeCustomRepository;
import com.querydsl.jpa.impl.JPAQuery;
import lombok.RequiredArgsConstructor;

import javax.persistence.EntityManager;
import java.util.List;

@RequiredArgsConstructor
public class EmployeeRepositoryImpl implements EmployeeCustomRepository {

    private final EntityManager entityManager;

    @Override
    public List<EmployeeEntity> findByFilter(EmployeeFilter filter) {
        return new JPAQuery<EmployeeEntity>(entityManager)
                .select(QEmployeeEntity.employeeEntity)
                .from(QEmployeeEntity.employeeEntity)
                .where(QEmployeeEntity.employeeEntity.firstName.containsIgnoreCase(filter.getFirstName()))
                .fetch();
    }
}
