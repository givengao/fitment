package com.zxyun.order.util;

import java.util.concurrent.TimeUnit;

/**
 * @des:
 * @Author: given
 * @Date 2019/11/4 12:03
 */
public class Voliate {
    private static int MAX_VALUE = 5;
    private static volatile int init_value = 0;

    public static void main(String[] args) {
        new Thread(()->{
            int localValue = init_value;
            while (localValue <= MAX_VALUE){
                if(init_value != localValue){
                    System.out.println("the init_value is updated : " + init_value);
                    localValue = init_value;
                }
            }
        },"Reader").start();

        new Thread(()->{
            int localValue = init_value;
            while (localValue <= MAX_VALUE){

                System.out.println("the init_value is changed : " + localValue ++);
                init_value = localValue;

                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"Updater").start();
    }
}
