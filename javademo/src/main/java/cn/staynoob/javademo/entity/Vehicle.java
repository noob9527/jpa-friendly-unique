package cn.staynoob.javademo.entity;

import cn.staynoob.springdatajpahelper.annotation.Unique;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Vehicle {
    @Id
    @GeneratedValue
    private Integer id;
    @Unique
    private String plateNumber;

    public Vehicle() {
    }

    public Vehicle(String plateNumber) {
        this.plateNumber = plateNumber;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPlateNumber() {
        return plateNumber;
    }

    public void setPlateNumber(String plateNumber) {
        this.plateNumber = plateNumber;
    }
}
