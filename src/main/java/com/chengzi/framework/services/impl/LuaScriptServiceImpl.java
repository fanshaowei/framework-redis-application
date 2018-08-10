package com.chengzi.framework.services.impl;

import com.alibaba.fastjson.JSONObject;
import com.chengzi.framework.services.LuaScriptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.scripting.support.ResourceScriptSource;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.*;

/**
 * Created by fanshaowei on 2018-8-6.
 */
@Service("luaScriptService")
public class LuaScriptServiceImpl implements LuaScriptService{
    @Autowired
    private RedisTemplate<String,Object> redisTemplate1;

    private DefaultRedisScript<Long> addRedisScript;
    private DefaultRedisScript<List> getRedisScript;

    @PostConstruct
    public void init(){
        addRedisScript = new DefaultRedisScript<Long>();
        addRedisScript.setResultType(Long.class);
        addRedisScript.setScriptSource(new ResourceScriptSource(new ClassPathResource("luascript/LimitLoadTimes.lua")));

        getRedisScript = new DefaultRedisScript<List>();
        getRedisScript.setResultType(List.class);
        getRedisScript.setScriptSource(new ResourceScriptSource(new ClassPathResource("luascript/LimitLoadTimes.lua")));
    }

    @Override
    public void redisAddScriptExec(){
        /**
         * List设置lua的KEYS
         */
        List<String> keyList = new ArrayList();
        keyList.add("count");
        keyList.add("rate.limiting:127.0.0.1");

        /**
         * 用Mpa设置Lua的ARGV[1]
         */
        Map<String,Object> argvMap = new HashMap<String,Object>();
        argvMap.put("expire",10000);
        argvMap.put("times",10);

        /**
         * 调用脚本并执行
         */
        List result = redisTemplate1.execute(getRedisScript,keyList, argvMap);
        System.out.println(result);

        /**
         * 解析返回的数据
         */
//        String val = Optional.ofNullable(result)
//                .filter(r -> r.size()>0)
//                .map(r -> result.size())
//                .map(size -> result.get(size-1))
//                .map(map -> ((JSONObject)map).getString("value"))
//                .orElse(null);
//        System.out.println(val);

    }
}
