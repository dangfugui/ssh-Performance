package com.bctc.action;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.bctc.entity.Average;
import com.bctc.entity.Behavior;
import com.bctc.entity.KPI;
import com.bctc.entity.Quarter;
import com.bctc.entity.ResultYear;
import com.bctc.entity.ScoreYear;
import com.bctc.entity.User;
import com.bctc.entity.Year;
import com.bctc.service.AverageService;
import com.bctc.service.KPIService;
import com.bctc.service.ResultYearService;
import com.bctc.service.ScoreYearService;
import com.bctc.service.YearService;
import com.bctc.tool.Config;
import com.bctc.tool.MySession;

/**
 * 行为技校内容
 *
 */
public class AverageAction extends SuperAction {
	private static final long serialVersionUID = 1L;
	private AverageService averageService;
	private YearService yearService;
	private ScoreYearService scoreYearService;
	private ResultYearService resultYearService;
	private KPIService kpiService;
	private List<Average> averageList;
	private Average average;
	private User user;
	private Year year;
	private Map<Long, Double> behaviorMap=new HashMap<Long, Double>();
	
	/**
	 * 年度打分人员列表
	 */
	public String listUser(){
		user=Tool.getUser();
		year=Tool.getYear();
		if(year!=null){
			averageList=averageService.find(year,user);
			
		}else {
			this.addFieldError("error", "没有创建年度考核");
			return ERROR;
		}
		return "behavior_user_list";
	}
	
	/**
	 * 去打分
	 * @return 
	 */
	public String toScoreYear(){
		average=averageService.load(average.getAid());
		session.setAttribute(MySession.AIMUSER, average.getAimUser());
		year=Tool.getYear();
		year=yearService.load(year.getYid());
		for (Behavior behavior : year.getBehaviors()) {
			ScoreYear scoreYear = scoreYearService.load(behavior, Tool.getUser(), average.getAimUser());
			if(null!=scoreYear){
				behaviorMap.put(behavior.getBid(), scoreYear.getScore());
			}
		}
		return "fill_behavior_list";
	}
	/**
	 * 开始计年度成绩
	 */
	public void calculateYear(){
		System.out.println("开始年度打分");
		year=(Year) session.getAttribute(MySession.YEAR);
		year=yearService.load(year.getYid());
		//计算出所有人给某人打的总分
		for (Average a : year.getAverages()) {
			List<ScoreYear> scores = scoreYearService.find(year,a.getAimUser(),a.getSourceUser());
			double total=0.0;
			for (ScoreYear s : scores) {
				total+=s.getScore();
			}
			a.setTotal(total);
			averageService.update(a);
		}
		for (Average a : year.getAverages()) {
			List<Average> list = averageService.findByAimUser(year, a.getAimUser());
			double total=0.0;
			for (Average average : list) {
				total+=average.getAverage()*average.getTotal();
			}
			double standard=0.0;
			int grade=a.getAimUser().getRole().getGrade();
			if(grade<50){
				for(int i=0;i<Config.senior.length;i++){
					if(Config.senior[i]<=total){
						standard=Config.standard[i];
					}
				}
			}else if(grade<100){
				for(int i=0;i<Config.middle.length;i++){
					if(Config.middle[i]<=total){
						standard=Config.standard[i];
					}
				}
			}else{
				for(int i=0;i<Config.staff.length;i++){
					if(Config.staff[i]<=total){
						standard=Config.standard[i];
					}
				}
			}
			//计算总总总成绩！
			//计算季度平均成绩
			double quarterAll=0.0;
			for(Quarter q:year.getQuarters()){
				KPI kpi=kpiService.load(q, a.getAimUser());
				quarterAll+=kpi.getResult();
			}
			quarterAll=quarterAll/(year.getQuarters().size());//取平均值
			ResultYear resultYear;
			resultYear=resultYearService.load(year, a.getAimUser());
			if(resultYear==null){
				resultYear=new ResultYear(year, a.getAimUser(), standard*Config.year+quarterAll*Config.quarters);
				resultYearService.save(resultYear);
			}else{
				resultYear.setResult(standard*Config.year+quarterAll*Config.quarters);
				resultYearService.save(resultYear);
			}
			
		}
	}

	public AverageService getAverageService() {
		return averageService;
	}
	@Resource
	public void setAverageService(AverageService averageService) {
		this.averageService = averageService;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public List<Average> getAverageList() {
		return averageList;
	}

	public void setAverageList(List<Average> averageList) {
		this.averageList = averageList;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Year getYear() {
		return year;
	}

	public void setYear(Year year) {
		this.year = year;
	}
	

	public Average getAverage() {
		return average;
	}

	public void setAverage(Average average) {
		this.average = average;
	}

	public YearService getYearService() {
		return yearService;
	}
	@Resource
	public void setYearService(YearService yearService) {
		this.yearService = yearService;
	}

	public ScoreYearService getScoreYearService() {
		return scoreYearService;
	}
	@Resource
	public void setScoreYearService(ScoreYearService scoreYearService) {
		this.scoreYearService = scoreYearService;
	}

	public ResultYearService getResultYearService() {
		return resultYearService;
	}
	@Resource
	public void setResultYearService(ResultYearService resultYearService) {
		this.resultYearService = resultYearService;
	}

	public KPIService getKpiService() {
		return kpiService;
	}
	@Resource
	public void setKpiService(KPIService kpiService) {
		this.kpiService = kpiService;
	}

	public Map<Long, Double> getBehaviorMap() {
		return behaviorMap;
	}

	public void setBehaviorMap(Map<Long, Double> behaviorMap) {
		this.behaviorMap = behaviorMap;
	}
	
}
