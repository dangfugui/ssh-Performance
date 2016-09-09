package com.bctc.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

import org.apache.struts2.json.annotations.JSON;
import org.hibernate.annotations.Cascade;

import com.bctc.tool.State;

@Entity
@Table(name="h_user")
public class User{
	private static final String KPI = null;
	@Id
	@GeneratedValue
	private long uid;
	private String agentId;//标号
	private String name;
	private String password;
	//@Transient //表示该属性并非一个到数据库的字段映射
	private String state=State.DEFAULT;	//状态 
	//@Column(columnDefinition="CURRENT_TIMESTAMP") //默认值
	private Date date=new Date();	//默认为当前时间
	@ManyToOne(cascade={} ,fetch=FetchType.EAGER)//级联关系和抓取策略(积极)
	@JoinColumn(name="department_did")	//指定外键    自己的数据库外键列名 
	private Department department;
	@ManyToOne(cascade={} ,fetch=FetchType.EAGER)//级联关系和抓取策略(积极)
	@JoinColumn(name="role_rid")	//指定外键 		自己的数据库外键列名 
	private Role role;
	@ManyToOne(cascade={} ,fetch=FetchType.EAGER)//级联关系和抓取策略(积极)
	@JoinColumn(name="powerGroup_pid")	//指定外键 		自己的数据库外键列名 
	private PowerGroup powerGroup;
	
	//-----------------------------------------
	@OneToMany
	@JoinColumn(name="aimUser_uid")//对应外键名称   对方的数据库外键列名
	private Set<KPI> myKPIs=new HashSet<KPI>();	//	我的KPI表
	@OneToMany
	@JoinColumn(name="fillUser_uid")//对应外键名称   对方的数据库外键列名
	private Set<KPI> fillKPIs=new HashSet<KPI>();	//我的要填写的KPI表
	@OneToMany
	@JoinColumn(name="fillUser_uid")//对应外键名称   对方的数据库外键列名
	private Set<ScoreKPI> fillScoreKPIs =new HashSet<ScoreKPI>();
	@OneToMany
	@JoinColumn(name="sourceUser_uid")//对应外键名称   对方的数据库外键列名
	private Set<Proportion> toProportions=new HashSet<Proportion>();	//我给别人打分的比例单
	
	@OneToMany
	@JoinColumn(name="aimUser_uid")//对应外键名称   对方的数据库外键列名
	private Set<Proportion> fromProportions=new HashSet<Proportion>();//谁给我打分的比例
	
	//@OneToMany(cascade={CascadeType.REMOVE},fetch=FetchType.LAZY)//
	@OneToMany
	@JoinColumn(name="sourceUser_uid")//对应外键名称   对方的数据库外键列名
	private Set<ContentKPI> fillContentKPIs =new HashSet<ContentKPI>();//我填写的kpi 数据来源
	
	
	@OneToMany
	@JoinColumn(name="aimUser_uid")//对应外键名称   对方的数据库外键列名
	private Set<ResultYear> resultYears =new HashSet<ResultYear>();//我的年度总分
	
	@OneToMany
	@JoinColumn(name="fillUser_uid")//对应外键名称   对方的数据库外键列名
	private Set<ScoreYear> fillScoreYears;
	@OneToMany
	@JoinColumn(name="aimUser_uid")//对应外键名称   对方的数据库外键列名
	private Set<ScoreYear> myScoreYears;
	
	@OneToMany
	@JoinColumn(name="sourceUser_uid")//对应外键名称   对方的数据库外键列名
	private Set<Average> toAverages=new HashSet<Average>();	//我给别人打分的比例单
	@OneToMany
	@JoinColumn(name="aimUser_uid")//对应外键名称   对方的数据库外键列名
	private Set<Average> fromAverages=new HashSet<Average>();//谁给我打分的比例
	
	
	
	///////////---------------------------------------
	
	
	public User(){}
	public User(String name,String password){
		this.name=name;
		this.password=password;
	}
	/////////////////////////////////////
	
	public void setUid(String uid) {
		this.uid = Integer.parseInt(uid);
	}
	public long getUid() {
		return uid;
	}
	public void setUid(long uid) {
		this.uid = uid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@JSON(serialize=false)
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Department getDepartment() {
		return department;
	}
	public void setDepartment(Department department) {
		this.department = department;
	}
	@JSON(serialize=false)
	public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
		this.role = role;
	}
	@JSON(serialize=false)
	public Set<KPI> getMyKPIs() {
		return myKPIs;
	}
	public void setMyKPIs(Set<KPI> myKPIs) {
		this.myKPIs = myKPIs;
	}
	@JSON(serialize=false)
	public Set<KPI> getFillKPIs() {
		return fillKPIs;
	}
	public void setFillKPIs(Set<KPI> fillKPIs) {
		this.fillKPIs = fillKPIs;
	}
	@JSON(serialize=false)
	public Set<ScoreKPI> getFillScoreKPIs() {
		return fillScoreKPIs;
	}
	public void setFillScoreKPIs(Set<ScoreKPI> fillScoreKPIs) {
		this.fillScoreKPIs = fillScoreKPIs;
	}
	public Set<Proportion> getToProportions() {
		return toProportions;
	}
	public void setToProportions(Set<Proportion> toProportions) {
		this.toProportions = toProportions;
	}
	@JSON(serialize=false)
	public Set<Proportion> getFromProportions() {
		return fromProportions;
	}
	public void setFromProportions(Set<Proportion> fromProportions) {
		this.fromProportions = fromProportions;
	}
	public Set<ContentKPI> getFillContentKPIs() {
		return fillContentKPIs;
	}
	public void setFillContentKPIs(Set<ContentKPI> fillContentKPIs) {
		this.fillContentKPIs = fillContentKPIs;
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
	public Set<ResultYear> getResultYears() {
		return resultYears;
	}
	public void setResultYears(Set<ResultYear> resultYears) {
		this.resultYears = resultYears;
	}
	public Set<ScoreYear> getFillScoreYears() {
		return fillScoreYears;
	}
	public void setFillScoreYears(Set<ScoreYear> fillScoreYears) {
		this.fillScoreYears = fillScoreYears;
	}
	public Set<ScoreYear> getMyScoreYears() {
		return myScoreYears;
	}
	public void setMyScoreYears(Set<ScoreYear> myScoreYears) {
		this.myScoreYears = myScoreYears;
	}
	public Set<Average> getToAverages() {
		return toAverages;
	}
	public void setToAverages(Set<Average> toAverages) {
		this.toAverages = toAverages;
	}
	public Set<Average> getFromAverages() {
		return fromAverages;
	}
	public void setFromAverages(Set<Average> fromAverages) {
		this.fromAverages = fromAverages;
	}
	public PowerGroup getPowerGroup() {
		return powerGroup;
	}
	public void setPowerGroup(PowerGroup powerGroup) {
		this.powerGroup = powerGroup;
	}
	public static String getKpi() {
		return KPI;
	}
	public String getAgentId() {
		return agentId;
	}
	public void setAgentId(String agentId) {
		this.agentId = agentId;
	}
	
	
}
