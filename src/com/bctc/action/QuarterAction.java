package com.bctc.action;

import java.util.List;

import javax.annotation.Resource;

import com.bctc.entity.Proportion;
import com.bctc.entity.Quarter;
import com.bctc.service.ProportionService;
import com.bctc.service.QuarterService;
import com.bctc.service.UserService;

public class QuarterAction extends SuperAction {

	
	private static final long serialVersionUID = 1L;
	private QuarterService quarterService;
	private Quarter quarter=new Quarter();
	private ProportionService proportionService;
	List<Quarter> list;
	@Override
	public String execute() throws Exception {
		return list();
	}
	
	public String add(){
		if(!Tool.havePower("C1")){
			this.addFieldError("error", "权限不足");
			return "error";
		}
		if(Tool.isAdmin()){
			quarterService.save(quarter);
			copyProportion();
		}
		return list();
	}
	public String delete(){
		if(!Tool.havePower("C1")){
			this.addFieldError("error", "权限不足");
			return "error";
		}
		if(Tool.isAdmin()){
			quarterService.delete(quarter );
		}
		return list();
	}
	
	public String update(){
		if(!Tool.havePower("C1")){
			this.addFieldError("error", "权限不足");
			return "error";
		}
		if(Tool.isAdmin()){
			quarterService.update(quarter);
		}
		return list();
	}
	public String list(){
		if(!Tool.havePower("C1")){
			this.addFieldError("error", "权限不足");
			return "error";
		}
		list = quarterService.list();
		return "quarter_list";
	}

	
	/**
	 * 拷贝某一季度评分比例   设置为当前季度评分比例
	 */
	public boolean copyProportion(){
		list=quarterService.list();
		if(list.size()<2) return false;	//第一个季度
		quarter=list.get(1);
		Quarter lastQuarter=list.get(0);
			//进行拷贝评分比例
		for (Proportion p:quarter.getProportions()) {
			Proportion proportion=new Proportion(lastQuarter,p.getProportion(),p.getAimUser(),p.getSourceUser());
			proportionService.save(proportion);
		}
		return true;
	}
	
	
	
	public Quarter getQuarter() {
		return quarter;
	}

	public void setQuarter(Quarter quarter) {
		this.quarter = quarter;
	}

	public List<Quarter> getList() {
		return list;
	}

	public void setList(List<Quarter> list) {
		this.list = list;
	}
	@Resource
	public void setQuarterService(QuarterService quarterService) {
		this.quarterService = quarterService;
	}
	@Resource
	public void setUserService(UserService userService) {
	}

	public ProportionService getProportionService() {
		return proportionService;
	}
	@Resource
	public void setProportionService(ProportionService proportionService) {
		this.proportionService = proportionService;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public QuarterService getQuarterService() {
		return quarterService;
	}
	
	
}
