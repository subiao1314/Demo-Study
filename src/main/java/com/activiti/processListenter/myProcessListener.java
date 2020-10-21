package com.activiti.processListenter;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
import org.springframework.stereotype.Service;

@Service
public class myProcessListener implements ExecutionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7763568996086753354L;

	@Override
	public void notify(DelegateExecution execution) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("start");
		System.out.println("######################################");
	}
	
	public void testListener(){
		System.out.println("my method");
		System.out.println("######################################");
	}

}
