package com.asdev.springdata.repository;

import com.asdev.springdata.entity.EmployeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<EmployeeEntity, Long>, EmployeeCustomRepository,
        QuerydslPredicateExecutor<EmployeeRepository> {

    List<EmployeeEntity> findByFirstNameContaining(String firstName);

    List<EmployeeEntity> findByFirstNameContainingAndAgeGreaterThan(String firstName, Integer age);

    @Query("select e from EmployeeEntity e where e.firstName = :first_name and e.secondName = :second_name")
    List<EmployeeEntity> findByFirstNameAndSecondName(@Param("first_name") String firstName, @Param("second_name") String secondName);

    @Query(value = "select e.id, e.first_name, e.second_name, e.age, e.company_id from employee e inner join company c on c.id = e.company_id " +
            "inner join address a on e.id = a.employee_id where a.street = :street and c.name = :company_name", nativeQuery = true)
    List<EmployeeEntity> findByStreetAndCompanyName(@Param("street") String street, @Param("company_name") String companyName);
}
