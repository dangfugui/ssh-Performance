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
 * 年度评分细表
 * @author duang
 *
 */
@Entity
public class ScoreYear {
	@Id
	@GeneratedValue
	private long sid;
	private double score;
	private String state=State.DEFAULT;
	private Date date=new Date();
	@ManyToOne(cascade={} ,fetch=FetchType.EAGER)//级联关系和抓取策略(积极)
	@JoinColumn(name="behavior_bid")	//指定外键    自己的数据库外键列名
	private Behavior behavior;
	@ManyToOne(cascade={} ,fetch=FetchType.EAGER)//级联关系和抓取策略(积极)
	@JoinColumn(name="fillUser_uid")	//指定外键    自己的数据库外键列名
	private User fillUser;
	@ManyToOne(cascade={} ,fetch=FetchType.EAGER)//级联关系和抓取策略(积极)
	@JoinColumn(name="aimUser_uid")	//指定外键    自己的数据库外键列名
	private User aimUser;
	
	
	public ScoreYear(){}
	
public ScoreYear(double score, Behavior behavior,
			User fillUser, User aimUser) {
		super();
		this.score = score;
		this.behavior = behavior;
		this.fillUser = fillUser;
		this.aimUser = aimUser;
	}

	////////////////////////////////////////////////////////////////
	public long getSid() {
		return sid;
	}
	public void setSid(long sid) {
		this.sid = sid;
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
	public Behavior getBehavior() {
		return behavior;
	}
	public void setBehavior(Behavior behavior) {
		this.behavior = behavior;
	}
	public User getFillUser() {
		return fillUser;
	}
	public void setFillUser(User fillUser) {
		this.fillUser = fillUser;
	}
	public User getAimUser() {
		return aimUser;
	}
	public void setAimUser(User aimUser) {
		this.aimUser = aimUser;
	}
	
	
}
