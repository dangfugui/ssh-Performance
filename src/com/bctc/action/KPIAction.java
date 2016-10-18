package com.bctc.action;


import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import com.bctc.entity.ContentKPI;
import com.bctc.entity.Department;
import com.bctc.entity.KPI;
import com.bctc.entity.Proportion;
import com.bctc.entity.Quarter;
import com.bctc.entity.ScoreKPI;
import com.bctc.entity.User;
import com.bctc.service.DepartmentService;
import com.bctc.service.KPIService;
import com.bctc.service.ProportionService;
import com.bctc.service.QuarterService;
import com.bctc.service.ScoreKPIService;
import com.bctc.service.UserService;
import com.bctc.tool.MySession;
import com.bctc.vo.UserKPI;

public class KPIAction extends SuperAction {

	private static final long serialVersionUID = 1L;
	private KPIService kpiService;
	private UserService userService;
	private DepartmentService departmentService;
	private QuarterService quarterService;
	private ProportionService proportionService;
	private ScoreKPIService scoreKPIService;	
	private ContentKPI contentKPI=new ContentKPI();
	private KPI kpi;
	//private List<ContentKPI> list=new ArrayList<ContentKPI>();
	private List<Department> departmentlist;
	private User user=new User();
	private int sourceUserUid;
	private String info;
	private List<ContentKPI> contentKPIs;
	private Quarter quarter=new Quarter();
	private List<KPI> kpiList;
	private List<Quarter> quarterList;
	private List<Proportion> proportionList;//评分详情里 显示评分比例
	private List<KPI> templates;
	private List<Long> userIdList=new ArrayList<Long>();
	private Map<Long, Double> kpiContentScore=new HashMap<Long, Double>();
	private List<User> sourceUserList=new ArrayList<User>();
	private User sourceUser=new User();
	@Override
	public String execute() throws Exception {
		
		return SUCCESS;
	}
	
	public void updateByRoot(){
		System.out.println(quarter.getQid());
		System.out.println(contentKPI.getCid());
		System.out.println(sourceUser.getUid());
		//contentKP
		ScoreKPI s = scoreKPIService.find(contentKPI, sourceUser);
		s.setScore(contentKPI.getResult());
		scoreKPIService.update(s);
	}
	
	
	/**
	 * 打开某个人的指标列别
	 * */
	public String toKPI(){
		if(!Tool.havePower("C2")){
			this.addFieldError("error", "权限不足");
			return "error";
		}
		if(null!=user&& user.getUid()!=0){		//默认打开自己的指标
			user=userService.load(user.getUid());
			session.setAttribute(MySession.KPIUSER, user);	
		}else {
			user=(User) session.getAttribute(MySession.KPIUSER);
		}
		
		Quarter quarter=(Quarter) session.getAttribute(MySession.QUARTER);
		KPI kpi = kpiService.load(quarter,user);
		if(null==kpi){
			User loginUser=(User)session.getAttribute(MySession.LOGINUSER);
			if(loginUser.getUid()==user.getUid()){
				this.addFieldError("error", "不能拟定自己的指标");
				return "error";
			}
			kpi=new KPI(quarter, user,loginUser);
			kpiService.saveOrUpdate(kpi);
			session.setAttribute(MySession.KPI, kpi);
		}else {
			session.setAttribute(MySession.KPI, kpi);
		}
		departmentlist=departmentService.list();
		templates=kpiService.listTemplate(Tool.getUser());
		return "kpi_content_list"; 
	}

