package com.chengzi.framework;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;
import java.util.function.Predicate;

/**
 * Created by fanshaowei on 2018-8-8.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class PredicateTest {
    private final Integer DEFAULT_AGE = 20;

    @Test
    public void testPredicate(){
        Student student = new Student("fsw", 20, 1);
        filter(student, getPredicateMap());

        Student student1 = new Student("czn", 22, 1);
        filter(student1,getPredicateList());
    }

    /**
     * 获取 predicate列表
     * @return
     */
    public List<Predicate<Student>> getPredicateList(){
        List<Predicate<Student>> list = new ArrayList();
        list.add(student -> student.age > DEFAULT_AGE);
        list.add(student -> student.sex == 1);

        return list;
    }

    /**
     * 获取predicate集合
     * @return
     */
    public Map<String ,Predicate<Student>> getPredicateMap(){
        Map<String, Predicate<Student>> studentMap = new HashMap<>();
        studentMap.put("judge age>20", student -> student.age > 20 && Optional.ofNullable(student.getName()).isPresent());
        studentMap.put("jude sex=1", student -> student.sex == 1 && Optional.ofNullable(student.getName()).isPresent());

        return studentMap;
    }


    public <T> void filter(T t, List<Predicate<T>> list){
        if(list.stream().anyMatch(r -> r.test(t)))
            System.out.println("数据有些匹配");
        else
            System.out.println("数据全不匹配");

        if(list.stream().allMatch(r -> r.test(t)))
            System.out.println("数据全匹配");
        else
            System.out.println("数据不全匹配");
    }

    /**
     * 用predicate来做参数的较验过滤
     * @param t
     * @param map
     * @param <T>
     */
    public <T> void filter(T t, Map<String, Predicate<T>> map){
        map.entrySet().forEach(element -> {
            System.out.println(element);

            Predicate<T> value = element.getValue();
            System.out.println(value);

            System.out.println(element.getKey() + " 判断返回值：" + value.test(t));
        });
    }

    class Student{
        private String name;
        private Integer age;
        private Integer sex;

        public Student(String name, Integer age, Integer sex) {
            this.name = name;
            this.age = age;
            this.sex = sex;
        }

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

        public Integer getSex() {
            return sex;
        }

        public void setSex(Integer sex) {
            this.sex = sex;
        }
    }
}
