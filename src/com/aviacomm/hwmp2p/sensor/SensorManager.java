package com.aviacomm.hwmp2p.sensor;

import android.content.Context;
import android.os.Handler;

public class SensorManager {
	Context context;
	Handler handler;
	BatteryHandler batteryhandler;
	VolumeHandler volumehandler;
	CompassHandler2 compasshandler;
	public SensorManager(Context context,Handler handler){
		this.handler=handler;
		this.context=context;
		batteryhandler=new BatteryHandler(context,handler);
		volumehandler=new VolumeHandler(context, handler);
		compasshandler=new CompassHandler2(context, handler);
	}
	public void start(){
		batteryhandler.start();
		volumehandler.start();
		compasshandler.start();
	}
}