	/**
	 * 跳到添加用户页面
	 */
	public String toAddKPI(){
		if(!Tool.havePower("C2")){
			this.addFieldError("error", "权限不足");
			return "error";
		}
		info="添加KPI指标";
		departmentlist=departmentService.list();
		return "kpi_content_add_kpi";
	}
	public String toAddGS(){
		if(!Tool.havePower("C2")){
			this.addFieldError("error", "权限不足");
			return "error";
		}
		info="添加GS指标";
		departmentlist=departmentService.list();
		return "kpi_content_add_gs";
	}
	/**
	 * 添加或更改kpi内容
	 */
	public String addOrUpdateContent(){
		if(!Tool.havePower("C2")){
			this.addFieldError("error", "权限不足");
			return "error";
		}
		kpi=(KPI)session.getAttribute(MySession.KPI);
		contentKPI.setKpi(kpi);
		if(sourceUserUid==0){
			contentKPI.setSourceUser(kpi.getAimUser());
		}else {
			contentKPI.setSourceUser(userService.load(sourceUserUid));
		}
		kpiService.saveOrUpdateContent(contentKPI);
		if(kpi.getAimUser().getUid()==kpi.getFillUser().getUid()){	//如果是模板
			KPI newkpi=kpiService.load(kpi.getKid());
			kpi=newkpi;
			return listTemplate(); 	
		}
		return toKPI();
	}
	/**
	 * 删除kpi指标内容行
	 */
	public String delete(){
		kpiService.delete(contentKPI);
		if(null!=kpi&&kpi.getAimUser().getUid()==kpi.getFillUser().getUid()){	//如果是模板
			kpi=kpiService.load(kpi.getKid());
			return listTemplate(); 	
		}
		return toKPI();
	}
	/**
	 * 跳到更改用户页面
	 */
	public String toUpdateKPI(){
		if(!Tool.havePower("C2")){
			this.addFieldError("error", "权限不足");
			return "error";
		}
		info="更改指标";
		departmentlist=departmentService.list();
		contentKPI=kpiService.loadContent(contentKPI.getCid());
		return "kpi_content_add_kpi";
	}
	public String toUpdateGS(){
		if(!Tool.havePower("C2")){
			this.addFieldError("error", "权限不足");
			return "error";
		}
		info="更改指标";
		departmentlist=departmentService.list();
		contentKPI=kpiService.loadContent(contentKPI.getCid());
		return "kpi_content_add_gs";
	}
	/**
	 * 设置拟KPI人
	 */
	public void setFillUser(){
		if(!Tool.havePower("C2")){
			this.addFieldError("error", "权限不足");
			return;
		}
		if(user.getUid()==0){
			user=(User) session.getAttribute(MySession.LOGINUSER);
		}
		kpi=(KPI) session.getAttribute(MySession.KPI);
		kpi.setFillUser(user);
		kpiService.update(kpi);
	}
	/**
	 * 显示我要填写的完成情况  值列表
	 */
	public String listForFillContent(){
		
		Quarter quarter=(Quarter)session.getAttribute(MySession.QUARTER);
		if(null==quarter){
			this.addFieldError("error","没用创建季度");
			return ERROR;
		}
		if(user.getUid()==0){
			user=Tool.getUser();
		}
		user=userService.load(user.getUid());
		contentKPIs=kpiService.findContentKPIs(user.getUid(),quarter.getQid());
		return "fill_content_list";
	}
	/**
	 * 打开填写KPI完成情况页面
	 */
	public String toFillContentKPI(){
		/*if(!Tool.havePower("C2")){
			this.addFieldError("error", "权限不足");
			return "error";
		}*/
		contentKPI=kpiService.loadContent(contentKPI.getCid());
		departmentlist=departmentService.list();
		return "fill_kpi_content_actual";
	}
	public String toFillContentGS(){
		contentKPI=kpiService.loadContent(contentKPI.getCid());
		departmentlist=departmentService.list();
		return "fill_gs_content_actual";
	}
	/**
	 * 更改完成情况
	 */
	public String fillContent(){
		ContentKPI c =kpiService.loadContent(contentKPI.getCid());
		User u;
		if(sourceUserUid!=0){
			u=userService.load(sourceUserUid);
		}else{
			u=c.getKpi().getAimUser();
		}
		c.setSourceUser(u);
		c.setActual(contentKPI.getActual());
		kpiService.saveOrUpdateContent(c);
		return listForFillContent();
	}
	
