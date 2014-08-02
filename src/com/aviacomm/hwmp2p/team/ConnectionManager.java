package com.aviacomm.hwmp2p.team;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.aviacomm.hwmp2p.HWMP2PClient;
import com.aviacomm.hwmp2p.MessageEnum;
import com.aviacomm.hwmp2p.uitl.WifiDirectConnectionUitl;
import com.ll.wd250util.TeamServiceBean;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.NetworkInfo;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pGroup;
import android.net.wifi.p2p.WifiP2pInfo;
import android.net.wifi.p2p.WifiP2pManager;
import android.net.wifi.p2p.WifiP2pManager.ActionListener;
import android.net.wifi.p2p.WifiP2pManager.Channel;
import android.net.wifi.p2p.WifiP2pManager.ConnectionInfoListener;
import android.net.wifi.p2p.WifiP2pManager.DnsSdServiceResponseListener;
import android.net.wifi.p2p.WifiP2pManager.DnsSdTxtRecordListener;
import android.net.wifi.p2p.WifiP2pManager.GroupInfoListener;
import android.net.wifi.p2p.nsd.WifiP2pDnsSdServiceInfo;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

public class ConnectionManager implements GroupInfoListener {
	ConnectionListener listener;
	public static boolean backupAp = false;
	private Channel mChannel;
	private WifiP2pManager mWifiP2pManager;
	private TeamManager teamManager;
	public static final String TXTRECORD_PROP_AVAILABLE = "available";
	public static final String SERVICE_INSTANCE = "hwmp2pclient";
	Activity mainActivity;
	WiFiDirectBroadcastReceiver receiver;
	private DnsSdServiceResponseListener dnsSdServiceResponseListener;
	private DnsSdTxtRecordListener dnsSdTxtRecordListener;
	public final String TAG = "ConnectionManager";
	Handler handler;
	List<MWifiDirectAP> aplist;

	private boolean mWifiP2pEnabled;
	private boolean mWifiP2pSearching;
	private int mConnectedDevices;
	private WifiP2pGroup mConnectedGroup;
	private boolean mLastGroupFormed = false;
	private WifiP2pDevice mThisDevice;
	public int SERVER_PORT;
	public String nickname;
	public ConnectionManager(Activity activity, ConnectionListener listener,
			Handler handler) {
		this.mainActivity = activity;
		this.listener = listener;
		this.handler = handler;
	}
	
	// ！！！！ you must invoke initial function before any usage.
	public void initial() {
		aplist = new ArrayList<ConnectionManager.MWifiDirectAP>();
		mWifiP2pManager = (WifiP2pManager) mainActivity
				.getSystemService(Context.WIFI_P2P_SERVICE);
		mChannel = mWifiP2pManager.initialize(mainActivity,
				mainActivity.getMainLooper(), null);
		teamManager = new TeamManager();
		receiver = new WiFiDirectBroadcastReceiver();
		mainActivity.registerReceiver(receiver, receiver.getWifiDirectFilter());
		dnsSdServiceResponseListener = new DnsSdServiceResponseListener() {
			@Override
			public void onDnsSdServiceAvailable(String instanceName,
					String registrationType, WifiP2pDevice srcDevice) {
				// A service has been discovered. Is this our app?
				if (instanceName.equalsIgnoreCase(SERVICE_INSTANCE)) {
					Log.i(TAG, "servicefind");
					TeamServiceRegisterTypeBean tmpTeambean = new TeamServiceRegisterTypeBean(
							registrationType);
					if (tmpTeambean.isCreateTeam()) {
						handler.obtainMessage(
								MessageEnum.WIFIAPDISCOVED,
								new Object[] { srcDevice,
										tmpTeambean.getGsignal() });
						if (teamManager.isReformedAp(tmpTeambean.gsignal)) {
							connect(srcDevice, tmpTeambean.gsignal);
						}
					}
				}
			}
		};

		dnsSdTxtRecordListener = new DnsSdTxtRecordListener() {
			@Override
			public void onDnsSdTxtRecordAvailable(String arg0,
					Map<String, String> arg1, WifiP2pDevice arg2) {

			}
		};

	}

	public void connect(WifiP2pDevice srcDevice, String gsignal) {

	}

	public boolean isConnected() {
		return true;
	}

	public void discoverTeamService() {

	}
	
	public void createTeamService() {
		Map<String,String> record = new HashMap<String,String>();
		SERVER_PORT=WifiDirectConnectionUitl.getAvailablePort();
		record.put("action", "CREATETEAM");
		record.put("gsignal", WifiDirectConnectionUitl.generateGsignal());
        record.put("listenport", String.valueOf(SERVER_PORT));
        record.put("nickname", nickname==null?("FireFighter" + (int) (Math.random() * 1000)):nickname);
        record.put("available", "visible");
		
		mWifiP2pManager.clearLocalServices(mChannel, new ActionListener() {
			@Override
			public void onSuccess() {
				Log.i(TAG, "removeok");
			}

			@Override
			public void onFailure(int error) {
				Log.i(TAG, "removewrong" + error);
			}
		});
		WifiP2pDnsSdServiceInfo addteamservice = WifiP2pDnsSdServiceInfo.newInstance(SERVICE_INSTANCE,
				tmpTeambean.toString(), record);
		manager.addLocalService(channel, addteamservice, new ActionListener() {
			@Override
			public void onSuccess() {
				Log.i(TAG, "createTeamok");
			}

			@Override
			public void onFailure(int error) {
				Log.i(TAG, "createTeamWWWW");
			}
		});
	}

