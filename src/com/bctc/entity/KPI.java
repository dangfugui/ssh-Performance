package com.bctc.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.bctc.tool.State;

/**
 * KPI关键指标
*/
@Entity
public class KPI {
	@Id
	@GeneratedValue
	private long kid;
	private double result=0.0;//最终结果
	private String state=State.DEFAULT;	//状态
	private Date date=new Date();	//默认为当前时间
	@ManyToOne( fetch=FetchType.EAGER)//级联关系和抓取策略(积极)
	@JoinColumn(name="aimUser_uid")	//指定外键    自己的数据库外键列名
	private User aimUser;//被考核人
	@ManyToOne(fetch=FetchType.EAGER)//级联关系和抓取策略(积极)
	@JoinColumn(name="fillUser_uid")	//指定外键    自己的数据库外键列名
	private User fillUser;//指标拟定人
	@ManyToOne( fetch=FetchType.EAGER)//级联关系和抓取策略(积极)
	@JoinColumn(name="quarter_qid")	//指定外键    自己的数据库外键列名
	private Quarter quarter ;//所属季度id
	
	//	控制反转 交给user控制				级联删除			级联关键系             			抓取策略(懒加载)
	@OneToMany( /*orphanRemoval=true , mappedBy="department",*/cascade={CascadeType.REMOVE},fetch=FetchType.LAZY)//
	@JoinColumn(name="kpi_kid")//对应外键名称   对方的数据库外键列名
	private List<ContentKPI> contents=new ArrayList<ContentKPI>();
	
	public KPI(){}
	public KPI(Quarter quarter,User aimUser ,User fillUser){
		this.aimUser = aimUser;
		this.fillUser = fillUser;
		this.quarter = quarter;
	}
	



	public long getKid() {
		return kid;
	}
	public void setKid(long kid) {
		this.kid = kid;
	}
	public User getAimUser() {
		return aimUser;
	}

	public void setAimUser(User aimUser) {
		this.aimUser = aimUser;
	}

	public User getFillUser() {
		return fillUser;
	}

	public void setFillUser(User fillUser) {
		this.fillUser = fillUser;
	}

	public Quarter getQuarter() {
		return quarter;
	}

	public void setQuarter(Quarter quarter) {
		this.quarter = quarter;
	}

	
	public List<ContentKPI> getContents() {
		return contents;
	}
	public void setContents(List<ContentKPI> contents) {
		this.contents = contents;
	}
	@Override
	public String toString() {
		return "KPI [kid=" + kid + "]";
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
	public double getResult() {
		return result;
	}
	public void setResult(double result) {
		this.result = result;
	}




}