	/**
	 * 去  进入打分列表
	 */
	public String toScoreKPI(){
		quarter=(Quarter) session.getAttribute(MySession.QUARTER);
		kpi = kpiService.load(quarter, user);
		if(null!=kpi){
			for (ContentKPI contentKPI : kpi.getContents()) {
				ScoreKPI score = scoreKPIService.load(contentKPI.getCid(), ((User)session.getAttribute(MySession.LOGINUSER)).getUid());
				if(score!=null){
					kpiContentScore.put(contentKPI.getCid(), score.getScore());
				}
				//System.out.println(kpiContentScore.get(contentKPI.getCid()));
			}
		}
		return "score_content_list";
	}
	/**
	 * 下载我评分的表
	 * @throws IOException 
	 * @throws WriteException 
	 * @throws RowsExceededException 
	 */
	public void toDownloadScoreKPI() throws IOException, RowsExceededException, WriteException{
		quarter=(Quarter) session.getAttribute(MySession.QUARTER);
		kpi = kpiService.load(quarter, user);
		
		OutputStream os = response.getOutputStream();//取得输出流
		response.reset();//清空输出流
		//下面是对中文文件名的处理
		response.setCharacterEncoding("UTF-8");//设置相应内容的编码格式
		response.setHeader("Content-Disposition","attachment;filename="+new String("download-myfile")+".xls");
		response.setContentType("application/msexcel");//定义输出类型
		  //创建工作薄
        WritableWorkbook workbook = Workbook.createWorkbook(os);
      
        WritableCellFormat wcf = new WritableCellFormat();
        wcf.setWrap(true);//通过调整宽度和高度自动换行
        //创建新的一页
        WritableSheet sheet = workbook.createSheet("First Sheet",0);
        sheet.setColumnView(0,10);//设置第2列宽度为10
        sheet.setColumnView(1,10);//设置第2列宽度为10
        sheet.setColumnView(2,30);//设置第2列宽度为10
        sheet.setColumnView(3,30);//设置第2列宽度为10
        sheet.setColumnView(4,40);//设置第2列宽度为10
        sheet.setColumnView(5,10);//设置第2列宽度为10
        sheet.setColumnView(6,30);//设置第2列宽度为10
        sheet.addCell(new Label(0,0,"季度:",wcf));
        sheet.addCell(new Label(1,0,kpi.getQuarter().getName()));
        sheet.addCell(new Label(2,0,"被考核人:",wcf));
        sheet.addCell(new Label(3,0,kpi.getAimUser().getName()));
        sheet.addCell(new Label(4,0,"拟表人:",wcf));
        sheet.addCell(new Label(5,0,kpi.getFillUser().getName()));
        sheet.addCell(new Label(6,0,"填表人:",wcf));
        sheet.addCell(new Label(7,0,Tool.getUser().getName()));
        //创建要显示的内容,创建一个单元格，第一个参数为列坐标，第二个参数为行坐标，第三个参数为内容
        String[] tatle={"指标名称","权重","定义、计算公式","目标值","计分方法","数据来源","实际完成情况","得分"};
        User u=(User)session.getAttribute(MySession.LOGINUSER);
        for (int i = 0; i < tatle.length; i++) {
             sheet.addCell(new Label(i,2,tatle[i],wcf));
		}
        int h=3;
        double sum=0.0;
        for(ContentKPI kpi:kpi.getContents()){
        	if(kpi.getState().equals("kpi")){
        		sheet.addCell(new Label(0,h,kpi.getName(),wcf));
        		sheet.addCell(new Label(1,h,""+kpi.getWeight(),wcf));
        		sheet.addCell(new Label(2,h,kpi.getDefinde(),wcf));
        		sheet.addCell(new Label(3,h,kpi.getAim(),wcf));
        		sheet.addCell(new Label(4,h,kpi.getProcess(),wcf));
        		sheet.addCell(new Label(5,h,kpi.getSourceUser().getName(),wcf));
        		sheet.addCell(new Label(6,h,kpi.getActual(),wcf));
        		ScoreKPI score = scoreKPIService.load(kpi.getCid(),u.getUid());
        		if(score!=null){
        			sum+=score.getScore();
        			sheet.addCell(new Label(7,h,""+score.getScore(),wcf));
        		}
        		h++;
        	}
        }
        h++;
        String[] tatle2={"指标名称","权重","关键行动计划及评分方法","数据来源","实际完成情况","得分"};
       
        for (int i = 0; i < tatle2.length; i++) {
             sheet.addCell(new Label(i,h,tatle2[i]));
		}
        h++;
        for(ContentKPI kpi:kpi.getContents()){
        	if(kpi.getState().equals("gs")){
        		sheet.addCell(new Label(0,h,kpi.getName(),wcf));
        		sheet.addCell(new Label(1,h,""+kpi.getWeight(),wcf));
        		sheet.addCell(new Label(2,h,kpi.getDefinde(),wcf));
        		sheet.addCell(new Label(3,h,kpi.getSourceUser().getName(),wcf));
        		sheet.addCell(new Label(4,h,kpi.getActual(),wcf));
        		ScoreKPI score = scoreKPIService.load(kpi.getCid(),u.getUid());
        		if(score!=null){
        			sum+=score.getScore();
        			sheet.addCell(new Label(7,h,""+score.getScore(),wcf));
        		}
        		h++;
        	}
        }
        sheet.addCell(new Label(8,0,"总分:",wcf));
        sheet.addCell(new Label(9,0,""+sum));
        //把创建的内容写入到输出流中，并关闭输出流
        workbook.write();
        workbook.close();
        os.close();   
	}
	

