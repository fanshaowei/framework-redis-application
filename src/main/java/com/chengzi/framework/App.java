package com.chengzi.framework;

import com.chengzi.framework.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.redis.core.RedisTemplate;

import static java.awt.Color.red;

/**
 * Hello world!
 *
 */
@SpringBootApplication
public class App 
{
    public static void main( String[] args ) {
        SpringApplication.run(App.class,args);
    }

}
