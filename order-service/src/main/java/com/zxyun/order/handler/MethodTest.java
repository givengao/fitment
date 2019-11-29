package com.zxyun.order.handler;

import com.zxyun.order.handler.function.ConsumerMethod;
import com.zxyun.order.handler.invoke.ConsumerMethodInvoker;
import com.zxyun.order.handler.register.MethodRegister;

/**
 * @des:
 * @Author: given
 * @Date 2019/11/29 17:28
 */
public class MethodTest {

    static class Test1 {
        public void test (String s) {
            System.out.println(s);
        }

        public void test1 (String s) {
            int i = 8/0;
            System.out.println(s + "sisiis");
        }
    }

    public static void main (String [] args) {
        Test1 test1 = new Test1();
        MethodRegister<ConsumerMethod<String>> methodMethodRegister = new MethodRegister<>();
        methodMethodRegister.registerMethod("test", (t) -> test1.test(t));
        methodMethodRegister.registerMethod("test1", (t) -> test1.test1(t));
        ConsumerMethodInvoker<String> methodMethodInvoker = new ConsumerMethodInvoker<String>(methodMethodRegister);
        methodMethodInvoker.invoke("test", "wiwiiwiw");
        methodMethodInvoker.syncInvoke("test1", "wiwiiwiw");
    }
}
