package cn.staynoob.javademo.repository;

import cn.staynoob.javademo.entity.CompositeExample;
import cn.staynoob.springdatajpahelper.repository.JpaHelperRepository;
import org.springframework.data.repository.CrudRepository;

public interface CompositeExampleRepository extends
        CrudRepository<CompositeExample, Integer>,
        JpaHelperRepository<CompositeExample, Integer> {
}
