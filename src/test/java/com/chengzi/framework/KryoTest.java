package com.chengzi.framework;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by fanshaowei on 2018-8-13.
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class KryoTest {
    @Test
    public void kryoTest(){
        Kryo kryo = new Kryo();
        String s = "qqqqq";
        Output output = new Output(1024);
        kryo.writeObject(output, s);;
        output.flush();
        output.close();

        byte[] w = output.toBytes();
        Input input = new Input(w);
        input.close();
        String s2 = kryo.readObject(input,String.class);
        System.out.println(s2);
    }
}
