package com.bctc.entity;

import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.bctc.tool.State;

@Entity
public class Behavior {
	@Id
	@GeneratedValue
	private long bid;
	private String name;
	@Column(length=2046)
	private String standard;
	private String state=State.DEFAULT;
	private Date date=new Date();
	@ManyToOne(cascade={} ,fetch=FetchType.EAGER)//级联关系和抓取策略(积极)
	@JoinColumn(name="year_yid")	//指定外键    自己的数据库外键列名
	private Year year;
	
	//--------------
	@OneToMany( cascade={},fetch=FetchType.LAZY)//
	@JoinColumn(name="behavior_bid")//对应外键名称   对方的数据库外键列名
	private Set<ScoreYear> scoreYears;
	
	
	public Behavior(){}
	 
	
	
public Behavior(String name, String standard, Year year) {
		super();
		this.name = name;
		this.standard = standard;
		this.year = year;
	}



////////////////////////////////////////////////////////////////
	public long getBid() {
		return bid;
	}

	public void setBid(long bid) {
		this.bid = bid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStandard() {
		return standard;
	}

	public void setStandard(String standard) {
		this.standard = standard;
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

	public Set<ScoreYear> getScoreYears() {
		return scoreYears;
	}

	public void setScoreYears(Set<ScoreYear> scoreYears) {
		this.scoreYears = scoreYears;
	}
	
}
