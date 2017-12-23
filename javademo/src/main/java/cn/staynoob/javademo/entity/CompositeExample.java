package cn.staynoob.javademo.entity;

import cn.staynoob.springdatajpahelper.annotation.CompositeUnique;
import cn.staynoob.springdatajpahelper.annotation.FriendlyUnique;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@FriendlyUnique(compositeUniques = {
        @CompositeUnique(fields = {"foo", "bar"})
})
public class CompositeExample {
    @Id
    @GeneratedValue
    private Integer id;

    private String foo;
    private String bar;

    public CompositeExample() {
    }

    public CompositeExample(String foo, String bar) {
        this.foo = foo;
        this.bar = bar;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFoo() {
        return foo;
    }

    public void setFoo(String foo) {
        this.foo = foo;
    }

    public String getBar() {
        return bar;
    }

    public void setBar(String bar) {
        this.bar = bar;
    }
}
