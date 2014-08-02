package com.aviacomm.hwmp2p.ui;


import com.aviacomm.hwmp2p.R;
import com.aviacomm.hwmp2p.R.id;
import com.aviacomm.hwmp2p.R.layout;
import com.aviacomm.hwmp2p.team.ConnectionManager;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class APSelectorFragment extends Fragment implements Callback {
	private View view;
	Button connect;
	Button cancel;
	RadioGroup apgroup;
	Context context;
	Handler apselectorHandler=new Handler(this);
	public APSelectorFragment(Context context){
		super();
		this.context=context;
	}
	
	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_ap_selector, container, false);
        connect=(Button) view.findViewById(R.id.mainpage_createteam);
        connect=(Button) view.findViewById(R.id.mainpage_scan);
        apgroup=(RadioGroup) view.findViewById(R.id.apGroup);
        return view;
    }
    public Handler  getHandler(){
    	return apselectorHandler;  	
    }
	public boolean handleMessage(Message msg){
		switch (msg.what) {
			
		default:
			break;
		}
		return false;
	}
	public class ApRadioButton extends RadioButton{
		public ApRadioButton(Context context, AttributeSet attrs) {
			super(context, attrs);
		}
	}
}
