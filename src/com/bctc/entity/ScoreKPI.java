package com.bctc.entity;

import java.util.Date;

import javax.persistence.*;

import com.bctc.tool.State;

/**
 * kpi指标得分表
 */
@Entity
public class ScoreKPI {
	@Id
	@GeneratedValue
	private long sid;//得分id
	private double score;//得分
	private String state=State.DEFAULT;	//状态 
	private Date date=new Date();	//默认为当前时间
	@ManyToOne(cascade={} ,fetch=FetchType.EAGER)//级联关系和抓取策略(积极)
	@JoinColumn(name="contentKPI_cid")	//指定外键 名 
	private ContentKPI contentKPI;//指标内容id
	@ManyToOne(cascade={} ,fetch=FetchType.EAGER)//级联关系和抓取策略(积极)
	@JoinColumn(name="fillUser_uid")	//指定外键   
	private User fillUser;//填表人
	
	
	
	///////////////////////////
	
	
	
	
	public ContentKPI getContentKPI() {
		return contentKPI;
	}
	public long getSid() {
		return sid;
	}
	public void setSid(long sid) {
		this.sid = sid;
	}
	public void setContentKPI(ContentKPI contentKPI) {
		this.contentKPI = contentKPI;
	}
	public User getFillUser() {
		return fillUser;
	}
	public void setFillUser(User fillUser) {
		this.fillUser = fillUser;
	}
	public double getScore() {
		return score;
	}
	public void setScore(double score) {
		this.score = score;
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

	
	
}
