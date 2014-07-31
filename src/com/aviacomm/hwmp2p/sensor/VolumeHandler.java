package com.aviacomm.hwmp2p.sensor;

import com.aviacomm.hwmp2p.MessageEnum;

import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.os.BatteryManager;
import android.os.Handler;

public class VolumeHandler implements Runnable {
	Handler handler;
	static int state;
	private final int RUN = 1;
	private final int STOP = 2;
	Context context;
	Thread thread;
//	IntentFilter filter = new IntentFilter();

	public VolumeHandler(Context context, Handler handler) {
		this.context = context;
		this.handler = handler;
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
		AudioManager mAudioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
		while(state==RUN){
			try {			
			    int max = mAudioManager.getStreamMaxVolume( AudioManager.STREAM_RING  ); 
			    int current = mAudioManager.getStreamVolume( AudioManager.STREAM_RING  ); 
			    int level = current * 100 / max;
				handler.obtainMessage(MessageEnum.VOLUMECHANGE, level, 0)
				.sendToTarget();
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				state=STOP;
				e.printStackTrace();
			}
		}
		
	}
}
