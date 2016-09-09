package com.bctc.entity;

import java.util.Date;
import java.util.HashSet;
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
public class ContentKPI {
	@Id
	@GeneratedValue
	private long cid;		//id
	private String name;	//指标名称
	private double weight;	//指标权重
	@Column(length=2046)
	private String definde;	//定义。计算公式
	@Column(length=2046)
	private String aim;		//目标值
	@Column(length=2046)
	private String process;//计分方法
	@Column(length=2046)
	private String actual ;//实际完成情况
	private double result;	//汇总得分
	private String state=State.DEFAULT;	//状态
	private Date date=new Date();	//默认为当前时间
	/////////////////////////////////////////////
	@ManyToOne(cascade={} ,fetch=FetchType.EAGER)//级联关系和抓取策略(积极)
	@JoinColumn(name="sourceUser_uid")	//指定外键  
	private User sourceUser;	//数据来源-----------------------
	@ManyToOne(cascade={} ,fetch=FetchType.EAGER)//级联关系和抓取策略(积极)
	@JoinColumn(name="kpi_kid")	//指定外键   
	private KPI kpi;		//属于哪个KPI表
	@OneToMany(cascade={},fetch=FetchType.LAZY)//级联关键系和抓取策略(懒加载)
	@JoinColumn(name="contentKPI_cid")	//对应外键名称
	private Set<ScoreKPI> scores=new HashSet<ScoreKPI>();
	
	public ContentKPI(){}

	 
	public ContentKPI(KPI kpi, String name, double weight,String definde, String aim, String process,
			User sourceUser, String state) {
		this.kpi=kpi;
		this.name=name;
		this.weight=weight;
		this.definde=definde;
		this.aim=aim;
		this.process=process;
		this.sourceUser=sourceUser;
		this.state=state;
	}

	public long getCid() {
		return cid;
	}


	public void setCid(long cid) {
		this.cid = cid;
	}


	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getWeight() {
		return weight;
	}
	public void setWeight(double weight) {
		this.weight = weight;
	}
	public String getDefinde() {
		return definde;
	}
	public void setDefinde(String definde) {
		this.definde = definde;
	}
	public String getAim() {
		return aim;
	}
	public void setAim(String aim) {
		this.aim = aim;
	}
	public String getProcess() {
		return process;
	}
	public void setProcess(String process) {
		this.process = process;
	}
	public String getActual() {
		return actual;
	}
	public void setActual(String actual) {
		this.actual = actual;
	}
	
	public double getResult() {
		return result;
	}
	public void setResult(double result) {
		this.result = result;
	}
	public User getSourceUser() {
		return sourceUser;
	}
	public void setSourceUser(User sourceUser) {
		this.sourceUser = sourceUser;
	}
	public KPI getKpi() {
		return kpi;
	}
	public void setKpi(KPI kpi) {
		this.kpi = kpi;
	}
	public Set<ScoreKPI> getScores() {
		return scores;
	}
	public void setScores(Set<ScoreKPI> scores) {
		this.scores = scores;
	}
	public String getState() {
		//if(null==state)state=State.DEFAULT;
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
