package com.bctc.tool;

public class Config {
	/**
	 * 中高级
	 */
	public static final Double senior[]  ={		12.5,	17.5,	22.5	}; 	//中高级	角色等级<50
	public static final Double middle[]  ={		10.0,	15.0,	20.0	};	//中级	50<角色等级<100
	public static final Double staff[]   ={		7.5,	12.5,	17.5	};	//员工	100<角色等级
	//默认0.0 所以省略0
	public static final Double standard[]={		60.0,	80.0,	100.0	};	//对用标准分
	
	
	/**
	 * 年度总成绩任务绩效和行为绩效所占比例
	 */
//	public static final Double year=0.2;		//年度成绩所占比例
//	public static final Double quarters=0.8;	//季度成绩所占比例
	
	
	private Config(){}
	
}
