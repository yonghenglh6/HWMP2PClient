package com.aviacomm.hwmp2p;

import android.os.Handler;
import android.util.Log;
import android.widget.TextView;

public class MLog {
//	TextView statusText;
	Handler handler;
	public MLog(Handler handler){
		this.handler=handler;
	}
	public void i(String tag,String msg){
		Log.i(tag, msg);
		handler.obtainMessage(MessageEnum.LOGMESSAGE,tag+":"+msg+"\n").sendToTarget();
	}
	public void i(String msg){
		Log.i("default", msg);
		handler.obtainMessage(MessageEnum.LOGMESSAGE,msg+"\n").sendToTarget();
	}
}
