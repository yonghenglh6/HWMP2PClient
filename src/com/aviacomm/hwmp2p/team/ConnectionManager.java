package com.aviacomm.hwmp2p.team;

public class ConnectionManager {
	ConnectionListener listener;
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
