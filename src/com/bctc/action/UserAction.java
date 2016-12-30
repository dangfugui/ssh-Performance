package com.bctc.action;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import com.bctc.entity.Average;
import com.bctc.entity.Department;
import com.bctc.entity.KPI;
import com.bctc.entity.PowerGroup;
import com.bctc.entity.Proportion;
import com.bctc.entity.Quarter;
import com.bctc.entity.ResultYear;
import com.bctc.entity.Role;
import com.bctc.entity.User;
import com.bctc.entity.Year;
import com.bctc.service.AverageService;
import com.bctc.service.DepartmentService;
import com.bctc.service.KPIService;
import com.bctc.service.PowerGroupService;
import com.bctc.service.ProportionService;
import com.bctc.service.QuarterService;
import com.bctc.service.ResultYearService;
import com.bctc.service.RoleService;
import com.bctc.service.UserService;
import com.bctc.service.YearService;
import com.bctc.tool.MD5Util;
import com.bctc.tool.MySession;
import com.bctc.vo.UserKPI;
//@Component("u")
//@Scope("prototype")//多例
public class UserAction extends SuperAction{

	private static final long serialVersionUID = 1L;
	private User user=new User();
	private UserService userService;
	private DepartmentService departmentServict;
	private RoleService roleService;
	private QuarterService quarterService;
	private PowerGroupService powerGroupService;
	private YearService yearService;
	private ResultYearService resultYearService;
	private ProportionService proportionService;
	@Resource
	private AverageService averageService;
	private KPIService kpiService;
	private List<Quarter> quarterList;
	private List<Department> departmentlist;
	private List<Role> roleList;
	private String info;
	private List<User> list;
	private List<Year> yearList;
	private List<PowerGroup> powerGroupList;
	private Year year=new Year();
	private long departmentDid;
	private long roleRid;
	private long quarterQid;
	private long powerGroupPid;
	private Quarter quarter;
	private List<UserKPI> userKPIList=new ArrayList<UserKPI>();
	private Map<Long, Map<Long,Double>> map=new HashMap<Long,Map<Long,Double>>();
	private Map<Long,String> proportionMap=new HashMap<Long, String>();

	
	/**
	 * 退出登录
	 * @return jsp页面
	 */
	public String logout(){
		if(session.getAttribute(MySession.LOGINUSER)!=null){
			session.removeAttribute(MySession.LOGINUSER);
		}
		return "login";
	}
	
	/**
	 * 修改密码
	 * @return
	 */
	public String changePassword(){
		User u = (User) session.getAttribute(MySession.LOGINUSER);
		if(u.getPassword().equals(MD5Util.getMD5(user.getPassword()))){
			u.setPassword(info);
			userService.update(u);
			this.addActionMessage("密码修改成功");
			return SUCCESS;
		}else {
			info="密码错误";
		}
		return "user_change_password";
	}
	/**
	 * 跳到添加用户页面
	 * @return
	 */
	public String toAddUser(){
		initList();
		info="添加用户";
		return "user_add";
	}
	/**
	 * 跳到更改用户页面
	 * @return
	 */
	public String toUpdate(){
		if(!Tool.havePower("E1")){
			this.addFieldError("error", "权限不足");
			return "error";
		}
		user=userService.load(user.getUid());
		initList();
		info="修改信息";
		return "user_add";
	}
	/**
	 * 更改或添加用户信息
	 * @return
	 */
	public String addOrUpdate(){
		if(!Tool.havePower("E1")){
			this.addFieldError("error", "权限不足");
			return "error";
		}
		
		if (departmentDid != 0) {
			user.setDepartment(departmentServict.load(departmentDid));
		}
		if (roleRid != 0) {
			user.setRole(roleService.load(roleRid));
		}
		if (powerGroupPid != 0) {
			user.setPowerGroup(powerGroupService.load(powerGroupPid));
		}
		if (user.getUid() == 0) {
			userService.save(user);
		} else {
			userService.update(user);
			
		}
		return list();
	}
	
	/**
	 * 删除用户
	 * @return
	 */
	public String delete(){
		if(!Tool.havePower("E1")){
			this.addFieldError("error", "权限不足");
			return "error";
		}
	
			userService.delete(user);
		
		return list();
	}
	
