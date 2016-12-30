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
 * KPI鍏抽敭鎸囨爣
*/
@Entity
public class KPI {
	@Id
	@GeneratedValue
	private long kid;
	private double result=0.0;//鏈�粓缁撴灉
	private String state=State.DEFAULT;	//鐘舵�
	private Date date=new Date();	//榛樿涓哄綋鍓嶆椂闂�
	@ManyToOne( fetch=FetchType.EAGER)//绾ц仈鍏崇郴鍜屾姄鍙栫瓥鐣�绉瀬)
	@JoinColumn(name="aimUser_uid")	//鎸囧畾澶栭敭    鑷繁鐨勬暟鎹簱澶栭敭鍒楀悕
	private User aimUser;//琚�鏍镐汉
	@ManyToOne(fetch=FetchType.EAGER)//绾ц仈鍏崇郴鍜屾姄鍙栫瓥鐣�绉瀬)
	@JoinColumn(name="fillUser_uid")	//鎸囧畾澶栭敭    鑷繁鐨勬暟鎹簱澶栭敭鍒楀悕
	private User fillUser;//鎸囨爣鎷熷畾浜�
	@ManyToOne( fetch=FetchType.EAGER)//绾ц仈鍏崇郴鍜屾姄鍙栫瓥鐣�绉瀬)
	@JoinColumn(name="quarter_qid")	//鎸囧畾澶栭敭    鑷繁鐨勬暟鎹簱澶栭敭鍒楀悕
	private Quarter quarter ;//鎵�睘瀛ｅ害id
	
	//	鎺у埗鍙嶈浆 浜ょ粰user鎺у埗				绾ц仈鍒犻櫎			绾ц仈鍏抽敭绯�            			鎶撳彇绛栫暐(鎳掑姞杞�
	@OneToMany( /*orphanRemoval=true , mappedBy="department",*/cascade={CascadeType.REMOVE},fetch=FetchType.LAZY)//
	@JoinColumn(name="kpi_kid")//瀵瑰簲澶栭敭鍚嶇О   瀵规柟鐨勬暟鎹簱澶栭敭鍒楀悕
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
