package com.itwllbs.aop;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class CalcTest {

	public static void main(String[] args) {
		
		ApplicationContext CTX = new ClassPathXmlApplicationContext("aop.xml");
		
		Calculator c = (Calculator)CTX.getBean("proxyCal");
		
		c.add(100, 200);
		

	}

}