	/**
	 * 查看最终得分
	 */
	public String kpiResult(){
		if(Tool.havePower("C4")){
			///
		}else if(Tool.havePower("B5")){
			user.setUid(0);
		}else{
			this.addFieldError("error", "权限不足");
			return "error";
		}
		quarterList=quarterService.list();
		if(quarter.getQid()==0){
			quarter=(Quarter) session.getAttribute(MySession.QUARTER);
		}else {
			quarter=quarterService.load(quarter.getQid());
			session.setAttribute(MySession.QUARTER, quarter);
		}
		if(null==user||user.getUid()==0){		//默认查询自己的
			user=(User) session.getAttribute(MySession.LOGINUSER);
		}else {
			if(!Tool.canQuarterResult()){
				this.addFieldError("error", "权限不足");
				return "error";
			}
		}
		user=userService.load(user.getUid());
		for (KPI k : user.getMyKPIs()) {
			if(k.getQuarter().getQid()==quarter.getQid()){
				kpi=k;
				break;
			}
		}
		List<Proportion> plist = proportionService.find(quarter, user.getUid());
		for(Proportion p:plist){
			User u=new User();
			u.setUid(p.getSourceUser().getUid());
			u.setName(p.getSourceUser().getName()+"  "+p.getProportion()+"%");
			sourceUserList.add(u);
		}
		if(sourceUser.getUid()==0){	//默认查询汇总分
			if(null!=kpi){
				kpiContentScore.put((long) 0,kpi.getResult());
				for(ContentKPI c:kpi.getContents()){
					kpiContentScore.put(c.getCid(), c.getResult());
				}
			}
		}else {
			kpiContentScore.clear();
			List<ScoreKPI> scoreList = scoreKPIService.find(quarter, user, sourceUser);
			double result = 0;
			for (ScoreKPI score:scoreList) {
				if(score.getContentKPI()!=null&&score!=null){
					kpiContentScore.put(score.getContentKPI().getCid(),score.getScore());
				}
				result+=score.getScore();
			}
			kpiContentScore.put((long) 0, result);
		}
		
		return "kpi_result";
	}
	/**
	 * 计算汇总成绩
	 */
	public void calculateKPI(){
		if(!Tool.canQuarterResult()){
			return;
		}
		quarter=(Quarter) session.getAttribute(MySession.QUARTER);
		quarter=quarterService.load(quarter.getQid());
		Set<KPI> kpisSet = quarter.getKpis();
		//处理每个KPI表的成绩
		for (KPI kpi : kpisSet) {
			List<ContentKPI> contents = kpi.getContents();
			//处理每个content表
			for (ContentKPI contentKPI : contents) {
				Set<ScoreKPI> scores = contentKPI.getScores();
				double contentResult=0.0;
				//计算每个指标的总分
				for (ScoreKPI scoreKPI : scores) {
					Proportion proportion=proportionService.
							find(quarter,kpi.getAimUser().getUid(), scoreKPI.getFillUser().getUid());
					if(null!=proportion){
						contentResult+=scoreKPI.getScore()*proportion.getProportion();
					}
				} //for score
				contentKPI.setResult(contentResult/100);
				kpiService.update(contentKPI);	
			}//for  contents			
		}//for  kpisSet		
		//填写kpi result
		quarter=quarterService.load(quarter.getQid());
		for(KPI kpi:quarter.getKpis()){
			double result=0.0;
			for(ContentKPI c:kpi.getContents()){
				result=c.getResult()+result;//result=c.getResult()*c.getWeight()+result;	成绩满分变成比例
			}
			kpi.setResult(result);
			kpiService.update(kpi);
		}
		for(Proportion p:quarter.getProportions()){
			List<ScoreKPI> list = scoreKPIService.find(p.getQuarter(),p.getAimUser(),p.getSourceUser());
			double total=0.0;
			if(list!=null){
				for (ScoreKPI s : list) {
					total+=s.getScore();
				}
			}
			p.setTotal(total);
			proportionService.update(p);
		}
	}
	/**
	 * 显示某contentKPI 得分详情
	 * @return 
	 */
	public String lookScore(){
		if(!Tool.canQuarterResult()){
			this.addFieldError("error", "权限不足");
			return "error";
		}
		contentKPI=kpiService.loadContent(contentKPI.getCid());
		if(null==contentKPI.getKpi().getAimUser().getRole()||contentKPI.getKpi().getAimUser().getRole().getGrade()<=Tool.getUser().getRole().getGrade()){
			this.addFieldError("error", "权限不足");
			return "error";
		}
		proportionList = proportionService.find(contentKPI.getKpi().getQuarter(), contentKPI.getKpi().getAimUser().getUid());
		return "look_score";
	}
	
	
	 /**
	  * 拷贝某季度指标到指定用户
	  * @return
	  */
	public String copy(){
		if(!Tool.havePower("C2")){
			this.addFieldError("error", "权限不足");
			return "error";
		}
		if(quarter.getQid()==0||quarter==null) {
			this.addFieldError("error", "季度为空");
			return "error";
		}
		quarter=quarterService.load(quarter.getQid());
		System.out.println(quarter.getName());
		Quarter lastQuarter=quarterService.list().get(0);
	
		
		for (long uid : userIdList) {
			System.out.println(uid);
			user=userService.load(uid);
			kpi=kpiService.load(quarter, user);
			KPI newkpi=kpiService.load(lastQuarter, user);
			if(newkpi==null){
				newkpi=new KPI(lastQuarter, kpi.getAimUser(),kpi.getFillUser());
			}else {
				newkpi.setFillUser(kpi.getFillUser());
			}
			kpiService.saveOrUpdate(newkpi);
			if(kpi!=null){
				for (ContentKPI c : kpi.getContents()) {
					System.out.println(c.getName());
					ContentKPI contentKPI=new ContentKPI
							(newkpi,c.getName(),c.getWeight(),c.getDefinde(),c.getAim(),c.getProcess(),c.getSourceUser(),c.getState());
					kpiService.saveOrUpdateContent(contentKPI);
				}
			}
			
		}
		this.addActionMessage("复用成功");
		return SUCCESS;
	}
	/**
	 * 添加指标模板	当目aimUser==fillUser时 是模板   模板名用state标志
	 * @return 
	 */
	public String addOrUpdateTemplate(){
		kpi.setQuarter(Tool.getQuarter());
		user=Tool.getUser();
		kpi.setAimUser(user);
		kpi.setFillUser(user);	//当目aimUser==fillUser时 是模板
		kpiService.saveOrUpdate(kpi);
		return listTemplate();
	}
	
