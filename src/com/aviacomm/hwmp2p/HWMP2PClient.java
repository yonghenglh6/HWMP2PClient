package com.aviacomm.hwmp2p;

import com.aviacomm.hwmp2p.sensor.SensorManager;
import com.aviacomm.hwmp2p.team.ConnectionManager;
import com.aviacomm.hwmp2p.ui.ActionPageFragment;
import com.aviacomm.hwmp2p.ui.DisplayPageFragment;
import com.aviacomm.hwmp2p.ui.MainPageFragment;
import com.aviacomm.hwmp2p.ui.MainPageFragment.MainPageListener;
import com.aviacomm.hwmp2p.ui.StartPageFragment;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Layout;
import android.util.AttributeSet;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

public class HWMP2PClient extends Activity implements
		ConnectionManager.ConnectionListener, Handler.Callback,
		MainPageListener {

	public static MLog log;
	// StartPageFragment startpageFragment;
	ViewGroup rootContent;
	// ViewGroup displayContent;
	// ViewGroup actionContent;
	MainPageFragment mainpageFragment;
	DisplayPageFragment displaypageFragment;
	ActionPageFragment actionpageFragment;
	ConnectionManager cmanager;
	Handler handler = new Handler(this);
	Fragment currentFragmentInRootContent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_hwmp2_pclient);
		log = new MLog((TextView) this.findViewById(R.id.stateText));

		cmanager = new ConnectionManager(this, this);
		SensorManager sensormanager = new SensorManager(this, getHandler());
		
		
		//init three fragment in the main activity
		mainpageFragment = new MainPageFragment(cmanager, this);
		displaypageFragment = new DisplayPageFragment(this);
		actionpageFragment = new ActionPageFragment(this);
		
		
		rootContent = (ViewGroup) findViewById(R.id.rootContent);
		showSingleFragmentInRootContent(mainpageFragment);
		getFragmentManager().beginTransaction()
				.add(R.id.displayContent, displaypageFragment).commit();
		getFragmentManager().beginTransaction()
				.add(R.id.actionContent, actionpageFragment).commit();
		
		
		
		
		//start Sensor
		// startpageFragment = new StartPageFragment(this);
		sensormanager.start();

		// show startPage
		// showSingleFragmentInRootContent(startpageFragment);
	}

	public void onConnectionEstablished() {
		// getFragmentManager().beginTransaction().remove(startpageFragment)
		// .add(R.id.rootContent, mainpageFragment).commit();
	}

	public void showSingleFragmentInRootContent(Fragment page) {
		FragmentTransaction transaction = getFragmentManager()
				.beginTransaction();
		if (currentFragmentInRootContent != null)
			transaction.remove(currentFragmentInRootContent);
		transaction.add(R.id.rootContent, page);
		transaction.commit();
		currentFragmentInRootContent = page;
	}

	public void onClickSetting() {

	}

	@Override
	public void onConnectionFailed() {
		// TODO Auto-generated method stub
	}

	public Handler getHandler() {
		return handler;
	}

	@Override
	public boolean handleMessage(Message msg) {
		// TODO Auto-generated method stub
		switch (msg.what) {
		case MessageEnum.BATTERYCHANGE:
		case MessageEnum.VOLUMECHANGE:
		case MessageEnum.ORIENTATIONCHANGE:
		case MessageEnum.WIFIINTENSITYCHANGE:
			mainpageFragment.handleMessage(msg);
			break;
		default:
			break;
		}
		return false;
	}

	@Override
	public void onClickBack() {
		// TODO Auto-generated method stub

	}
}