	private void onConnectionEstablished() {

	}

	private void onConnectionBreaken() {

	}

	public interface ConnectionListener {
		public void onConnectionEstablished();

		public void onConnectionFailed();
	}

	private class WiFiDirectBroadcastReceiver extends BroadcastReceiver {
		public WiFiDirectBroadcastReceiver() {
			intentFilter
					.addAction(WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION);
			intentFilter
					.addAction(WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION);
			intentFilter
					.addAction(WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION);
			intentFilter
					.addAction(WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION);
		}

		IntentFilter intentFilter = new IntentFilter();

		public IntentFilter getWifiDirectFilter() {
			return intentFilter;
		}

		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			if (WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION.equals(action)) {
				mWifiP2pEnabled = intent.getIntExtra(
						WifiP2pManager.EXTRA_WIFI_STATE,
						WifiP2pManager.WIFI_P2P_STATE_DISABLED) == WifiP2pManager.WIFI_P2P_STATE_ENABLED;
				handleP2pStateChanged();
			} else if (WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION
					.equals(action)) {
				if (mWifiP2pManager == null)
					return;
				NetworkInfo networkInfo = (NetworkInfo) intent
						.getParcelableExtra(WifiP2pManager.EXTRA_NETWORK_INFO);
				WifiP2pInfo wifip2pinfo = (WifiP2pInfo) intent
						.getParcelableExtra(WifiP2pManager.EXTRA_WIFI_P2P_INFO);
				if (mWifiP2pManager != null) {
					mWifiP2pManager.requestGroupInfo(mChannel,
							ConnectionManager.this);
				}
				mLastGroupFormed = wifip2pinfo.groupFormed;
				if (networkInfo.isConnected()) {
					Log.d(TAG, "Connected");
				} else if (mLastGroupFormed != true) {
					// we are disconnected
					Log.d(TAG, "Disconnected");
				}
			} else if (WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION
					.equals(action)) {
				mThisDevice = (WifiP2pDevice) intent
						.getParcelableExtra(WifiP2pManager.EXTRA_WIFI_P2P_DEVICE);
				Log.d(TAG, "Update device info: " + mThisDevice);
				// device state changed
			} else if (WifiP2pManager.WIFI_P2P_DISCOVERY_CHANGED_ACTION
					.equals(action)) {
				int discoveryState = intent.getIntExtra(
						WifiP2pManager.EXTRA_DISCOVERY_STATE,
						WifiP2pManager.WIFI_P2P_DISCOVERY_STOPPED);
				Log.d(TAG, "Discovery state changed: " + discoveryState);
				if (discoveryState == WifiP2pManager.WIFI_P2P_DISCOVERY_STARTED) {
					Toast.makeText(context, "discoverstarted",
							Toast.LENGTH_SHORT).show();
				} else {
					Toast.makeText(context, "discoverend", Toast.LENGTH_SHORT)
							.show();
				}
			}
		}
		/*
		 * @Override public void onReceive(Context context, Intent intent) { //
		 * TODO Auto-generated method stub String action = intent.getAction();
		 * 
		 * if (WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION
		 * .equals(action)) { NetworkInfo networkInfo = (NetworkInfo) intent
		 * .getParcelableExtra(WifiP2pManager.EXTRA_NETWORK_INFO);
		 * 
		 * if (networkInfo.isConnected()) { // we are connected with the other
		 * device, request // connection // info to find group owner IP //
		 * Log.d(Wd250Demo.TAG, //
		 * "Connected to p2p network. Requesting network details"); //
		 * manager.requestConnectionInfo(); //
		 * manager.requestConnectionInfo(channel, // (ConnectionInfoListener)
		 * activity); onConnectionEstablished(); } else { // It's a disconnect
		 * // manager.disconnect(); onConnectionBreaken(); } } else if
		 * (WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION .equals(action))
		 * { WifiP2pDevice device = (WifiP2pDevice) intent
		 * .getParcelableExtra(WifiP2pManager.EXTRA_WIFI_P2P_DEVICE);
		 * HWMP2PClient.log.i(TAG, "My P2P Status is" +
		 * WifiDirectUitl.transferWifiDeviceStatus(device.status)); } }
		 */
	}

	class MWifiDirectAP {
		WifiP2pDevice device;
		String gsignal;
		String nickname;
		int listenPort;

		@Override
		public boolean equals(Object o) {
			return device.deviceAddress
					.equalsIgnoreCase(((WifiP2pDevice) o).deviceAddress);
		}
	}

	private void handleP2pStateChanged() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onGroupInfoAvailable(WifiP2pGroup arg0) {
		// TODO Auto-generated method stub

	}
}
