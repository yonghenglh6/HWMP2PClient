package com.aviacomm.hwmp2p.ui;

import com.aviacomm.hwmp2p.MessageEnum;
import com.aviacomm.hwmp2p.R;
import com.aviacomm.hwmp2p.R.id;
import com.aviacomm.hwmp2p.R.layout;
import com.aviacomm.hwmp2p.team.ConnectionManager;
import com.aviacomm.hwmp2p.ui.StartPageFragment.StartPageListener;

import android.app.Fragment;
import android.os.Bundle;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;

public class MainPageFragment extends Fragment {
	View view;
	ProgressBar battery;
	ProgressBar volume;
	ImageView compass_pointer;
	MainPageListener listener;
	ConnectionManager cmanager;
	ImageButton back;
	ImageView wifi_intensity;
	public MainPageFragment(ConnectionManager cmanager,MainPageListener listener){
		super();
		this.listener=listener;
		this.cmanager=cmanager;
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_main, container, false);
//		BatteryView bbiew=new BatteryView(this.getActivity(), null, TRIM_MEMORY_BACKGROUND);
		initModule();
		return view;
	}
	
	public void initModule(){
		battery=(ProgressBar) view.findViewById(R.id.batteryProgressBar);
		volume=(ProgressBar) view.findViewById(R.id.volumeProgressBar);
		compass_pointer=(ImageView) view.findViewById(R.id.compass_pointer);
		back=(ImageButton) view.findViewById(R.id.mainpage_back_button);
		back.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				listener.onClickBack();
			}
		});
		wifi_intensity=(ImageView) view.findViewById(R.id.wifiIntensity);
	}
	
	public void handleMessage(Message msg){
		switch (msg.what) {
		case MessageEnum.BATTERYCHANGE:
			if(battery!=null)
				battery.setProgress(msg.arg1);
			break;
		case MessageEnum.VOLUMECHANGE:
			if(volume!=null)
				volume.setProgress(msg.arg1);
			break;
		case MessageEnum.ORIENTATIONCHANGE:
			if(compass_pointer!=null)
				compass_pointer.setRotation(360-(Float) msg.obj);
		default:
			break;
		}
	}
    public interface MainPageListener {
        public void onClickBack();
    }
}
