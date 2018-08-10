package com.chengzi.framework;

import com.chengzi.framework.services.LuaScriptService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by fanshaowei on 2018-8-6.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class LuaScriptTest {
    @Autowired
    LuaScriptService luaScriptService;

    @Test
    public void luaScriptTest(){
        luaScriptService.redisAddScriptExec();
    }
}
