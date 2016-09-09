package com.bctc.action;

import java.util.List;

import javax.annotation.Resource;

import com.bctc.entity.Quarter;
import com.bctc.entity.User;
import com.bctc.service.QuarterService;
import com.bctc.service.UserService;
import com.bctc.service.YearService;
import com.bctc.tool.MD5Util;
import com.bctc.tool.MySession;


public class LoginAction extends SuperAction{
	private static final long serialVersionUID = 1L;
	private User user=new User();
	private UserService userService;
	private QuarterService quarterService;
	private YearService yearService;
	private List<User> list;
	public String info;
	@Override
	public String execute() throws Exception {
		return SUCCESS;
	}
	/**
	 * 登录
	 * @return jsp页面
	 */
	public String login(){
		//////////////////////////
		
		/*user.setName("root");
		user.setPassword("root");
		info=(String) session.getAttribute(MySession.VERIFUCODE);
		*/
	////////////////////////////////////////////
	
		if(info==null||!info.equals(session.getAttribute(MySession.VERIFUCODE))){
			info="验证码错误";
			return "login";
		}
		User inUser = userService.login(user);
		if(null!=inUser){
			if(inUser.getPassword().equals(MD5Util.getMD5(user.getPassword()))){
				//设置默认季度为最后季度
				if(null!=quarterService.list()&&quarterService.list().size()>0){
					Quarter quarter=quarterService.list().get(0);
					session.setAttribute(MySession.QUARTER,quarter);	
				}
				if(null!=yearService.list()&&yearService.list().size()>0){
					session.setAttribute(MySession.YEAR, yearService.list().get(0));
				}
				session.setAttribute(MySession.LOGINUSER, inUser);//登录成功 用户加入session
				return SUCCESS;
			}else {
				info="密码错误";
				this.addFieldError("error", "密码错误");
				return "login";
			}
		}
		info="用户名不存在";
		this.addFieldError("error", "用户名不存在");
		return "login";
	}
	public void testError(){
		int a=10/0;
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
	public QuarterService getQuarterService() {
		return quarterService;
	}
	@Resource
	public void setQuarterService(QuarterService quarterService) {
		this.quarterService = quarterService;
	}
	public List<User> getList() {
		return list;
	}
	public void setList(List<User> list) {
		this.list = list;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	public YearService getYearService() {
		return yearService;
	}
	@Resource
	public void setYearService(YearService yearService) {
		this.yearService = yearService;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	
	
}
