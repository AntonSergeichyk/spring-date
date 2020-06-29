package com.asdev.springdata.repository;

import com.asdev.springdata.entity.AddressEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AddressRepository extends JpaRepository<AddressEntity, Long>, AddressCustomRepository,
        QuerydslPredicateExecutor<AddressEntity> {

    List<AddressEntity> findByCityContaining(String city);

    @Query("select a from AddressEntity a where a.city = :city ")
    List<AddressEntity> findByCity(@Param("city") String city);

    List<AddressEntity> findByCityContainingAndStreet(String city, String street);

    @Query("select a from AddressEntity a where a.city = :city and a.street = :street")
    List<AddressEntity> findByCityAndStreet(@Param("city") String city, @Param("street") String street);

    @Query("select a from EmployeeEntity e left join AddressEntity a on e.company.id = a.id where e.id = :employee_id")
    Optional<AddressEntity> findByEmployeeId(@Param("employee_id") Long employeeId);

}
