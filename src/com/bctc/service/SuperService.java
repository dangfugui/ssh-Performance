package com.bctc.service;

import javax.annotation.Resource;

import org.springframework.orm.hibernate4.HibernateTemplate;


public class SuperService {

	public HibernateTemplate hibernateTemplate;
	@Resource
	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}
}
