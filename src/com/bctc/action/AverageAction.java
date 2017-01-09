package com.bctc.action;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.bctc.entity.Average;
import com.bctc.entity.Behavior;
import com.bctc.entity.KPI;
import com.bctc.entity.Proportion;
import com.bctc.entity.Quarter;
import com.bctc.entity.ResultYear;
import com.bctc.entity.Role;
import com.bctc.entity.ScoreYear;
import com.bctc.entity.User;
import com.bctc.entity.Year;
import com.bctc.service.AverageService;
import com.bctc.service.KPIService;
import com.bctc.service.ResultYearService;
import com.bctc.service.RoleService;
import com.bctc.service.ScoreYearService;
import com.bctc.service.UserService;
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
	@Resource
	private UserService userService;
	private KPIService kpiService;
	private List<Role> roleList;
	@Resource
	private RoleService roleService;
	private List<Average> averageList;
	private Average average;
	private User user;
	private List<User> userList;
	private Year year;
	private Map<Long, Double> behaviorMap=new HashMap<Long, Double>();
	private Map<Long, Double> map=new HashMap<Long, Double>();
	private List<Long> userIDs;
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
			double standard=total;
//			int grade=a.getAimUser().getRole().getGrade();
//			if(grade<50){
//				for(int i=0;i<Config.senior.length;i++){
//					if(Config.senior[i]<=total){
//						standard=Config.standard[i];
//					}
//				}
//			}else if(grade<100){
//				for(int i=0;i<Config.middle.length;i++){
//					if(Config.middle[i]<=total){
//						standard=Config.standard[i];
//					}
//				}
//			}else{
//				for(int i=0;i<Config.staff.length;i++){
//					if(Config.staff[i]<=total){
//						standard=Config.standard[i];
//					}
//				}
//			}
			//计算总总总成绩！
			//计算季度平均成绩
			double quarterAll=0.0;
			double sumProportion=0.0;
			for(Quarter q:year.getQuarters()){
				KPI kpi=kpiService.load(q, a.getAimUser());
				if(kpi!=null){
					quarterAll+=(kpi.getResult()*q.getProportion())/100;//####
					sumProportion+=q.getProportion();
				}
			}
			//quarterAll=quarterAll/(year.getQuarters().size());//取平均值
			ResultYear resultYear;
			resultYear=resultYearService.load(year, a.getAimUser());
			if(resultYear==null){
				resultYear=new ResultYear(year, a.getAimUser(), (standard*(100-sumProportion))/100+quarterAll);
				resultYearService.save(resultYear);
			}else{
				resultYear.setResult((standard*(100-sumProportion))/100+quarterAll);
				resultYearService.save(resultYear);
			}
			
		}
	}
	
	
	/**
	 * 查看某人的评分比例#####
	 * @return 
	 */
	public String lookAverage(){
		if(Tool.havePower("D3")){
			if(user.getUid()==0){
				user=(User) session.getAttribute(MySession.LOGINUSER);
			}else{
				user=userService.load(user.getUid());
			}
		}else if(Tool.havePower("B1")){
			user=(User) session.getAttribute(MySession.LOGINUSER);
		}else{
			this.addFieldError("error", "权限不足");
			return "error";
		}
		year = (Year) session.getAttribute(MySession.YEAR);
		year=yearService.load(year.getYid());
		if(null==year){
			this.addFieldError("error", "还没有创建年度");
			return "error";
		}
		averageList = averageService.find(year, user.getUid());
		return "look_average";
	}
	
	/**
	 * 去设置某人比例的页面
	 */
	public String toAverage() {
		if(!Tool.havePower("D3")){
			this.addFieldError("error", "权限不足");
			return "error";
		}
		user = userService.load(user.getUid());
		session.setAttribute(MySession.AVERAGEUSER, user);
		roleList = roleService.list();
		userList = userService.listExcludeUser(user.getUid(), user.getDepartment().getDid(), 0);
		if(user.getRole().getGrade()<50){
			userList.addAll(userService.listByGrade(user,50));
		}
		return "average_list";
	}
	/**
	 * 设置某人的 年度  比例
	 * @return 
	 */
	public String setAverage() {
		if(!Tool.havePower("D3")){
			this.addFieldError("error", "权限不足");
			return "error";
		}
		year = (Year) session.getAttribute(MySession.YEAR);
		user = (User) session.getAttribute(MySession.AVERAGEUSER);
		if(year==null){
			this.addFieldError("error", "没有创建度");
			return ERROR;
		}
		averageService.delete(year,user);
		// 遍历角色
		Iterator<Long> keys = map.keySet().iterator();
		while (keys.hasNext()) {
			long count = 0; // 总人数
			long roleid = (Long) keys.next();
			Double roleProportion = map.get(roleid); // 本角色的评分比例
			if(null==roleProportion){
				roleProportion=0.0;
			}
			for (long userId : userIDs) {// 遍历用户 查看用户是否属于本角色
				User sourceUser = userService.load(userId);
				if (sourceUser.getRole().getRid() == roleid) {// 本人属于 本角色
					count++;
				}
			}
			for (long userId : userIDs) {// 遍历用户 查看用户是否属于本角色
				User sourceUser = userService.load(userId);
				if (sourceUser.getRole().getRid() == roleid) {// 本人属于 本角色
					Average average = averageService.find(year,user.getUid(), sourceUser.getUid());
					if (null == average) {
						average = new Average(roleProportion / count,year, user, sourceUser);
						averageService.save(average);
					} else {
						average.setAverage(roleProportion / count);
						averageService.update(average);
					}
				}
			}
		}
		return lookAverage();
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

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public List<Role> getRoleList() {
		return roleList;
	}

	public void setRoleList(List<Role> roleList) {
		this.roleList = roleList;
	}

	public RoleService getRoleService() {
		return roleService;
	}

	public void setRoleService(RoleService roleService) {
		this.roleService = roleService;
	}

	public List<User> getUserList() {
		return userList;
	}

	public void setUserList(List<User> userList) {
		this.userList = userList;
	}

	public Map<Long, Double> getMap() {
		return map;
	}

	public void setMap(Map<Long, Double> map) {
		this.map = map;
	}

	public List<Long> getUserIDs() {
		return userIDs;
	}

	public void setUserIDs(List<Long> userIDs) {
		this.userIDs = userIDs;
	}
	
	
}
