package com.aviacomm.hwmp2p.ui;

import com.aviacomm.hwmp2p.MessageEnum;
import com.aviacomm.hwmp2p.R;
import com.aviacomm.hwmp2p.R.id;
import com.aviacomm.hwmp2p.R.layout;

import android.app.Fragment;
import android.os.Bundle;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

public class MainPageFragment extends Fragment {
	View view;
	ProgressBar battery;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_mainpage, container, false);
//		BatteryView bbiew=new BatteryView(this.getActivity(), null, TRIM_MEMORY_BACKGROUND);
		battery=(ProgressBar) view.findViewById(R.id.batteryProgressBar);
		return view;
	}
	
	
	public void handleMessage(Message msg){
		switch (msg.what) {
		case MessageEnum.BATTERYCHANGE:
			if(battery!=null)
				battery.setProgress(msg.arg1);
			break;
		default:
			break;
		}
	}
	
}
