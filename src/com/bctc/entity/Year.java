package com.bctc.entity;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import com.bctc.tool.State;
@Entity
public class Year {
	@Id
	@GeneratedValue
	private long yid;
	private String name;
	private String state=State.DEFAULT;
	private Date date=new Date();
	
	@OneToMany( cascade={},fetch=FetchType.LAZY)//
	@JoinColumn(name="year_yid")//对应外键名称   对方的数据库外键列名
	private Set<ResultYear> resultYears=new HashSet<ResultYear>();	
	
	@OneToMany( cascade={},fetch=FetchType.LAZY)//
	@JoinColumn(name="year_yid")//对应外键名称   对方的数据库外键列名
	private List<Behavior> behaviors =new ArrayList<Behavior>();
	
	@OneToMany( cascade={},fetch=FetchType.LAZY)//
	@JoinColumn(name="year_yid")//对应外键名称   对方的数据库外键列名
	private Set<Average> averages;	//总分表
	
//	@ManyToMany(fetch=FetchType.LAZY)
//	@JoinTable(name="years_quarters",	//中间表名称
//	joinColumns={@JoinColumn(name="year_yid")},	//本表主键
//	inverseJoinColumns={@JoinColumn(name="quarter_qid")})//外表主键
	@OneToMany( cascade={},fetch=FetchType.LAZY)//
	@JoinColumn(name="year_yid")//对应外键名称   对方的数据库外键列名
	private Set<Quarter> quarters=new HashSet<Quarter>();
	/////////////////////////////////////////////////////////////////////////////////////////////
	public long getYid() {
		return yid;
	}
	public void setYid(long yid) {
		this.yid = yid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
	public Set<Quarter> getQuarters() {
		return quarters;
	}
	public void setQuarters(Set<Quarter> quarters) {
		this.quarters = quarters;
	}
	public Set<ResultYear> getResultYears() {
		return resultYears;
	}
	public void setResultYears(Set<ResultYear> resultYears) {
		this.resultYears = resultYears;
	}
	
	public List<Behavior> getBehaviors() {
		return behaviors;
	}
	public void setBehaviors(List<Behavior> behaviors) {
		this.behaviors = behaviors;
	}
	public Set<Average> getAverages() {
		return averages;
	}
	public void setAverages(Set<Average> averages) {
		this.averages = averages;
	}
	
	
	
}
