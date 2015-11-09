package com.shadow.mytest;

import org.jmock.Expectations;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.jmock.lib.legacy.ClassImposteriser;
import org.junit.Rule;
import org.junit.Test;


public class T1Test   {

	@Rule
	public JUnitRuleMockery context = new JUnitRuleMockery(){
		{
			setImposteriser(ClassImposteriser.INSTANCE);
		}
	};


	@Test
	public void testPrint() {
		final T1 t1=context.mock(T1.class);
		context.checking(new Expectations() {
			{
				oneOf(t1).print();
				will(returnValue("3"));
			}
		});
		System.out.println(	t1.print());

	}

}
