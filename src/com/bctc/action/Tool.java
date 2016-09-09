package com.bctc.action;

import javax.annotation.Resource;
import com.bctc.entity.Quarter;
import com.bctc.entity.User;
import com.bctc.entity.Year;
import com.bctc.service.YearService;
import com.bctc.tool.MySession;

public class Tool extends SuperAction {
	private static final long serialVersionUID = 1L;
	private static YearService yearService =new YearService();
	public Tool(){
		System.out.println("new Tool ");
	}
	/**
	 * 判断是不是管理员
	 */
	public static  boolean isAdmin(){
		User user=(User) session.getAttribute(MySession.LOGINUSER);
		if(user.getPowerGroup().getPowerString().contains("A1")){
			return true;
		}
		return false;
	}
	/**
	 * 判断是不是部门经理
	 */
	public static boolean isDepartment(){
		User user=(User) session.getAttribute(MySession.LOGINUSER);
		if(user.getPowerGroup().getPowerString().contains("A2")||user.getPowerGroup().getPowerString().contains("A3")){
			return true;
		}
		return false;
	}
	public static boolean canQuarterResult(){
		if(getUser().getPowerGroup().getPowerString().contains("D2")){
			return true;
		}
		return false;
	}
	public static boolean canYearResult(){
		if(getUser().getPowerGroup().getPowerString().contains("C4")){
			return true;
		}
		return false;
	}
	public static boolean havePower(String power){
		if(getUser().getPowerGroup().getPowerString().contains(power)){
			return true;
		}
		return false;
	}
	/**
	 * 获得登录的用户
	 * @return	登录 的用户
	 */
	public static User getUser(){
		return (User) session.getAttribute(MySession.LOGINUSER);
	}
	/**
	 * 获取当前季度
	 * @return 当前季度
	 */
	public static Quarter getQuarter(){
		return (Quarter) session.getAttribute(MySession.QUARTER);
	}
	public static Year getYear() {
		return (Year) session.getAttribute(MySession.YEAR);
	}
	public YearService getYearService() {
		return yearService;
	}
	@Resource
	public void setYearService(YearService yearService) {
		this.yearService = yearService;
	}
	
}
