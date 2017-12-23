package cn.staynoob.javademo.repository;

import cn.staynoob.javademo.entity.Vehicle;
import cn.staynoob.springdatajpahelper.repository.JpaHelperRepository;
import org.springframework.data.repository.CrudRepository;

public interface VehicleRepository extends
        CrudRepository<Vehicle, Integer>,
        JpaHelperRepository<Vehicle, Integer> {
}