	public String deleteTemplate(){
		if(!Tool.havePower("C2")){
			this.addFieldError("error", "权限不足");
			return "error";
		}
		kpiService.delete(kpi);
		return listTemplate();
		
	}

	/**
	 * 显示我的模板列表
	 * @return 
	 */
	public String listTemplate(){
		if(!Tool.havePower("C3")){
			this.addFieldError("error", "权限不足");
			return "error";
		}
		user=Tool.getUser();
		kpiList=kpiService.listTemplate(user);
		return "kpi_template_list";//显示我的模板列表
	}
	/**
	 * 查看模板内容
	 * @return 
	 */
	public String toTemplate(){
		kpi = kpiService.load(kpi.getKid());	
		session.setAttribute(MySession.KPI, kpi);
		departmentlist=departmentService.list();
		return "kpi_template_content_list"; 
	}
	/**
	 *拷贝模板内容到用户指标
	 * @return
	 */
	public String copyTemplate(){
		KPI template=kpiService.load(kpi.getKid());
		kpi=(KPI) session.getAttribute(MySession.KPI);
		kpi=kpiService.load(kpi.getKid());		//正编辑的kpi
		for (ContentKPI c : template.getContents()) {
			ContentKPI contentKPI=null;
			if(c.getSourceUser().getUid()==template.getAimUser().getUid()){//数据来源是自己
				contentKPI=new ContentKPI(kpi, c.getName(),c.getWeight(), c.getDefinde(), c.getAim(), c.getProcess(), kpi.getAimUser(), c.getState());
			}else{
				contentKPI=new ContentKPI(kpi, c.getName(),c.getWeight(), c.getDefinde(), c.getAim(), c.getProcess(), c.getSourceUser(), c.getState());
			}
			
			kpiService.save(contentKPI);
		}
		user=kpi.getAimUser();
		return toKPI();
	}
	
	
	
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	@Resource
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	@Resource
	public void setKpiService(KPIService kpiService) {
		this.kpiService = kpiService;
	}
	
