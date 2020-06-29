package com.asdev.springdata.repository;

import com.asdev.springdata.entity.CompanyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CompanyRepository extends JpaRepository<CompanyEntity, Long>, CompanyCustomRepository,
        QuerydslPredicateExecutor<CompanyEntity> {

    List<CompanyEntity> findByNameContaining(String name);

    @Query("select c from CompanyEntity c where c.name = :name")
    List<CompanyEntity> findByName(@Param("name") String name);

    @Query("select c from EmployeeEntity e left join CompanyEntity c on e.company.id = c.id where e.id = :employee_id")
    Optional<CompanyEntity> findByEmployeeId(@Param("employee_id") Long employeeId);
}