	/**
	 * 显示用户列表
	 * @return
	 */
	public String list(){
		if(!Tool.havePower("E1")){
			this.addFieldError("error", "权限不足");
			return "error";
		}
		 initList();
		return "user_list";
	}
	/**
	 * 显示填写KPI 用户列表
	 * @return
	 */
	public String listForKPI(){
		if(!Tool.havePower("C2")){
			this.addFieldError("error", "权限不足");
			return "error";
		}
		if(0!=quarterQid){
			quarter= quarterService.load(quarterQid);
			session.setAttribute(MySession.QUARTER, quarter);
		}else {
			if(quarterService.list().size()>0){
				quarter=quarterService.list().get(0);
				session.setAttribute(MySession.QUARTER,quarter);
			}
		}
		initList();
		for(User u:list){
			KPI kpi = kpiService.load(quarter, u);
			if(null!=kpi){
				proportionMap.put(u.getUid(), ""+kpi.getContents().size());
			}else {
				proportionMap.put(u.getUid(), "0");
			}
		}
		return "kpi_user_list";
	}
	/**
	 * 显示某人需要填写的完成情况
	 * @return
	 */
	public String listForContent(){
		if(0!=quarterQid){
			session.setAttribute(MySession.QUARTER, quarterService.load(quarterQid));
		}else {
			if(quarterService.list().size()>0){
				Quarter quarter=quarterService.list().get(0);
				session.setAttribute(MySession.QUARTER,quarter);
			}
		}
		initList();
		User loginUser=(User) session.getAttribute(MySession.LOGINUSER);
		loginUser=userService.load(loginUser.getUid());
		return "fill_content_user_list";
	}
	/**
	 * 显示要拟定比例的人员列表
	 */
	public String listForProportion(){
		if(!Tool.havePower("E2")){
			this.addFieldError("error", "权限不足");
			return "error";
		}
		if(0!=quarterQid){
			quarter=quarterService.load(quarterQid);
			session.setAttribute(MySession.QUARTER, quarter);
		}else {
			if(quarterService.list().size()>0){
				quarter=quarterService.list().get(0);
				session.setAttribute(MySession.QUARTER,quarter);
			}
		}
		initList();
		for (User u : list) {
			List<Proportion> plist = proportionService.find(quarter, u.getUid());
			if(null!=plist){
				proportionMap.put(u.getUid(),""+plist.size());
			}
		}
		return "proportion_user_list";
	}
	
	/**
	 * 显示     年度        要拟定比例的人员列表  ####
	 */
	public String listForAverage(){
		if(!Tool.havePower("E2")){
			this.addFieldError("error", "权限不足");
			return "error";
		}
		System.out.println(year.getYid());
		if(0!=year.getYid()){
			year=yearService.load(year.getYid());
			session.setAttribute(MySession.YEAR, year);
		}else {
			if(yearService.list().size()>0){
				year=yearService.list().get(0);
				session.setAttribute(MySession.QUARTER,quarter);
			}
		}
		initList();
		yearList=yearService.list();
		for (User u : list) {
			List<Average> alist = averageService.find(year, u.getUid());
			if(null!=alist){
				proportionMap.put(u.getUid(),""+alist.size());
			}
		}
		return "average_user_list";
	}
	
	
	
