package com.asdev.springdata.repository;

import com.asdev.springdata.entity.AddressEntity;
import com.asdev.springdata.entity.CompanyEntity;
import com.asdev.springdata.entity.EmployeeEntity;
import com.asdev.springdata.entity.QEmployeeEntity;
import com.asdev.springdata.entity.filter.EmployeeFilter;
import com.asdev.springdata.utit.QPredicates;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;

class EmployeeRepositoryTest extends IntegrationRepositoryTestBase {

    @Autowired
    private EmployeeRepository repository;
    @Autowired
    private CompanyRepository companyRepository;
    @Autowired
    private AddressRepository addressRepository;

    @Test
    void save() {
        List<CompanyEntity> company = companyRepository.findByName("netCracker");
        AddressEntity address = addressRepository.save(AddressEntity.builder().city("Brest").street("Rokossovskogo").build());
        List<AddressEntity> addresses = new ArrayList<>();
        addresses.add(address);
        EmployeeEntity employee = repository.save(EmployeeEntity.builder()
                .firstName("Pavel")
                .secondName("Volosyk")
                .age(19)
                .company(company.get(0))
                .addresses(addresses)
                .build());
        Assertions.assertNotNull(employee.getId());
    }

    @Test
    void findByFirstNameContaining() {
        List<EmployeeEntity> employees = repository.findByFirstNameContaining("Pet");
        MatcherAssert.assertThat(employees, hasSize(1));
    }

    @Test
    void findByFirstNameContainingAndAgeGreaterThan() {
        List<EmployeeEntity> employees = repository.findByFirstNameContainingAndAgeGreaterThan("Anton", 26);
        MatcherAssert.assertThat(employees, hasSize(1));
    }

    @Test
    void findByFirstNameAndSecondName() {
        List<EmployeeEntity> employees = repository.findByFirstNameAndSecondName("Anton", "Sergeichyk");
        MatcherAssert.assertThat(employees, hasSize(1));
    }

    @Test
    void findByStreetAndCompanyName() {
        List<EmployeeEntity> employees = repository.findByStreetAndCompanyName("Odincova", "netCracker");
        MatcherAssert.assertThat(employees, hasSize(1));
    }

    @Test
    void findByFilter() {
        EmployeeFilter filter = EmployeeFilter.builder()
                .firstName("Ant")
                .build();
        List<EmployeeEntity> employees = repository.findByFilter(filter);
        MatcherAssert.assertThat(employees, hasSize(1));
    }

    @Test
    void findAll() {
        BooleanExpression predicate = QEmployeeEntity.employeeEntity.firstName.containsIgnoreCase("ant")
                .and(QEmployeeEntity.employeeEntity.age.between(20, 30));
        Page<EmployeeRepository> employees = repository.findAll(predicate, Pageable.unpaged());
        MatcherAssert.assertThat(employees.getContent(), hasSize(1));
    }

    @Test
    void qPredicateTest() {
        EmployeeFilter filter = EmployeeFilter.builder()
                .firstName("Anton")
                .secondName("Sergeichyk")
                .build();
        Predicate predicate = QPredicates.builder()
                .add(filter.getFirstName(), QEmployeeEntity.employeeEntity.firstName::contains)
                .add(filter.getSecondName(), QEmployeeEntity.employeeEntity.secondName::contains)
                .add(filter.getAge(), QEmployeeEntity.employeeEntity.age::goe)
                .buildAnd();
        Iterable<EmployeeRepository> employees = repository.findAll(predicate);
        Assertions.assertTrue(employees.iterator().hasNext());
    }
}