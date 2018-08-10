package com.chengzi.framework;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.lang.NonNull;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.function.Predicate;

/**
 * Created by fanshaowei on 2018-8-7.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class LambdaTest {

    @Test
    public void test(){
        /**
         * lambada调用
         */
        Action action = ()-> System.out.println("1");
        action.run();
        action.say();

        /**
         * 泛型传参给Lambada表达式
         */
        Go<String, Integer> go_var = (Integer a) -> {
            System.out.println("the num is:" + a);
           return Integer.toString(a);
        };
        String str_go = go_var.go(8);

        /**
         * 通过::引用对象方法，该对象方法的参数和返回值和接口定义的方法一致
         */
        LambdaTest lambdaTest = new LambdaTest();
        Go<String, Integer> go_var1 = lambdaTest::intToStr;
        go_var1.go(7);

        /**
         * 通过::引用构造函数，这里变量的类型需要接口类型，
         */
        ModelCreateInterface modelCreate = Model::new;
        Model model = modelCreate.createModel("fsw");
        model.test1();

        Model model1 = new Model();
        ModelAction<Model> modelAction = model::test2;
        modelAction.modelActionRun(model);
    }

    public  String intToStr(Integer i){
        System.out.println("use :: to call method,the param is:" + i);
        return Integer.toString(i);
    }

    @FunctionalInterface
    interface Action{
        public void run();

        default void say(){
            System.out.println("Action,i am runing");
        }
    }

    @FunctionalInterface
    interface Go<T,F>{
        public T go(@NonNull F f);
    }

    class Model {
        private String name;

        public Model(String name){
            this.name = name;
        }
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public  Model(){}

        public void test1(){
            System.out.println("test1 run");
        }
        public void test2(Model m){
            System.out.println("test2 run,get model name:" + m.getName());
        }
        public String test3(){
            System.out.println("test3 run");
            return "test3";
        }
    }

    @FunctionalInterface
    interface ModelCreateInterface{
        Model createModel(String name);
    }

    @FunctionalInterface
    interface ModelAction<T>{
        public void modelActionRun(T t);
    }

    public <T> void checkParams(T t, Predicate<T> map){

    }
}
