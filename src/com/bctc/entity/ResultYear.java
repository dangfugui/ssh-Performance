package com.bctc.entity;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.bctc.tool.State;

/**
 * 年度总分表
 */
@Entity
public class ResultYear {
	@Id
	@GeneratedValue
	private long rid;
	private double result;
	private String state=State.DEFAULT;
	private Date date=new Date();
	@ManyToOne(cascade={} ,fetch=FetchType.EAGER)//级联关系和抓取策略(积极)
	@JoinColumn(name="aimUser_uid")	//指定外键    自己的数据库外键列名
	private User aimUser;
	@ManyToOne(cascade={} ,fetch=FetchType.EAGER)//级联关系和抓取策略(积极)
	@JoinColumn(name="year_yid")	//指定外键    自己的数据库外键列名
	private Year year;
	
	
	public ResultYear(){}
	public ResultYear(Year year,User aimUser,double result){
		this.year=year;
		this.aimUser=aimUser;
		this.result=result;
	}
	
////////////////////////////////////////////////////////////////
	public long getRid() {
		return rid;
	}
	public void setRid(long rid) {
		this.rid = rid;
	}
	public double getResult() {
		return result;
	}
	public void setResult(double result) {
		this.result = result;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public User getAimUser() {
		return aimUser;
	}
	public void setAimUser(User aimUser) {
		this.aimUser = aimUser;
	}
	public Year getYear() {
		return year;
	}
	public void setYear(Year year) {
		this.year = year;
	}


}
