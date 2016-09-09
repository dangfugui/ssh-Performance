package com.bctc.action;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import com.bctc.entity.PowerGroup;
import com.bctc.service.PowerGroupService;

public class PowerGroupAction extends SuperAction {

	private static final long serialVersionUID = 1L;
	private PowerGroupService powerGroupService;
	private PowerGroup powerGroup=new PowerGroup();
	private List<String> powerList=new ArrayList<String>();
	private List<PowerGroup> powerGroupList;
	public String list(){
		if(!Tool.havePower("E5")){
			this.addFieldError("error", "权限不足");
			return "error";
		}
		powerGroupList=powerGroupService.list();
		System.out.println(powerGroupList.size());
		return "powerGroup_list";
	}
	
	public String toAddOrUpdate(){
		if(!Tool.havePower("E5")){
			this.addFieldError("error", "权限不足");
			return "error";
		}
		if(powerGroup.getPid()!=0){
			powerGroup=powerGroupService.load(powerGroup.getPid());
		}
		return "powerGroup_toAddOrUpdate";
	}
	public String addOrUpdate(){
		if(!Tool.havePower("E5")){
			this.addFieldError("error", "权限不足");
			return "error";
		}
		String powerString="";
		for (String s:powerList) {
			powerString+=s+",";
		}
		powerGroup.setPowerString(powerString);
		if(powerGroup.getPid()==0){
			powerGroupService.save(powerGroup);
		}else {
			powerGroupService.update(powerGroup);
		}
		return list();
	}
	public String delete(){
		if(!Tool.havePower("E5")){
			this.addFieldError("error", "权限不足");
			return "error";
		}
		powerGroupService.delete(powerGroup);
		return list();
	}
	
	
	
	
	public PowerGroupService getPowerGroupService() {
		return powerGroupService;
	}
	@Resource
	public void setPowerGroupService(PowerGroupService powerGroupService) {
		this.powerGroupService = powerGroupService;
	}

	public PowerGroup getPowerGroup() {
		return powerGroup;
	}
	public void setPowerGroup(PowerGroup powerGroup) {
		this.powerGroup = powerGroup;
	}
	public List<String> getPowerList() {
		return powerList;
	}
	public void setPowerList(List<String> powerList) {
		this.powerList = powerList;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public List<PowerGroup> getPowerGroupList() {
		return powerGroupList;
	}

	public void setPowerGroupList(List<PowerGroup> powerGroupList) {
		this.powerGroupList = powerGroupList;
	}



	
	
}
