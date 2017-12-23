package cn.staynoob.javademo.repository;

import cn.staynoob.javademo.entity.Vehicle;
import cn.staynoob.springdatajpahelper.autoconfigure.JpaHelperAutoConfiguration;
import cn.staynoob.springdatajpahelper.exception.DuplicatedEntityException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@ImportAutoConfiguration(JpaHelperAutoConfiguration.class)
class VehicleRepositoryTest {
    @Autowired
    private VehicleRepository repository;

    @Test
    @DisplayName("save duplicate entity should throw exception with handy error details")
    void friendlyUniqueConstraint() {
        Vehicle vehicle1 = new Vehicle("foo");
        Vehicle vehicle2 = new Vehicle("foo");
        repository.save(vehicle1);
        try {
            repository.save(vehicle2);
            fail("should throw exception");
        } catch (DuplicatedEntityException e) {
            assertThat(e.getMessage())
                    .isEqualTo("duplicated entity of type Vehicle");
            assertThat(e.getFieldMap())
                    .containsKey("plateNumber")
                    .containsValue("foo");
        }
    }

    @Test
    @DisplayName("findByUniqueKey")
    void findByUniqueKey() {
        Vehicle vehicle1 = new Vehicle("foo");
        repository.save(vehicle1);
        Vehicle vehicle2 = new Vehicle("foo");
        Vehicle result = repository.findByUQ(vehicle2);
        assertThat(result).isNotNull();
    }
}