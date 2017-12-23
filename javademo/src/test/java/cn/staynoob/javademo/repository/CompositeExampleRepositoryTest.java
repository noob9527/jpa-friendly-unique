package cn.staynoob.javademo.repository;

import cn.staynoob.javademo.entity.CompositeExample;
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
import static org.assertj.core.api.Fail.fail;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@ImportAutoConfiguration(JpaHelperAutoConfiguration.class)
class CompositeExampleRepositoryTest {

    @Autowired
    private CompositeExampleRepository repository;

    @Test
    @DisplayName("save duplicate entity should throw exception with handy error details")
    void friendlyUniqueConstraint() {
        CompositeExample sample1 = new CompositeExample("foo", "bar");
        CompositeExample sample2 = new CompositeExample("foo", "bar");
        repository.save(sample1);
        try {
            repository.save(sample2);
            fail("should throw exception");
        } catch (DuplicatedEntityException e) {
            assertThat(e.getMessage())
                    .isEqualTo("duplicated entity of type CompositeExample");
            assertThat(e.getFieldMap())
                    .containsKeys("foo", "bar")
                    .containsValues("foo", "bar");
        }
    }

    @Test
    @DisplayName("findByUniqueKey")
    void findByUniqueKey() {
        CompositeExample sample1 = new CompositeExample("foo", "bar");
        repository.save(sample1);
        CompositeExample sample2 = new CompositeExample("foo", "bar");
        CompositeExample result = repository.findByUQ(sample2);
        assertThat(result).isNotNull();
    }
}