	/**
	 * 显示要查看考核情况的人员列表
	 */
	public String listForResult(){
		if(!Tool.havePower("C4")){
			this.addFieldError("error", "权限不足");
			return "error";
		}
		if(0!=quarterQid){
			quarter= quarterService.load(quarterQid);
			session.setAttribute(MySession.QUARTER, quarter);
		}else {
			if(quarterService.list().size()>0){
				quarter=quarterService.list().get(0);
				session.setAttribute(MySession.QUARTER,quarter);
			}
		}
		initList();
		quarter = (Quarter) session.getAttribute(MySession.QUARTER);
		if(quarter==null){
			this.addFieldError("message", "没有创建季度");
			return ERROR;
		}
		//计算各级评的分数
		for (User u:list) {
			UserKPI userkpi=new UserKPI();
			userkpi.setUser(u);
			List<Proportion> prolist = proportionService.find(quarter, u.getUid());
			//Map<Double, Double> zuyaomap=new HashMap<Double,Double>();
			List<Double> zuyaomap1=new ArrayList<Double>();
			List<Double> zuyaomap2=new ArrayList<Double>();
			//Map<Double, Double> fenguan=new HashMap<Double,Double>();
			List<Double> fenguan1=new ArrayList<Double>();
			List<Double> fenguan2=new ArrayList<Double>();
			//Map<Double, Double> sijingli=new HashMap<Double,Double>();
			List<Double> sijingli1=new ArrayList<Double>();
			List<Double> sijingli2=new ArrayList<Double>();
			//Map<Double, Double> yuangong=new HashMap<Double,Double>();
			List<Double> yuangong1=new ArrayList<Double>();
			List<Double> yuangong2=new ArrayList<Double>();
			//HashMap<Double, Double> xiaji=new HashMap<Double,Double>();
			if(prolist!=null){
				for (Proportion pro : prolist) {
					int grade=0;
					if(pro!=null&&pro.getSourceUser()!=null&&pro.getSourceUser().getRole()!=null){
						grade=pro.getSourceUser().getRole().getGrade();
					}
					if(grade<50){
						zuyaomap1.add(pro.getProportion());
						zuyaomap2.add( pro.getTotal());
						//zuyaomap.put(pro.getProportion(), pro.getTotal());
					}else if(grade<70) {
						fenguan1.add(pro.getProportion());
						fenguan2.add( pro.getTotal());
						//fenguan.put(pro.getProportion(), pro.getTotal());
					}else if (grade<100) {
						sijingli1.add(pro.getProportion());
						sijingli2.add( pro.getTotal());
						//sijingli.put(pro.getProportion(), pro.getTotal());
					}else{
						yuangong1.add(pro.getProportion());
						yuangong2.add( pro.getTotal());
						//yuangong.put(pro.getProportion(), pro.getTotal());
					}
				}
			}
			double sum=0.0,proportion=0.0;
			for (int i=0;i< zuyaomap1.size();i++) {//主要负责人
				sum+=zuyaomap1.get(i)*zuyaomap2.get(i);
				proportion+=zuyaomap1.get(i);
			}
			
			userkpi.setZuyao(sum/proportion);sum=0.0;proportion=0.0;
			
			for (int i=0;i< fenguan1.size();i++) {//主要负责人
				sum+=fenguan1.get(i)*fenguan2.get(i);
				proportion+=fenguan1.get(i);
			}
			
			userkpi.setFenguang(sum/proportion);sum=0.0;proportion=0.0;
			
			for (int i=0;i< sijingli1.size();i++) {//主要负责人
				sum+=sijingli1.get(i)*sijingli2.get(i);
				proportion+=sijingli1.get(i);
			}
			
			userkpi.setSijingli(sum/proportion);sum=0.0;proportion=0.0;
			for (int i=0;i< yuangong1.size();i++) {//员工
				sum+=yuangong1.get(i)*yuangong2.get(i);
				proportion+=yuangong1.get(i);
			}
			
			userkpi.setYuangong(sum/proportion);sum=0.0;proportion=0.0;
			
			KPI kpi = kpiService.load(quarter, u);
			if(kpi!=null){
				userkpi.setZonghe(kpi.getResult());
			}
			userKPIList.add(userkpi);
					
		}
		return "result_user_list";
	}
	
