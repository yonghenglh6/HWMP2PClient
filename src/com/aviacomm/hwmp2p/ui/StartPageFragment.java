package com.aviacomm.hwmp2p.ui;


import com.aviacomm.hwmp2p.R;
import com.aviacomm.hwmp2p.R.id;
import com.aviacomm.hwmp2p.R.layout;
import com.aviacomm.hwmp2p.team.ConnectionManager;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class StartPageFragment extends Fragment {
	private View view;
	StartPageListener listener;
	ConnectionManager cmanager;
	public StartPageFragment(ConnectionManager cmanager,StartPageListener listener){
		super();
		this.listener=listener;
		this.cmanager=cmanager;
		
	}
	
	public StartPageFragment(StartPageListener listener){
		super();
		this.listener=listener;
	}
	
	
	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_startpage, container, false);
        View conenct=view.findViewById(R.id.startpage_connect_button);
        View settings=view.findViewById(R.id.startpage_settings_button);
        conenct.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				listener.onClickConnect();
			}
		});
        settings.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				listener.onClickSetting();
			}
		});
        return view;
    }
    
    
    public interface StartPageListener {
        public void onClickSetting();
        public void onClickConnect();
    }
}
