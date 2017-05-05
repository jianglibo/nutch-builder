package com.jianglibo.nutchbuilder.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.junit.Test;

public class TestThreadLocal {
	
	private ThreadLocal<String> tokenHolder = new ThreadLocal<String>() {
		protected String initialValue() {
			return "";
		};
	};

	@Test
	public void t() {
		List<MyTh> ths = new ArrayList<>();
		for(int i=0;i<10;i++) {
			MyTh mt = new MyTh();
			ths.add(mt);
			mt.start();
		}
		
		while(true) {
			long l = ths.stream().filter(t -> t.isAlive()).count();
			if (l == 0) {
				break;
			}
		}
	}
	
	public class MyTh extends Thread {
		@Override
		public void run() {
			tokenHolder.set(new Random().toString());
		}
	}
}
