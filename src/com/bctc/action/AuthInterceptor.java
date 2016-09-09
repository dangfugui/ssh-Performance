package com.bctc.action;

import java.util.Map;
import com.bctc.tool.MySession;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

/**
 * 权限拦截器 看是否登录
 */
public class AuthInterceptor extends AbstractInterceptor {

	private static final long serialVersionUID = 1L;
	@Override	//初始化方法
	public void init() {
		// TODO Auto-generated method stub
		super.init();
	}
	@Override  //摧毁 清理方法
	public void destroy() {
		// TODO Auto-generated method stub
		super.destroy();
	}
	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		// TODO Auto-generated method stub
		ActionContext context=ActionContext.getContext();
		Map<String, Object> session=context.getSession();
		/*
		System.out.println("Action："+invocation.getAction().getClass().getName());  
		System.out.println("Struts2 中配置的Action："+invocation.getProxy().getActionName());  
		System.out.println("调用的方法："+invocation.getProxy().getMethod()); */
		
		if(session.get(MySession.LOGINUSER)!=null){
			String result=invocation.invoke();
			return result;
		}
		return "login";
	}

}
