package com.bctc.vo;

import com.bctc.entity.User;

public class UserKPI {
	private User user;				//1-10 管理员    10-50中高级   50-100 中级    100+ 员工
	private double zuyao=0.0;			//主要负责人评分
	private double fenguang=0.0;		//部门分管负责人评分
	private double sijingli=0.0;		//科室经理评分
	private double yuangong=0.0;		//员工互评
	private double xiaji=0.0;			//下级评分
	private double zonghe=0.0;			//综合成绩
	private int paiming=0;			//排名
	
	

	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public double getZuyao() {
		return zuyao;
	}
	public void setZuyao(double zuyao) {
		this.zuyao = zuyao;
	}
	public double getFenguang() {
		return fenguang;
	}
	public void setFenguang(double fenguang) {
		this.fenguang = fenguang;
	}
	public double getSijingli() {
		return sijingli;
	}
	public void setSijingli(double sijingli) {
		this.sijingli = sijingli;
	}
	public double getYuangong() {
		return yuangong;
	}
	public void setYuangong(double yuangong) {
		this.yuangong = yuangong;
	}
	public double getXiaji() {
		return xiaji;
	}
	public void setXiaji(double xiaji) {
		this.xiaji = xiaji;
	}
	public double getZonghe() {
		return zonghe;
	}
	public void setZonghe(double zonghe) {
		this.zonghe = zonghe;
	}
	public int getPaiming() {
		return paiming;
	}
	public void setPaiming(int paiming) {
		this.paiming = paiming;
	}
	
}
