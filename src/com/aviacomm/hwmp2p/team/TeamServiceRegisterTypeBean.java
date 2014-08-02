package com.aviacomm.hwmp2p.team;

import java.util.Random;

public class TeamServiceRegisterTypeBean {
	public String action = "";
	public String gsignal = "";

	
	public TeamServiceRegisterTypeBean(String registerType) {
		decode(registerType);
	}

	public TeamServiceRegisterTypeBean(String action, String gsignal) {
		this.action = action;
		this.gsignal = gsignal;
	}
	public boolean decode(String registerType) {
		String[] strs = registerType.split("\\.");
		action = strs[0].trim();
		if (action.equalsIgnoreCase("createTeam")) {
			if (strs.length < 2)
				return false;
			gsignal = strs[1].trim();
		}
		return true;
	}
	private static String generateGsignal(){
		String gsianal="";
		Random random=new Random();
		for(int i=0;i<10;i++){
			gsianal+=random.nextInt()%10;
		}
		return gsianal;
	}
	public String getGsignal(){
		return gsignal;
	}
	public final static int CREATETEAM =1;
	public static TeamServiceRegisterTypeBean newInstance(int type,String gsignal){
		if(type==CREATETEAM){
			if(gsignal==null)
				return new TeamServiceRegisterTypeBean("createTeam", generateGsignal());
			else
				return new TeamServiceRegisterTypeBean("createTeam", gsignal);
		}
		return null;
	}
	public String code() {
		String registerType = "";
		if(isCreateTeam())
			registerType = action + "." + gsignal;
		return registerType;
	}

	public String toString() {
		return code();
	}

	public boolean isCreateTeam() {
		return action.equalsIgnoreCase("createTeam");
	}
}