	public List<Department> getDepartmentlist() {
		return departmentlist;
	}
	
	public void setDepartmentlist(List<Department> departmentlist) {
		this.departmentlist = departmentlist;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public ContentKPI getContentKPI() {
		return contentKPI;
	}

	public void setContentKPI(ContentKPI contentKPI) {
		this.contentKPI = contentKPI;
	}

	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	public KPI getKpi() {
		return kpi;
	}
	public void setKpi(KPI kpi) {
		this.kpi = kpi;
	}
	public int getSourceUserUid() {
		return sourceUserUid;
	}
	public void setSourceUserUid(int sourceUserUid) {
		this.sourceUserUid = sourceUserUid;
	}
	
	public List<ContentKPI> getContentKPIs() {
		return contentKPIs;
	}
	public void setContentKPIs(List<ContentKPI> contentKPIs) {
		this.contentKPIs = contentKPIs;
	}
	public Quarter getQuarter() {
		return quarter;
	}
	public void setQuarter(Quarter quarter) {
		this.quarter = quarter;
	}
	public List<KPI> getKpiList() {
		return kpiList;
	}
	public void setKpiList(List<KPI> kpiList) {
		this.kpiList = kpiList;
	}
	public List<Quarter> getQuarterList() {
		return quarterList;
	}
	public void setQuarterList(List<Quarter> quarterList) {
		this.quarterList = quarterList;
	}
	@Resource
	public void setQuarterService(QuarterService quarterService) {
		this.quarterService = quarterService;
	}
	@Resource
	public void setProportionService(ProportionService proportionService) {
		this.proportionService = proportionService;
	}
	public List<Proportion> getProportionList() {
		return proportionList;
	}
	public void setProportionList(List<Proportion> proportionList) {
		this.proportionList = proportionList;
	}
	public List<KPI> getTemplates() {
		return templates;
	}
	public void setTemplates(List<KPI> templates) {
		this.templates = templates;
	}
	public List<Long> getUserIdList() {
		return userIdList;
	}
	public void setUserIdList(List<Long> userIdList) {
		this.userIdList = userIdList;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public KPIService getKpiService() {
		return kpiService;
	}
	public UserService getUserService() {
		return userService;
	}
	
	public QuarterService getQuarterService() {
		return quarterService;
	}
	public ProportionService getProportionService() {
		return proportionService;
	}
	
	
	public ScoreKPIService getScoreKPIService() {
		return scoreKPIService;
	}
	@Resource
	public void setScoreKPIService(ScoreKPIService scoreKPIService) {
		this.scoreKPIService = scoreKPIService;
	}
	public DepartmentService getDepartmentService() {
		return departmentService;
	}
	@Resource
	public void setDepartmentService(DepartmentService departmentService) {
		this.departmentService = departmentService;
	}
	public Map<Long, Double> getKpiContentScore() {
		return kpiContentScore;
	}
	public void setKpiContentScore(Map<Long, Double> kpiContentScore) {
		this.kpiContentScore = kpiContentScore;
	}
	public List<User> getSourceUserList() {
		return sourceUserList;
	}
	public void setSourceUserList(List<User> sourceUserList) {
		this.sourceUserList = sourceUserList;
	}
	public User getSourceUser() {
		return sourceUser;
	}
	public void setSourceUser(User sourceUser) {
		this.sourceUser = sourceUser;
	}
	
	
	
}
