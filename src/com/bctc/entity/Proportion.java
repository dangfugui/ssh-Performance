package com.bctc.entity;

import java.util.Date;

import javax.persistence.*;

/**
 * 互评比例表
 */
@Entity
public class Proportion {
	@Id
	@GeneratedValue
	private long pid;//互评比例表id
	private double proportion;//比例
	private double total;	//总分
	private String state="未打分";	//状态 
	private Date date=new Date();	//默认为当前时间
	
	
	@ManyToOne(cascade={} ,fetch=FetchType.EAGER)//级联关系和抓取策略(积极)
	@JoinColumn(name="aimUser_uid")	//指定外键    自己的数据库外键列名
	private User aimUser;//目标用户
	
	@ManyToOne(cascade={} ,fetch=FetchType.EAGER)//级联关系和抓取策略(积极)
	@JoinColumn(name="sourceUser_uid")	//指定外键    自己的数据库外键列名 
	private User sourceUser;//来源用户
	@ManyToOne(cascade={} ,fetch=FetchType.EAGER)//级联关系和抓取策略(积极)
	@JoinColumn(name="quarter_qid")	//指定外键    自己的数据库外键列名
	private Quarter quarter;//季度

	public Proportion(){}
	public Proportion(Quarter quarter,User aimUser,User sourceUser,Double proportion){
		this.quarter=quarter;
		this.aimUser=aimUser;
		this.sourceUser=sourceUser;
		this.proportion=proportion;
	}
	
	
		
	public Proportion(Quarter quarter, double proportion, User aimUser,
			User sourceUser) {
		this.quarter=quarter;
		this.proportion=proportion;
		this.aimUser=aimUser;
		this.sourceUser=sourceUser;
	}
	

	public long getPid() {
		return pid;
	}
	public void setPid(long pid) {
		this.pid = pid;
	}
	public double getProportion() {
		return proportion;
	}

	public void setProportion(double proportion) {
		this.proportion = proportion;
	}

	public User getAimUser() {
		return aimUser;
	}

	public void setAimUser(User aimUser) {
		this.aimUser = aimUser;
	}

	public User getSourceUser() {
		return sourceUser;
	}

	public void setSourceUser(User sourceUser) {
		this.sourceUser = sourceUser;
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

	public Quarter getQuarter() {
		return quarter;
	}

	public void setQuarter(Quarter quarter) {
		this.quarter = quarter;
	}
	public double getTotal() {
		return total;
	}
	public void setTotal(double total) {
		this.total = total;
	}


	
}
