package com.aviacomm.hwmp2p.team;

public class TeamManager {
	public GROUPSTATE groupstate=GROUPSTATE.DISSOCIATE;
	
	enum GROUPSTATE{
		DISSOCIATE,
		JOINED,
		LEADER,
		BEOKEN
	}
	String currentGsignal;
	
	
	
	public boolean isBroken(){
		return groupstate==GROUPSTATE.BEOKEN;
	}
	
	public boolean isReformedAp(String anotherGsignal){
		return  groupstate==GROUPSTATE.BEOKEN&&anotherGsignal.equals(currentGsignal);
	}
}
