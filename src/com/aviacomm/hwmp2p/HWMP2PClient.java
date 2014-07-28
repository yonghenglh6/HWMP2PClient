package com.aviacomm.hwmp2p;

import com.aviacomm.hwmp2p.sensor.SensorManager;
import com.aviacomm.hwmp2p.team.ConnectionManager;
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
import android.util.AttributeSet;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

public class HWMP2PClient extends Activity implements
		StartPageFragment.StartPageListener,
		ConnectionManager.ConnectionListener, Handler.Callback,
		MainPageListener {
	
	public static MLog log;
	StartPageFragment startpageFragment;
	ViewGroup rootContent;
	MainPageFragment mainpageFragment;
	ConnectionManager cmanager;
	Handler handler = new Handler(this);
	Fragment currentFragmentInRootContent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_hwmp2_pclient);
		log = new MLog((TextView) this.findViewById(R.id.stateText));
		
		cmanager = new ConnectionManager(this);
		SensorManager sensormanager = new SensorManager(this, getHandler());
		rootContent = (ViewGroup) findViewById(R.id.rootContent);
		startpageFragment = new StartPageFragment(this);
		mainpageFragment = new MainPageFragment(cmanager, this);
		sensormanager.start();

		// show startPage
		showSingleFragmentInRootContent(startpageFragment);
	}

	public void onConnectionEstablished() {
		getFragmentManager().beginTransaction().remove(startpageFragment)
				.add(R.id.rootContent, mainpageFragment).commit();
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

	public void onClickBack() {
		showSingleFragmentInRootContent(startpageFragment);
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
			mainpageFragment.handleMessage(msg);
			break;
		default:
			break;
		}
		return false;
	}

	@Override
	public void onClickConnect() {
		showSingleFragmentInRootContent(mainpageFragment);
	}
}
