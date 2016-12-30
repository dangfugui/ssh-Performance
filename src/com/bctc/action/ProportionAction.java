package com.bctc.action;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.bctc.entity.Proportion;
import com.bctc.entity.Quarter;
import com.bctc.entity.Role;
import com.bctc.entity.User;
import com.bctc.service.ProportionService;
import com.bctc.service.QuarterService;
import com.bctc.service.RoleService;
import com.bctc.service.UserService;
import com.bctc.tool.MySession;

public class ProportionAction extends SuperAction {

	private static final long serialVersionUID = 1L;
	private User user = new User();
	private List<Role> roleList;
	private List<User> userList;
	List<Proportion> proportionList;
	private UserService userService;
	private RoleService roleService;
	private ProportionService proportionService;
	private QuarterService quarterService;
	private Quarter quarter;
	private List<Long> userIDs;
	private Map<Long, Double> map=new HashMap<Long, Double>();

	public String execute() throws Exception {
		return SUCCESS;
	}
	/**
	 * 去设置某人比例的页面
	 */
	public String toProportion() {
		if(!Tool.havePower("E2")){
			this.addFieldError("error", "权限不足");
			return "error";
		}
		user = userService.load(user.getUid());
		session.setAttribute(MySession.PROPORTIONUSER, user);
		roleList = roleService.list();
		userList = userService.listExcludeUser(user.getUid(), user.getDepartment().getDid(), 0);
		if(user.getRole().getGrade()<50){
			userList.addAll(userService.listByGrade(user, 50));
		}
		return "proportion_list";
	}
	/**
	 * 设置某人的比例
	 * @return 
	 */
	public String setProportion() {
		if(!Tool.havePower("E2")){
			this.addFieldError("error", "权限不足");
			return "error";
		}
		quarter = (Quarter) session.getAttribute(MySession.QUARTER);
		user = (User) session.getAttribute(MySession.PROPORTIONUSER);
		if(quarter==null){
			this.addFieldError("error", "没有创建季度");
			return ERROR;
		}
		proportionService.delete(quarter.getQid(),user.getUid());
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
					Proportion proportion = proportionService.find(quarter,
							user.getUid(), sourceUser.getUid());
					if (null == proportion) {
						proportion = new Proportion(quarter, user, sourceUser,
								roleProportion / count);
						proportionService.save(proportion);
					} else {
						proportion.setProportion(roleProportion / count);
						proportionService.update(proportion);
					}
				}
			}
		}
		return lookProportion();
	}

	/**
	 * 查看某人的评分比例
	 * @return 
	 */
	public String lookProportion(){
		if(Tool.havePower("E2")){
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
		quarter = (Quarter) session.getAttribute(MySession.QUARTER);
		if(null==quarter){
			this.addFieldError("error", "还没有创建季度");
			return "error";
		}
		proportionList = proportionService.find(quarter, user.getUid());
		return "look_proportion";
	}
	
	/**
	 * 显示我要评分的KPI列表
	 * @return 
	 */
	public String listForScore(){
		user=(User) session.getAttribute(MySession.LOGINUSER);
		quarter = (Quarter) session.getAttribute(MySession.QUARTER);
		proportionList = proportionService.findMyFill(quarter, user.getUid());
		return "score_user_list";
	}
	
	/**
	 * 拷贝某一季度评分比例   设置为当前季度评分比例
	 * @return 
	 */
	public String copy(){
		if(!Tool.havePower("E2")){
			this.addFieldError("error", "权限不足");
			return "error";
		}
		if(Tool.isAdmin()){
			quarter=quarterService.load(quarter.getQid());
			Quarter lastQuarter=quarterService.list().get(0);
			for (Proportion proportion : lastQuarter.getProportions()) {
				proportionService.delete(proportion);//删除目前已经拟定的 比例  	准备使用模板
			}
			//进行拷贝评分比例
			for (Proportion p:quarter.getProportions()) {
				Proportion proportion=new Proportion(lastQuarter,p.getProportion(),p.getAimUser(),p.getSourceUser());
				proportionService.save(proportion);
			}
			return SUCCESS;
		}else {
			return "httpheader";
		}
		
	}
	
	
	// //////////////////////////////////////
	@Resource
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	@Resource
	public void setRoleService(RoleService roleService) {
		this.roleService = roleService;
	}

	@Resource
	public void setProportionService(ProportionService proportionService) {
		this.proportionService = proportionService;
	}
	

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<Role> getRoleList() {
		return roleList;
	}

	public void setRoleList(List<Role> roleList) {
		this.roleList = roleList;
	}

	public List<User> getUserList() {
		return userList;
	}

	public void setUserList(List<User> userList) {
		this.userList = userList;
	}

	public List<Long> getUserIDs() {
		return userIDs;
	}

	public void setUserIDs(List<Long> userIDs) {
		this.userIDs = userIDs;
	}

	public Map<Long, Double> getMap() {
		return map;
	}

	public void setMap(Map<Long, Double> map) {
		this.map = map;
	}
	public List<Proportion> getProportionList() {
		return proportionList;
	}
	public void setProportionList(List<Proportion> proportionList) {
		this.proportionList = proportionList;
	}
	public Quarter getQuarter() {
		return quarter;
	}
	public void setQuarter(Quarter quarter) {
		this.quarter = quarter;
	}
	@Resource
	public void setQuarterService(QuarterService quarterService) {
		this.quarterService = quarterService;
	}

}
