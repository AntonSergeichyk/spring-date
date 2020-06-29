package com.asdev.springdata.repository.impl;

import com.asdev.springdata.entity.CompanyEntity;
import com.asdev.springdata.entity.QCompanyEntity;
import com.asdev.springdata.entity.filter.CompanyFilter;
import com.asdev.springdata.repository.CompanyCustomRepository;
import com.querydsl.jpa.impl.JPAQuery;
import lombok.RequiredArgsConstructor;

import javax.persistence.EntityManager;
import java.util.List;

@RequiredArgsConstructor
public class CompanyRepositoryImpl implements CompanyCustomRepository {

    private final EntityManager entityManager;

    @Override
    public List<CompanyEntity> findByFilter(CompanyFilter filter) {
        return new JPAQuery<CompanyEntity>(entityManager)
                .select(QCompanyEntity.companyEntity)
                .from(QCompanyEntity.companyEntity)
                .where(QCompanyEntity.companyEntity.name.eq(filter.getName()))
                .fetch();
    }

}
