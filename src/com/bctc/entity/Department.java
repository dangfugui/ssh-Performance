package com.bctc.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import com.bctc.tool.State;

/**
 * 部门表
 */
@Entity
public class Department implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private long did;//部门id
	private String name;//部门名
	private String state=State.DEFAULT;	//状态 
	private Date date=new Date();	//默认为当前时间
	//			控制反转 交给user控制				级联删除			级联关键系             			抓取策略(懒加载)
	@OneToMany( /*orphanRemoval=true , mappedBy="department",*/cascade={},fetch=FetchType.LAZY)//
	@JoinColumn(name="department_did")//对应外键名称   对方的数据库外键列名
	private Set<User> users=new HashSet<User>();
	public Department(){}
	public  Department(String name){
		this.name=name;
	}
	
	
	
	
	
	public long getDid() {
		return did;
	}
	public void setDid(long did) {
		this.did = did;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Set<User> getUsers() {
		return users;
	}
	public void setUsers(Set<User> users) {
		this.users = users;
	}
	@Override
	public String toString() {
		return "Department [did=" + did + ", name=" + name + "]";
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
