package com.aviacomm.hwmp2p.sensor;

import com.aviacomm.hwmp2p.HWMP2PClient;
import com.aviacomm.hwmp2p.MessageEnum;
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
	
	
	//Task will be excused circlely.
	@Override
	public void oneTask() {
		// TODO Auto-generated method stub
		int rssi=wifi_service.getConnectionInfo().getRssi();
		int level=0;
		if (rssi <= 0 && rssi >= -50) {
			level = 4;
		} else if (rssi < -50 && rssi >= -70) {
			level = 3;
		} else if (rssi < -70 && rssi >= -80) {
			level = 2;
		} else if (rssi < -80 && rssi >= -100) {
			level = 1;
		} 
		HWMP2PClient.log.i("wifi level is"+level);
		handler.obtainMessage(MessageEnum.WIFIINTENSITYCHANGE,level,0).sendToTarget();
	}
}
