package com.aviacomm.hwmp2p.uitl;

import android.content.Context;
import android.media.AudioManager;
import android.os.Handler;

import com.aviacomm.hwmp2p.MessageEnum;

public class CommonSingleCircleThread implements Runnable{	
	static int state;
	private final int RUN = 1;
	private final int STOP = 2;
	private int interval=1000;
	Thread thread;
	public CommonSingleCircleThread() {
	}
	public void oneTask(){
		 
	}
	public void setInterval(int interval){
		this.interval=interval;
	}
	public int getInterval(){
		return interval;
	}
	public void start() {
		state = RUN;
		if(thread!=null&&thread.isAlive()){
			return;
		}
		thread=new Thread(this);
		thread.start();
	}
	public void stop() {
		state = STOP;
	}


	@Override
	public void run() {
		while(state==RUN){
			try {			
				 oneTask();
				 Thread.sleep(interval);
			} catch (Exception e) {
				state=STOP;
				e.printStackTrace();
			}
		}
	}

}
