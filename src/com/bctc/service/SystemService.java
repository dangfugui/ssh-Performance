package com.bctc.service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.annotation.Resource;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate4.HibernateCallback;
import org.springframework.stereotype.Component;

@Component
public class SystemService extends SuperService{
	private SessionFactory sessionFactory;
	/**
	 * 设置子系统
	 * @param total	子系统总数
	 * @param subsystem		子系统编号
	 */
	public void setSubsystem(int total, int subsystem){
		String  sql="select count(uid) from User";
	/*	List list = hibernateTemplate.execute(new HibernateCallback<List>() {
			public List doInHibernate(Session session) throws HibernateException {
				  return session.createQuery(sql).list();
			}
		});
		System.out.println(list.get(0));*/
		Connection conn = hibernateTemplate.getSessionFactory().openStatelessSession().connection();
		Statement st = null;
		ResultSet result=null;
		String tables[]={"kpi","contentkpi","scorekpi","scoreyear","resultYear"};
		String ids   []={"kid","cid"		,"sid"		,"sid"		,"rid"	};
		long max=0;
		long usercount=0;
		try {
			st = conn.createStatement();
			result = st.executeQuery("select count(uid) from h_user");
			while (result.next()) {
				usercount=result.getLong(1);
			}
			for (int i=0;i<tables.length;i++) {
				result = st.executeQuery("select max("+ids[i]+") from "+tables[i]);
				max=0;
				while (result.next()) {
					max=result.getLong(1);
				}
				sql="ALTER TABLE "+tables[i]+" AUTO_INCREMENT="+(max+usercount*usercount*usercount*subsystem);
				System.out.println(sql);
				st.execute(sql);
			}
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {	
				st.close();
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
	}
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	@Resource
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
}
