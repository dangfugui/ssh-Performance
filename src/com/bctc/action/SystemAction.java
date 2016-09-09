package com.bctc.action;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import com.bctc.service.SystemService;

public class SystemAction extends SuperAction {
	private static final long serialVersionUID = 1L;
	private SystemService systemService;
	private int total;
	private int subsystem;
	private Map<String, String> menuMap=new HashMap<String, String>();
	/**
	 * 设置显示菜单  控制流程
	 */
	public String setMenu(){
		application.setAttribute("proportion", "hide");
		application.setAttribute("fill", "hide");
		application.setAttribute("quarter", "hide");
		application.setAttribute("year", "hide");
		application.setAttribute("my", "hide");
		for (Map.Entry<String, String> entry : menuMap.entrySet()) {
			application.setAttribute(entry.getKey(), entry.getValue());
		}
		this.addActionMessage("设置成功");
		return SUCCESS;
	}
	/**
	 * 设置子系统
	 */
	public String setSubsystem(){
		System.out.println(total+"-"+subsystem);
		systemService.setSubsystem(total,subsystem);
		this.addActionMessage("设置成功");
		return SUCCESS;
	}
	
	public Map<String, String> getMenuMap() {
		return menuMap;
	}
	public void setMenuMap(Map<String, String> menuMap) {
		this.menuMap = menuMap;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public int getSubsystem() {
		return subsystem;
	}
	public void setSubsystem(int subsystem) {
		this.subsystem = subsystem;
	}
	public SystemService getSystemService() {
		return systemService;
	}
	@Resource
	public void setSystemService(SystemService systemService) {
		this.systemService = systemService;
	}
	
}
