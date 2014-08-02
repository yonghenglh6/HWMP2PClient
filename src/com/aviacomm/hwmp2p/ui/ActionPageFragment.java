package com.aviacomm.hwmp2p.ui;

import com.aviacomm.hwmp2p.MessageEnum;
import com.aviacomm.hwmp2p.R;
import com.aviacomm.hwmp2p.R.id;
import com.aviacomm.hwmp2p.R.layout;
import com.aviacomm.hwmp2p.team.ConnectionManager;
import com.aviacomm.hwmp2p.ui.StartPageFragment.StartPageListener;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;

public class ActionPageFragment extends Fragment {
	View view;
	Context context;

	public ActionPageFragment(Context context){
		super();
		this.context=context;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_action, container, false);
//		BatteryView bbiew=new BatteryView(this.getActivity(), null, TRIM_MEMORY_BACKGROUND);
		return view;
	}
	
	public void initModule(){
		
	}
	
	public void handleMessage(Message msg){
		switch (msg.what) {
		
		default:
			break;
		}
	}
}
