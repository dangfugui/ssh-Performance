package com.bctc.tool;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SzFilter implements Filter{
 private FilterConfig config;
 private String redirectURL;
 
 @Override
 public void init(FilterConfig config){
  this.config = config;
  this.redirectURL = config.getInitParameter("redirectURL");
 }

 @Override
 public void destroy() {
  this.config = null;
  this.redirectURL = null;
 }

 @Override
 public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
   FilterChain chain) throws IOException, ServletException {
  HttpServletRequest request = (HttpServletRequest) servletRequest;    
  HttpServletResponse response = (HttpServletResponse) servletResponse;  
  // 获取ServletContext对象用于记录日志
  ServletContext scon = this.config.getServletContext();
  long before = System.currentTimeMillis();
  System.out.println("开始过滤::::" + before);
  HttpServletRequest hsq = (HttpServletRequest)servletRequest;
  scon.log("接收到的请求::::" + hsq.getServletPath());
  try{
   response.sendRedirect(request.getContextPath() + redirectURL);
   //chain.doFilter(request,response);
  }catch(Exception e){
   e.printStackTrace();
  }
  long after = System.currentTimeMillis();
  System.out.println("结束过滤::::" + after);
  scon.log("请求过滤到::::" + request.getContextPath() + redirectURL + "所花时间::::" + (after-before));
 }
}