	/**
	 * 下载要查看考核情况的人员列表
	 * @throws IOException 
	 * @throws WriteException 
	 * @throws RowsExceededException 
	 */
	public void listForResultDownload() throws IOException, RowsExceededException, WriteException{
		quarter=(Quarter) session.getAttribute(MySession.QUARTER);
		quarterQid = quarter.getQid();
		listForResult();
		String fname = "考核结果-"+quarter.getName();
		OutputStream os = response.getOutputStream();//取得输出流
		response.reset();//清空输出流
		//下面是对中文文件名的处理
		response.setCharacterEncoding("UTF-8");//设置相应内容的编码格式
		fname = java.net.URLEncoder.encode(fname,"UTF-8");
		response.setHeader("Content-Disposition","attachment;filename="+new String("download-quarter")+".xls");
		response.setContentType("application/msexcel");//定义输出类型
		  //创建工作薄
        WritableWorkbook workbook = Workbook.createWorkbook(os);
        //创建新的一页
        WritableSheet sheet = workbook.createSheet("First Sheet",0);
        //创建要显示的内容,创建一个单元格，第一个参数为列坐标，第二个参数为行坐标，第三个参数为内容
        String[] tatle={"部门","姓名","主要负责人","分管负责人","室经理","员工","综合成绩"};
        for (int i = 0; i < tatle.length; i++) {
        	 Label xuexiao = new Label(i,0,tatle[i]);
             sheet.addCell(xuexiao);
		}
        for (int h = 0; h < userKPIList.size(); h++) {
			UserKPI u=userKPIList.get(h);
			sheet.addCell(new Label(0,h+1,u.getUser().getDepartment().getName()));
			sheet.addCell(new Label(1,h+1,u.getUser().getName()));
			sheet.addCell(new Label(2,h+1,""+u.getZuyao()));
			sheet.addCell(new Label(3,h+1,""+u.getFenguang()));
			sheet.addCell(new Label(4,h+1,""+u.getSijingli()));
			sheet.addCell(new Label(5,h+1,""+u.getYuangong()));
			sheet.addCell(new Label(6,h+1,""+u.getZonghe()));
		}
        //把创建的内容写入到输出流中，并关闭输出流
        workbook.write();
        workbook.close();
        os.close();   
	}
	/**
	 * 查看互评详表
	 */
	public String listForDetail(){
		if(0!=quarterQid){
			quarter= quarterService.load(quarterQid);
			session.setAttribute(MySession.QUARTER, quarter);
		}else {
			if(quarterService.list().size()>0){
				quarter=quarterService.list().get(0);
				session.setAttribute(MySession.QUARTER,quarter);
			}
		}
		if(departmentDid==0){
			departmentDid=Tool.getUser().getDepartment().getDid();
		}
		initList();
		for(User u:list){
			Map<Long, Double> m=new HashMap<Long, Double>();
			for (User x:list) {
				Proportion p = proportionService.find(quarter, u.getUid(), x.getUid());
				if(p!=null){
					m.put(x.getUid(), p.getTotal());
				}
			}
			map.put(u.getUid(), m);
		}
		return "result_detail_list";
	}
	/**
	 * 互评详表的下载
	 * @throws IOException
	 * @throws RowsExceededException
	 * @throws WriteException
	 */
	public void listForDetailDownload() throws IOException, RowsExceededException, WriteException{
		quarter = (Quarter) session.getAttribute(MySession.QUARTER);
		quarterQid = quarter.getQid();
		listForDetail();
		String fname = "互评详表-"+quarter.getName();
		OutputStream os = response.getOutputStream();//取得输出流
		response.reset();//清空输出流
		//下面是对中文文件名的处理
		response.setCharacterEncoding("UTF-8");//设置相应内容的编码格式
		fname = java.net.URLEncoder.encode(fname,"UTF-8");
		response.setHeader("Content-Disposition","attachment;filename="+new String("download")+".xls");
		response.setContentType("application/msexcel");//定义输出类型
		  //创建工作薄
        WritableWorkbook workbook = Workbook.createWorkbook(os);
        //创建新的一页
        WritableSheet sheet = workbook.createSheet("First Sheet",0);
        //创建要显示的内容,创建一个单元格，第一个参数为列坐标，第二个参数为行坐标，第三个参数为内容
       // String[] tatle={"部门","姓名"};
        sheet.addCell(new Label(0,0,"部门"));
        sheet.addCell(new Label(1,0,"姓名"));
        for (int i = 0; i < list.size(); i++) {
        	 Label xuexiao = new Label(i+2,0,list.get(i).getName());
             sheet.addCell(xuexiao);
		}
        for (int h = 0; h < list.size(); h++) {
        	sheet.addCell(new Label(0,h+1,list.get(h).getDepartment().getName()));
        	sheet.addCell(new Label(1,h+1,list.get(h).getName()));
        	for (int i = 0; i <list.size(); i++) {
        		//Double value = map.get(list.get(h).getUid()).get(list.get(i).getUid());
        		Map<Long, Double> map1 = map.get(list.get(h).getUid());
        		if(null!=map1){
        			Double value = map1.get(list.get(i).getUid());
        			if(null!=value){
        				sheet.addCell(new Label(i+2,h+1,value.toString()));
        			}
        		}
        		//sheet.addCell(new Label(i+2,h+1,"d"));
			}
		}
        
        //把创建的内容写入到输出流中，并关闭输出流
        workbook.write();
        workbook.close();
        os.close();   

	}
	
