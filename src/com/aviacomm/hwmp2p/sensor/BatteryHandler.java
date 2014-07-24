package com.aviacomm.hwmp2p.sensor;

import com.aviacomm.hwmp2p.MessageEnum;

import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Handler;

public class BatteryHandler {
	Handler handler;
	static int state;
	private final int RUN = 1;
	private final int STOP = 2;
	BatteryBroadcastReceiver broadcastReceiver;
	Context context;

	IntentFilter filter = new IntentFilter();

	public BatteryHandler(Context context, Handler handler) {
		this.context = context;
		this.handler = handler;
		broadcastReceiver = new BatteryBroadcastReceiver();
		filter.addAction(Intent.ACTION_BATTERY_CHANGED);
	}

	public void start() {
		state = RUN;
		context.registerReceiver(broadcastReceiver, filter);

	}
	public void stop() {
		state = STOP;
		context.unregisterReceiver(broadcastReceiver);
	}

	public class BatteryBroadcastReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context arg0, Intent intent) {
			int rawlevel = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
			int scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
			int level = rawlevel * 100 / scale;
			handler.obtainMessage(MessageEnum.BATTERYCHANGE, level, 0)
					.sendToTarget();
		}
	}
}
