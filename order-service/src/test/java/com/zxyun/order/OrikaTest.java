package com.zxyun.order;

import com.zxyun.order.orika.MapperUtils;

/**
 * @des:
 * @Author: given
 * @Date 2020/3/16 16:07
 */
public class OrikaTest {

    public static void main (String[] args) {
        B b = new B();
        b.setName("aaa");
        b.setAge(3);
        A a = MapperUtils.INSTANCE.map(A.class, b);
        int i = 1;
    }

    static class A {
        private String name;
        private Integer age;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Integer getAge() {
            return age;
        }

        public void setAge(Integer age) {
            this.age = age;
        }
    }

    static class B {
        private String name;
        private Integer age;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Integer getAge() {
            return age;
        }

        public void setAge(Integer age) {
            this.age = age;
        }
    }
}
