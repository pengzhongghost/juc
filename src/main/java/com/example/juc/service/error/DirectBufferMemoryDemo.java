package com.example.juc.service.error;

import java.nio.ByteBuffer;

/**
 * 在堆外空间创建对象使得内存溢出
 * -XX:MaxDirectMemorySize=4m
 */
public class DirectBufferMemoryDemo {
    public static void main(String[] args) {
        System.out.println("配置的maxDirectMemory：" + (sun.misc.VM.maxDirectMemory() / (double) 1024 / 1024) + "mb");
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(1024 * 1024 * 5);
    }
}
