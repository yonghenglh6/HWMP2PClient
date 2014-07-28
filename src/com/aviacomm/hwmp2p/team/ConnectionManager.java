package com.aviacomm.hwmp2p.team;

import android.app.Activity;
import android.net.wifi.p2p.WifiP2pManager;
import android.net.wifi.p2p.WifiP2pManager.Channel;

public class ConnectionManager {
	ConnectionListener listener;
	public static boolean backupAp=false;
	private Channel channel;
	private WifiP2pManager manager;
	Activity mainActivity;
	String currentGsignal;
	public ConnectionManager(ConnectionListener listener){
		this.listener=listener;
	}
	public void connect(){
		if(isConnected())
			listener.onConnectionEstablished();
	}
	
	
	public boolean isConnected(){
		return true;
	}
	public interface ConnectionListener{
		public void onConnectionEstablished();
		public void onConnectionFailed();
	}
}
