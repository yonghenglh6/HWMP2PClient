package com.aviacomm.hwmp2p.sensor;

import android.content.Context;
import android.os.Handler;

public class WifiIntensityHandler {
	Handler handler;
	Context context;
	//context是安卓上下文，用于获取系统服务。handler是主程序的消息句柄。使用方式参照VolumeHandler
	public WifiIntensityHandler(Context context, Handler handler) {
		this.context = context;
		this.handler = handler;
	}
}
