package com.asdev.springdata.repository;

import com.asdev.springdata.entity.CompanyEntity;
import com.asdev.springdata.entity.QCompanyEntity;
import com.asdev.springdata.entity.filter.CompanyFilter;
import com.asdev.springdata.utit.QPredicates;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;

class CompanyRepositoryTest extends IntegrationRepositoryTestBase {

    @Autowired
    private CompanyRepository repository;

    @Test
    void save() {
        CompanyEntity company = CompanyEntity.builder().name("Fitbit").build();
        repository.save(company);
        Assertions.assertNotNull(company.getId());
    }

    @Test
    void findByNameContaining() {
        List<CompanyEntity> companies = repository.findByNameContaining("Point");
        MatcherAssert.assertThat(companies, hasSize(1));
    }

    @Test
    void findByName() {
        List<CompanyEntity> companies = repository.findByNameContaining("netCracker");
        MatcherAssert.assertThat(companies, hasSize(1));
    }

    @Test
    void findByEmployeeId() {
        Optional<CompanyEntity> company = repository.findByEmployeeId(2L);
        Assertions.assertEquals(company.get().getName(), "netCracker");
    }

    @Test
    void findByFilter() {
        CompanyFilter filter = CompanyFilter.builder()
                .name("checkPoint")
                .build();
        List<CompanyEntity> company = repository.findByFilter(filter);
        MatcherAssert.assertThat(company, hasSize(1));
    }

    @Test
    void findAll() {
        BooleanExpression predicate = QCompanyEntity.companyEntity.id.eq(1L);
        Iterable<CompanyEntity> company = repository.findAll(predicate);
        Assertions.assertTrue(company.iterator().hasNext());
    }

    @Test
    void qPredicateTest() {
        CompanyFilter company = CompanyFilter.builder()
                .name("netCracker")
                .build();
        Predicate predicate = QPredicates.builder().add(company.getId(), QCompanyEntity.companyEntity.id::eq)
                .add(company.getName(), QCompanyEntity.companyEntity.name::contains)
                .buildAnd();
        Iterable<CompanyEntity> companies = repository.findAll(predicate);
        Assertions.assertTrue(companies.iterator().hasNext());
    }
}