	/**
	 * 显示要查看年度考核情况的人员列表
	 */
	public String listForYearScore(){
		if(!Tool.canYearResult()){
			this.addFieldError("error", "权限不足");
			return "error";
		}
		if(year.getYid()!=0){
			year = yearService.load(year.getYid());
			session.setAttribute(MySession.YEAR, year);
		}else {
			if(Tool.getYear()==null){
				
				this.addFieldError("error", "没有默认年度");
				return ERROR;
			}
			year=yearService.load(Tool.getYear().getYid());
		}
		yearList=yearService.list();
		initList();
		List<User> mylist=new ArrayList<User>();
		for (User u : list) {
			ResultYear resultYear = resultYearService.load(year, u);
			User us=new User();
			us.setUid(u.getUid());
			us.setName(u.getName());
			us.setRole(u.getRole());
			us.setDepartment(u.getDepartment());
			if(resultYear!=null){
				us.setState(resultYear.getResult()+"");
			}else {
				us.setState("未打分");
			}
			mylist.add(us);
		}
		list=mylist;
		return "year_score_user_list";
	}
	public void listForYearDownload() throws IOException, RowsExceededException, WriteException{
	
		year=(Year) session.getAttribute(MySession.YEAR);
		
		String fname = "考核结果-"+year.getName();
		OutputStream os = response.getOutputStream();//取得输出流
		response.reset();//清空输出流
		//下面是对中文文件名的处理
		response.setCharacterEncoding("UTF-8");//设置相应内容的编码格式
		fname = java.net.URLEncoder.encode(fname,"UTF-8");
		response.setHeader("Content-Disposition","attachment;filename="+new String("download-year")+".xls");
		response.setContentType("application/msexcel");//定义输出类型
		  //创建工作薄
        WritableWorkbook workbook = Workbook.createWorkbook(os);
        //创建新的一页
        WritableSheet sheet = workbook.createSheet("First Sheet",0);
        //创建要显示的内容,创建一个单元格，第一个参数为列坐标，第二个参数为行坐标，第三个参数为内容
        String[] tatle={"姓名","部门","角色","总分"};
        for (int i = 0; i < tatle.length; i++) {
        	 Label xuexiao = new Label(i,0,tatle[i]);
             sheet.addCell(xuexiao);
		}
        initList();
        for (int h = 0; h < list.size(); h++) {
			User u=list.get(h);
			ResultYear resultYear = resultYearService.load(year, u);
			sheet.addCell(new Label(0,h+1,u.getName()));
			sheet.addCell(new Label(1,h+1,u.getDepartment().getName()));
			sheet.addCell(new Label(2,h+1,""+u.getRole().getName()));
			if(resultYear!=null){
				sheet.addCell(new Label(3,h+1,""+resultYear.getResult()));
			}else {
				sheet.addCell(new Label(3,h+1,"未打分"));
			}
		}
        //把创建的内容写入到输出流中，并关闭输出流
        workbook.write();
        workbook.close();
        os.close();   
	}
	
	/**
	 * 添加要返回的列表
	 */
	private void initList(){
		if(Tool.getUser().getPowerGroup().getPowerString().contains("A4")){//角色比自己小的		//仅本部门
			departmentDid=Tool.getUser().getDepartment().getDid();	
			departmentlist=new ArrayList<Department>();
			departmentlist.add(Tool.getUser().getDepartment());
			roleList = roleService.listByGrade(Tool.getUser().getRole().getGrade());
			list = userService.listByGrade(user.getName(),departmentDid,roleRid,Tool.getUser().getRole().getGrade());
		}else if (Tool.getUser().getPowerGroup().getPowerString().contains("A3")) {//非本部门
			departmentlist = departmentServict.list();
			roleList=roleService.list();
			list = userService.listExcludeDepartment(Tool.getUser().getDepartment(),user.getName(),departmentDid,roleRid);
			powerGroupList=powerGroupService.list();
		}else if (Tool.getUser().getPowerGroup().getPowerString().contains("A2")) {//仅本部门
			departmentDid=Tool.getUser().getDepartment().getDid();	
			departmentlist=new ArrayList<Department>();
			departmentlist.add(Tool.getUser().getDepartment());
			roleList = roleService.list();
			list = userService.list(user.getName(),departmentDid,roleRid);
			
		}else if(Tool.getUser().getPowerGroup().getPowerString().contains("A1")){//超级管理员
			departmentlist = departmentServict.list();
			roleList=roleService.list();
			list = userService.list(user.getName(),departmentDid,roleRid);
			powerGroupList=powerGroupService.list();
		}else{
			List<KPI> kpilist = kpiService.getMyFillKPI(Tool.getUser(),Tool.getQuarter());
			list=new ArrayList<User>();
			if(kpilist!=null){
				for(KPI kpi:kpilist){
					list.add(kpi.getAimUser());
				}
			}
		}
		quarterList=quarterService.list();
	}

	
////////////////////////////////////////////////////////////////////////////////////////	
	
