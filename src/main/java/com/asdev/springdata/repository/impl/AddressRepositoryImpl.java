package com.asdev.springdata.repository.impl;

import com.asdev.springdata.entity.AddressEntity;
import com.asdev.springdata.entity.QAddressEntity;
import com.asdev.springdata.entity.QEmployeeEntity;
import com.asdev.springdata.entity.filter.AddressFilter;
import com.asdev.springdata.repository.AddressCustomRepository;
import com.querydsl.jpa.impl.JPAQuery;
import lombok.RequiredArgsConstructor;

import javax.persistence.EntityManager;
import java.util.List;

@RequiredArgsConstructor
public class AddressRepositoryImpl implements AddressCustomRepository {

    private final EntityManager entityManager;


    @Override
    public List<AddressEntity> findByFilter(AddressFilter filter) {
        return new JPAQuery<AddressEntity>(entityManager)
                .select(QAddressEntity.addressEntity)
                .from(QAddressEntity.addressEntity)
                .leftJoin(QEmployeeEntity.employeeEntity)
                .on(QEmployeeEntity.employeeEntity.addresses.contains(QAddressEntity.addressEntity))
                .where(QEmployeeEntity.employeeEntity.id.eq(filter.getEmployeeId()))
                .fetch();
    }
}
