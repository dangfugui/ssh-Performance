package com.bctc.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import com.bctc.tool.State;

@Entity
public class PowerGroup {
	@Id
	@GeneratedValue
	private long pid;
	private String name;
	private String powerString;
	private String state=State.DEFAULT;
	
	private Date date=new Date();
	@OneToMany
	@JoinColumn(name="powerGroup_pid")//对应外键名称   对方的数据库外键列名
	private List<User> users;
	public long getPid() {
		return pid;
	}
	public void setPid(long pid) {
		this.pid = pid;
	}
	public String getPowerString() {
		return powerString;
	}
	public void setPowerString(String powerString) {
		this.powerString = powerString;
	}
	public List<User> getUsers() {
		return users;
	}
	public void setUsers(List<User> users) {
		this.users = users;
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
}
