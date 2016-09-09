package com.bctc.entity;

import java.util.Date;
import java.util.Set;

import javax.persistence.*;

import com.bctc.tool.State;

/**
 * 角色表
 */
@Entity
public class Role {
	@Id
	@GeneratedValue
	private long rid;//角色id
	private String name;//角色名
	private int grade;//等级 越小等级越高
	private String state=State.DEFAULT;	//状态 
	private Date date=new Date();	//默认为当前时间
	@OneToMany(cascade={},fetch=FetchType.LAZY )//***********级联关键系和抓取策略(懒加载)<,级联删除
	@JoinColumn(name="role_rid")	//对应外键名称    对方的数据库外键列名
	private Set<User> users;
	public Role(){}
	public Role(String name,int grade){
		this.name=name;
		this.grade=grade;
	}
	
	
	
	
	public long getRid() {
		return rid;
	}
	public void setRid(long rid) {
		this.rid = rid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getGrade() {
		return grade;
	}
	public void setGrade(int grade) {
		this.grade = grade;
	}
	public Set<User> getUsers() {
		return users;
	}
	public void setUsers(Set<User> users) {
		this.users = users;
	}
	public String getState() {
		if(null==state)state=State.DEFAULT;
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
