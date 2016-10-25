package com.cheng.bookstore.test;

public class ThreadLocalTest implements Runnable {
	@Override
	public void run() {
		ThreadLocal<String> threadLocal=new ThreadLocal<>();
		threadLocal.set("hehe");
	}

}
