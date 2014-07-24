package com.aviacomm.hwmp2p;

import android.util.Log;
import android.widget.TextView;

public class MLog {
	TextView statusText;
	public MLog(TextView statusText){
		this.statusText=statusText;
	}
	public void i(String tag,String msg){
		Log.i(tag, msg);
		if(statusText!=null)
			statusText.append(tag+":"+msg);
	}

	public void i(String msg){
		Log.i("default", msg);
		if(statusText!=null)
			statusText.append(msg);
	}
}