	@Resource
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	public YearService getYearService() {
		return yearService;
	}
	@Resource
	public void setYearService(YearService yearService) {
		this.yearService = yearService;
	}

	@Resource
	public void setDepartmentServict(DepartmentService departmentServict) {
		this.departmentServict = departmentServict;
	}
	@Resource
	public void setRoleService(RoleService roleService) {
		this.roleService = roleService;
	}
	@Resource
	public void setQuarterService(QuarterService quarterService) {
		this.quarterService = quarterService;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public List<User> getList() {
		return list;
	}
	public void setList(List<User> list) {
		this.list = list;
	}
	public List<Department> getDepartmentlist() {
		return departmentlist;
	}
	public void setDepartmentlist(List<Department> departmentlist) {
		this.departmentlist = departmentlist;
	}
	public List<Role> getRoleList() {
		return roleList;
	}
	public void setRoleList(List<Role> roleList) {
		this.roleList = roleList;
	}

	public List<Quarter> getQuarterList() {
		return quarterList;
	}

	public void setQuarterList(List<Quarter> quarterList) {
		this.quarterList = quarterList;
	}

	public long getDepartmentDid() {
		return departmentDid;
	}

	public void setDepartmentDid(long departmentDid) {
		this.departmentDid = departmentDid;
	}

	public long getRoleRid() {
		return roleRid;
	}

	public void setRoleRid(long roleRid) {
		this.roleRid = roleRid;
	}

	public long getQuarterQid() {
		return quarterQid;
	}

	public void setQuarterQid(long quarterQid) {
		this.quarterQid = quarterQid;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public UserService getUserService() {
		return userService;
	}

	public DepartmentService getDepartmentServict() {
		return departmentServict;
	}

	public RoleService getRoleService() {
		return roleService;
	}

	public QuarterService getQuarterService() {
		return quarterService;
	}

	public List<Year> getYearList() {
		return yearList;
	}

	public void setYearList(List<Year> yearList) {
		this.yearList = yearList;
	}

	public Year getYear() {
		return year;
	}

	public void setYear(Year year) {
		this.year = year;
	}

	public ResultYearService getResultYearService() {
		return resultYearService;
	}
	@Resource
	public void setResultYearService(ResultYearService resultYearService) {
		this.resultYearService = resultYearService;
	}

	public List<UserKPI> getUserKPIList() {
		return userKPIList;
	}

	public void setUserKPIList(List<UserKPI> userKPIList) {
		this.userKPIList = userKPIList;
	}

	public ProportionService getProportionService() {
		return proportionService;
	}
	@Resource
	public void setProportionService(ProportionService proportionService) {
		this.proportionService = proportionService;
	}

	public Quarter getQuarter() {
		return quarter;
	}

	public void setQuarter(Quarter quarter) {
		this.quarter = quarter;
	}

	public KPIService getKpiService() {
		return kpiService;
	}
	@Resource
	public void setKpiService(KPIService kpiService) {
		this.kpiService = kpiService;
	}

	

	public Map<Long, Map<Long, Double>> getMap() {
		return map;
	}

	public void setMap(Map<Long, Map<Long, Double>> map) {
		this.map = map;
	}

	public List<PowerGroup> getPowerGroupList() {
		return powerGroupList;
	}

	public void setPowerGroupList(List<PowerGroup> powerGroupList) {
		this.powerGroupList = powerGroupList;
	}

	public long getPowerGroupPid() {
		return powerGroupPid;
	}

	public void setPowerGroupPid(long powerGroupPid) {
		this.powerGroupPid = powerGroupPid;
	}

	public PowerGroupService getPowerGroupService() {
		return powerGroupService;
	}
	@Resource
	public void setPowerGroupService(PowerGroupService powerGroupService) {
		this.powerGroupService = powerGroupService;
	}

	public Map<Long, String> getProportionMap() {
		return proportionMap;
	}

	public void setProportionMap(Map<Long, String> proportionMap) {
		this.proportionMap = proportionMap;
	}

	public AverageService getAverageService() {
		return averageService;
	}

	public void setAverageService(AverageService averageService) {
		this.averageService = averageService;
	}
	
	
}
