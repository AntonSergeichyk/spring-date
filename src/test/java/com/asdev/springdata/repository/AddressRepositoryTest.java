package com.asdev.springdata.repository;

import com.asdev.springdata.entity.AddressEntity;
import com.asdev.springdata.entity.QAddressEntity;
import com.asdev.springdata.entity.filter.AddressFilter;
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
import static org.junit.jupiter.api.Assertions.assertTrue;

class AddressRepositoryTest extends IntegrationRepositoryTestBase {

    @Autowired
    private AddressRepository repository;

    @Test
    void save() {
        AddressEntity address = repository.save(AddressEntity.builder().city("Brest").street("Rokossovskogo").build());
        Assertions.assertNotNull(address.getId());
    }

    @Test
    void findByCityContaining() {
        List<AddressEntity> addresses = repository.findByCityContaining("Mins");
        MatcherAssert.assertThat(addresses, hasSize(2));
    }

    @Test
    void findByCity() {
        List<AddressEntity> addresses = repository.findByCity("Minsk");
        MatcherAssert.assertThat(addresses, hasSize(2));
    }

    @Test
    void findByCityContainingAndStreet() {
        List<AddressEntity> addresses = repository.findByCityContainingAndStreet("Min", "Odincova");
        MatcherAssert.assertThat(addresses, hasSize(1));
    }

    @Test
    void findByCityAndStreet() {
        List<AddressEntity> addresses = repository.findByCityContainingAndStreet("Minsk", "Odincova");
        MatcherAssert.assertThat(addresses, hasSize(1));
    }

    @Test
    void findByEmployeeId() {
        Optional<AddressEntity> address = repository.findByEmployeeId(1L);
        Assertions.assertEquals(address.get().getStreet(), "Odincova");
    }

    @Test
    void findByFilter() {
        AddressFilter filter = AddressFilter.builder()
                .employeeId(2L)
                .build();
        List<AddressEntity> addresses = repository.findByFilter(filter);
        MatcherAssert.assertThat(addresses, hasSize(1));
    }

    @Test
    void findAll() {
        BooleanExpression predicate = QAddressEntity.addressEntity.city.contains("Minsk")
                .and(QAddressEntity.addressEntity.street.contains("Odincova"));
        Iterable<AddressEntity> addresses = repository.findAll(predicate);
        assertTrue(addresses.iterator().hasNext());
    }

    @Test
    void qPredicatesTest() {
        AddressFilter filter = AddressFilter.builder()
                .city("Minsk")
                .build();
        Predicate predicate = QPredicates.builder()
                .add(filter.getCity(), QAddressEntity.addressEntity.city::contains)
                .add(filter.getStreet(), QAddressEntity.addressEntity.street::contains)
                .buildAnd();
        Iterable<AddressEntity> addresses = repository.findAll(predicate);
        Assertions.assertTrue(addresses.iterator().hasNext());
    }
}