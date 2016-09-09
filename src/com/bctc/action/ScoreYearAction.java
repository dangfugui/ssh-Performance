package com.bctc.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.bctc.entity.Average;
import com.bctc.entity.Behavior;
import com.bctc.entity.ScoreYear;
import com.bctc.entity.User;
import com.bctc.service.AverageService;
import com.bctc.service.ScoreYearService;
import com.bctc.service.UserService;
import com.bctc.tool.MySession;

public class ScoreYearAction extends SuperAction{

	private static final long serialVersionUID = 1L;
	private User user=new User();
	private UserService userService;
	private ScoreYearService scoreYearService;
	private Behavior behavior=new Behavior();
	private ScoreYear scoreYear=new ScoreYear();
	private AverageService averageService;
	private Map<Long,Double> map=new HashMap<Long, Double>();
	/**
	 * 提交打分 
	 * @return 
	 */
	public String scoreYear(){
		double sum=0.0;
		for (Map.Entry<Long, Double> entry : map.entrySet()) {
		   System.out.println("key= " + entry.getKey() + " and value= " + entry.getValue());
		   long bid=entry.getKey();
		   behavior.setBid(bid);
		   Double score=entry.getValue();
		   if(null==score){
			   score=0.0;
		   }
		   sum+=score;
		   user=(User) session.getAttribute(MySession.AIMUSER);
		   scoreYear= scoreYearService.load(behavior,Tool.getUser(),user);
		   if(scoreYear==null){
			   scoreYear=new ScoreYear(score, behavior, Tool.getUser(), user);
			   scoreYearService.save(scoreYear);
		   }else{
			   scoreYear.setScore(score);
			   scoreYearService.update(scoreYear);
		   }
		}
		List<Average> averageList = averageService.findByAimUser(scoreYear.getBehavior().getYear(), user);
		if(null!=averageList&&averageList.size()!=0){
			Average average = averageList.get(0);
			average.setState(""+sum);
			averageService.update(average);
		}
		this.addActionMessage("打分成功");
		return SUCCESS;
		
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public UserService getUserService() {
		return userService;
	}
	@Resource
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	public ScoreYearService getScoreYearService() {
		return scoreYearService;
	}
	@Resource
	public void setScoreYearService(ScoreYearService scoreYearService) {
		this.scoreYearService = scoreYearService;
	}
	public Behavior getBehavior() {
		return behavior;
	}
	public void setBehavior(Behavior behavior) {
		this.behavior = behavior;
	}
	public void setMap(Map<Long, Double> map) {
		this.map = map;
	}
	public Map<Long, Double> getMap() {
		return map;
	}
	public AverageService getAverageService() {
		return averageService;
	}
	@Resource
	public void setAverageService(AverageService averageService) {
		this.averageService = averageService;
	}
	

}