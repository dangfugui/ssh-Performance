package com.bctc.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.bctc.tool.State;

/*
 * 季度表*/
@Entity
public class Quarter {
	@Id
	@GeneratedValue
	private long qid;//季度id
	private String name;//季度名
	private Date date=new Date();	//默认为当前时间
	private Double proportion;
	private String state=State.DEFAULT;	//状态
	
	@OneToMany( cascade={} ,fetch=FetchType.LAZY)//
	@JoinColumn(name="quarter_qid")//对应外键名称   对方的数据库外键列名
	private Set<KPI> kpis=new HashSet<KPI>();
	@OneToMany( cascade={} ,fetch=FetchType.LAZY)//
	@JoinColumn(name="quarter_qid")//对应外键名称   对方的数据库外键列名
	private Set<Proportion> proportions;
	
	//@ManyToMany(mappedBy="quarters") //指定被主控放控制  自己的句柄名
	@ManyToOne(cascade={} ,fetch=FetchType.EAGER)//级联关系和抓取策略(积极)
	@JoinColumn(name="year_yid")	//指定外键    自己的数据库外键列名
	private Year year;
	//private Set<Teachers> teachers=new HashSet<Teachers>(); 学生

	public Quarter(){}
	
	
	
	public long getQid() {
		return qid;
	}


	public void setQid(long qid) {
		this.qid = qid;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public Set<KPI> getKpis() {
		return kpis;
	}
	public void setKpis(Set<KPI> kpis) {
		this.kpis = kpis;
	}

	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}

	public Set<Proportion> getProportions() {
		return proportions;
	}


	public void setProportions(Set<Proportion> proportions) {
		this.proportions = proportions;
	}

	public Year getYear() {
		return year;
	}

	public void setYear(Year year) {
		this.year = year;
	}

	public Double getProportion() {
		return proportion;
	}

	public void setProportion(Double proportion) {
		this.proportion = proportion;
	}

	
}
