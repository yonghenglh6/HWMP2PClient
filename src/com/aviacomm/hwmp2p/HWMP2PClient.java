package com.aviacomm.hwmp2p;

import com.aviacomm.hwmp2p.sensor.SensorManager;
import com.aviacomm.hwmp2p.team.ConnectionManager;
import com.aviacomm.hwmp2p.ui.APSelectorFragment;
import com.aviacomm.hwmp2p.ui.ActionPageFragment;
import com.aviacomm.hwmp2p.ui.DisplayPageFragment;
import com.aviacomm.hwmp2p.ui.MainPageFragment;
import com.aviacomm.hwmp2p.ui.MainPageFragment.MainPageListener;
import com.aviacomm.hwmp2p.ui.StartPageFragment;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.net.wifi.WifiManager;
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
import android.widget.Toast;

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
	public static Handler handler ;
	Fragment currentFragmentInRootContent;
	APSelectorFragment apSelectorFragment;
	TextView statusView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_hwmp2_pclient);
		handler= new Handler(this);
		log = new MLog(handler);
		statusView=(TextView) this.findViewById(R.id.stateText);
		cmanager = new ConnectionManager(this,this,handler);
		SensorManager sensormanager = new SensorManager(this, getHandler());
		
		
		//init three fragment in the main activity
		mainpageFragment = new MainPageFragment(cmanager, this);
		displaypageFragment = new DisplayPageFragment(this);
		actionpageFragment = new ActionPageFragment(this);
		apSelectorFragment=new APSelectorFragment(this);
		
		rootContent = (ViewGroup) findViewById(R.id.rootContent);
		showSingleFragmentInRootContent(mainpageFragment);
		getFragmentManager().beginTransaction()
				.add(R.id.displayContent, displaypageFragment).commit();
		getFragmentManager().beginTransaction()
				.add(R.id.actionContent, actionpageFragment).commit();
		
		
		
		
		//start Sensor
		// startpageFragment = new StartPageFragment(this);
		sensormanager.startall();

		// show startPage
		// showSingleFragmentInRootContent(startpageFragment);
	}

	public void onConnectionEstablished() {
		// getFragmentManager().beginTransaction().remove(startpageFragment)
		// .add(R.id.rootContent, mainpageFragment).commit();
	}

	private void showSingleFragmentInRootContent(Fragment page) {
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
			
		case MessageEnum.LOGMESSAGE:
			statusView.append((String)msg.obj);
			break;
			 
		default:
			break;
		}
		return false;
	}

	@Override
	public void onClickBack() {
		// TODO Auto-generated method stub
		Toast.makeText(this, "where do you want go back to?", Toast.LENGTH_SHORT ).show();
	}

	@Override
	public void onClickScan() {
		showSingleFragmentInRootContent(apSelectorFragment);
		cmanager.discoverTeamService();
	}

	@Override
	public void onClickCreateTeam() {
		cmanager.createTeamService();
	}
}
