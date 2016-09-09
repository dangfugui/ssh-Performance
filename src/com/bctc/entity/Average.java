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
 * 年度平均比例
 */
@Entity
public class Average {
	@Id
	@GeneratedValue
	private long aid;
	private double average;
	private double total;	//总分
	private String state="未打分";
	private Date date=new Date();
	@ManyToOne(cascade={} ,fetch=FetchType.EAGER)//级联关系和抓取策略(积极)
	@JoinColumn(name="year_yid")	//指定外键    自己的数据库外键列名
	private Year year;
	@ManyToOne(cascade={} ,fetch=FetchType.EAGER)//级联关系和抓取策略(积极)
	@JoinColumn(name="aimUser_uid")	//指定外键    自己的数据库外键列名
	private User aimUser;
	@ManyToOne(cascade={} ,fetch=FetchType.EAGER)//级联关系和抓取策略(积极)
	@JoinColumn(name="sourceUser_uid")	//指定外键    自己的数据库外键列名
	private User sourceUser;
	
	
	public Average(){}
	
	
	public Average(double average, Year year, User aimUser, User sourceUser) {
		this.average = average;
		this.year = year;
		this.aimUser = aimUser;
		this.sourceUser = sourceUser;
	}


	//////////////////////////////////////////////////////////////////////
	public long getAid() {
		return aid;
	}
	public void setAid(long aid) {
		this.aid = aid;
	}
	public double getAverage() {
		return average;
	}
	public void setAverage(double average) {
		this.average = average;
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
	public Year getYear() {
		return year;
	}
	public void setYear(Year year) {
		this.year = year;
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


	public double getTotal() {
		return total;
	}


	public void setTotal(double total) {
		this.total = total;
	}


	
	
	
	
}
