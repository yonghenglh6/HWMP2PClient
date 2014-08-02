package com.aviacomm.hwmp2p.sensor;

import android.content.Context;
import android.os.Handler;

public class SensorManager {
	Context context;
	Handler handler;
	BatteryHandler batteryhandler;
	VolumeHandler volumehandler;
	CompassHandler2 compasshandler;
	WifiIntensityHandler wfiIntensityHandler;
	public SensorManager(Context context,Handler handler){
		this.handler=handler;
		this.context=context;
		batteryhandler=new BatteryHandler(context,handler);
		volumehandler=new VolumeHandler(context, handler);
		compasshandler=new CompassHandler2(context, handler);
		wfiIntensityHandler=new WifiIntensityHandler(context, handler);
	}
	public void startall(){
		batteryhandler.start();
		volumehandler.start();
		compasshandler.start();
		wfiIntensityHandler.start();
	}
}
