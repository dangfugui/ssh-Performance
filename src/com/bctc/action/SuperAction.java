package com.bctc.action;

import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.apache.struts2.interceptor.SessionAware;
import org.apache.struts2.util.ServletContextAware;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

//所有Action动作的父类
public class SuperAction extends ActionSupport implements ServletRequestAware,ServletResponseAware,ServletContextAware  ,
SessionAware
{

	private static final long serialVersionUID = 1L;
	protected HttpServletRequest request;//请求对象
	protected HttpServletResponse response;//响应对象
	protected static HttpSession session;//会话对象
	protected ServletContext application;//全局对象
	protected Map<String,Object> sessionMap;

	/**
	 * 为了避免与Servlet API耦合在一起，方便Action类做单元测试，Struts2对HttpServletRequest、HttpSession和ServletContext进行了封装，
	 * 构造了三个Map对象来替代这三种对象，在Action中，直接使用HttpServletRequest、HttpSession、ServletContext对应的Map对象来保存和读取数据。
	 * 要获得这三个Map对象，可以使用com.opensymphony.xwork2.ActionContext类。
	 */
	public void dinit(){
		ActionContext context=ActionContext.getContext();
		Map requestMap=(Map) context.get("request");
		sessionMap=context.getSession();
		Map applicationMap=(Map) context.getApplication();
	}
	/**
	 * 前面采用的用Map对象来封装Servlet API。如果想要在action类中直接使用HttpServletRequest、HttpServletResponse、ServletContext这些对象，Struts2.x中又以什么方式来提供支持？
	 * (此种方式的不足就是与Servlet API耦合，在测试时需要Servlet容器) 在Struts2中可以直接获取HttpServletRequest和ServletContext对象，可以使用org.apache.struts2.ServletActionContext类
	 * ，该类是ActionContext的子类，在这个类中定义了下面的三个静态方法：

	 */
	public void dinit2(){
		request = (HttpServletRequest) ServletActionContext.getRequest();
		session=request.getSession();
		application=ServletActionContext.getServletContext();
	}
	
	@Override
	public void setServletContext(ServletContext application) {
		// TODO Auto-generated method stub
		this.application=application;
	
	}

	@Override
	public void setServletResponse(HttpServletResponse response) {
		// TODO Auto-generated method stub
		this.response=response;
	}

	@Override
	public void setServletRequest(HttpServletRequest request) {
		// TODO Auto-generated method stub
		this.request=request;
		session=this.request.getSession();
	}

	@Override		//SessionAware  得到封装到map 中的 session
	public void setSession(Map<String, Object> session) {
		// TODO Auto-generated method stub
		this.sessionMap=session;
	}

}
