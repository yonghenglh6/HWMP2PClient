package com.aviacomm.hwmp2p.sensor;

import com.aviacomm.hwmp2p.uitl.CommonSingleCircleThread;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Handler;

public class WifiIntensityHandler extends CommonSingleCircleThread{
	Handler handler;
	Context context;
	Thread thread;
	WifiManager wifi_service;
	//context是安卓上下文，用于获取系统服务。handler是主程序的消息句柄。使用方式参照VolumeHandler
	public WifiIntensityHandler(Context context, Handler handler) {
		super();
		this.context = context;
		this.handler = handler;
		wifi_service=(WifiManager)context.getSystemService(Context.WIFI_SERVICE); 
	}
	
	@Override
	public void oneTask() {
		// TODO Auto-generated method stub
		int rssi=wifi_service.getConnectionInfo().getRssi();
		
	}
}
