package com.chengzi.framework.mybatisplugins;

import org.apache.ibatis.executor.resultset.ResultSetHandler;
import org.apache.ibatis.plugin.*;

import java.sql.Statement;
import java.util.*;

/**
 * 将下划线转换成驼峰写法
 */
@Intercepts(
    @Signature(
        type = ResultSetHandler.class,
        method = "handleResultSets",
        args={Statement.class}
    )
)
public class CameHumpInterceptor implements Interceptor {
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        List<Object> list = (List<Object>) invocation.proceed();
        for (Object object: list){
            if(object instanceof  Map){
                processMap((Map<String, Object>) object);
            }else{
                break;
            }
        }
        return list;
    }

    private void processMap(Map<String, Object> map){
        Set<String> keySet = new HashSet<String>(map.keySet());
        for(String key : keySet){
            if((key.charAt(0)>='A'
                && key.charAt(0) <= 'Z')
                || key.indexOf("_") >=0){
                Object value = map.get(key);
                map.remove(key);
                map.put(underlineToCamelhump(key),value);
            }
        }
    }

    public  static String underlineToCamelhump(String inputString){
        StringBuilder sb = new StringBuilder();
        boolean nextUperCase = false;

        for(int i=0; i<inputString.length(); i++){
            char c= inputString.charAt(i);
            if(c=='_'){
                if(sb.length()>0){
                    nextUperCase =true;
                }
            }else{
                if(nextUperCase){
                    sb.append(Character.toUpperCase(c));
                    nextUperCase =false;
                }else {
                    sb.append(Character.toUpperCase(c));
                }
            }
        }
        return sb.toString();
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target,this);
    }

    @Override
    public void setProperties(Properties properties) {

    }
}
