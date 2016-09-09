package com.bctc.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import com.bctc.entity.Average;
import com.bctc.entity.Behavior;
import com.bctc.entity.Proportion;
import com.bctc.entity.Quarter;
import com.bctc.entity.User;
import com.bctc.entity.Year;
import com.bctc.service.AverageService;
import com.bctc.service.QuarterService;
import com.bctc.service.UserService;
import com.bctc.service.YearService;
import com.bctc.tool.MySession;

public class YearAction extends SuperAction{

	private static final long serialVersionUID = 1L;
	private QuarterService quarterService;
	private YearService yearService;
	private UserService userService;
	private AverageService averageService;
	private List<Quarter> quarterList=new ArrayList<Quarter>();
	private List<Year> yearList;
	private Behavior behavior;
	private Year year=new Year();

	/**
	 *查看年度列表
	 */
	public String list(){
		yearList=yearService.list();
		return "year_list";
	}
	public String toAdd(){
		quarterList=quarterService.list();
		return "add_year";
	}
	/**
	 * 添加年度
	 * @return 
	 */
	public String add(){
		Long yid=yearService.save(year);
		year.setYid(yid);
		if(quarterList==null||quarterList.size()==0){
			this.addFieldError("error", "请选择年度");
			return ERROR;
		}
		//获取季度中的评分比例
		Map<String, Double> map=new HashMap<String, Double>();
		Set<Quarter> quarters=new HashSet<Quarter>();
		for (Quarter quarter : quarterList) {
			quarter=quarterService.load(quarter.getQid());
			quarters.add(quarter);
			for (Proportion proportion : quarter.getProportions()) {
				double value=0.0;
				String key=proportion.getAimUser().getUid()+"-"+proportion.getSourceUser().getUid();
				if(map.get(key)!=null){
					value=map.get(key)+proportion.getProportion();
				}else {
					value=proportion.getProportion();
				}
				map.put(key,value);
			}
			
		}
		year.setQuarters(quarters);	
		yearService.save(year);			//保存本年度
		//计算年度平均评分比例
		int size=quarterList.size();
		  for (Map.Entry<String, Double> entry : map.entrySet()) {
			  String[] strs=entry.getKey().split("-");
			  long aimUid=Long.parseLong(strs[0]);
			  User aimUser=userService.load(aimUid);
			  long sourceUid=Long.parseLong(strs[1]);
			  User sourceUser=userService.load(sourceUid);
			  Average a=new Average(entry.getValue()/size, year, aimUser, sourceUser);
			  averageService.save(a);
		  }
		//拷贝上年的行为绩效表
		  yearList=yearService.list();
		  if(yearList!=null&&yearList.size()>1){	//不是第一年
				Year lastYear=yearService.list().get(1);
				System.out.println("lastyear"+lastYear.getName());
				if(lastYear.getBehaviors()!=null){
					for(Behavior be:lastYear.getBehaviors()){
						Behavior behavior=new Behavior(be.getName(),be.getStandard(), year);
						yearService.saveBehavior(behavior);
					}
				}
			}
		session.setAttribute(MySession.YEAR, year);
		return list();
	}
	/**
	 * 查看行为绩效列表
	 */
	public String toBehavior(){
		year=yearService.load(year.getYid());
		return "behavior_list";
	}
	public String addBehavior(){
		year=yearService.load(year.getYid());
		behavior.setYear(year);
		yearService.saveBehavior(behavior);
		return toBehavior();
	}
	
	public String delete(){
		yearService.delete(year);
		return list();
	}
	public String deleteBehavior(){
		year=behavior.getYear();
		yearService.delete(behavior);
		return toBehavior();
	}
	
	
	//////////////////////////////////////////////
	
	public QuarterService getQuarterService() {
		return quarterService;
	}
	
	public AverageService getAverageService() {
		return averageService;
	}
	@Resource
	public void setAverageService(AverageService averageService) {
		this.averageService = averageService;
	}
	public UserService getUserService() {
		return userService;
	}
	@Resource
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	@Resource
	public void setQuarterService(QuarterService quarterService) {
		this.quarterService = quarterService;
	}
	public List<Quarter> getQuarterList() {
		return quarterList;
	}
	public void setQuarterList(List<Quarter> quarterList) {
		this.quarterList = quarterList;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public YearService getYearService() {
		return yearService;
	}
	@Resource
	public void setYearService(YearService yearService) {
		this.yearService = yearService;
	}
	public Year getYear() {
		return year;
	}
	public void setYear(Year year) {
		this.year = year;
	}
	public List<Year> getYearList() {
		return yearList;
	}
	public void setYearList(List<Year> yearList) {
		this.yearList = yearList;
	}
	public Behavior getBehavior() {
		return behavior;
	}
	public void setBehavior(Behavior behavior) {
		this.behavior = behavior;
	}
	
	
}
