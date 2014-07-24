package com.aviacomm.hwmp2p.sensor;

import android.content.Context;
import android.os.Handler;

public class SensorManager {
	Context context;
	Handler handler;
	BatteryHandler batteryhandler;
	public SensorManager(Context context,Handler handler){
		this.handler=handler;
		this.context=context;
		batteryhandler=new BatteryHandler(context,handler);
	}
	public void start(){
		batteryhandler.start();
	}
}
