package com.weshopify.platform.cqrs.handler;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.axonframework.eventhandling.EventHandler;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.weshopify.platform.bean.CategoryBean;
import com.weshopify.platform.cqrs.domainevents.CategoryEvent;
import com.weshopify.platform.repository.BrandsRepo;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class CategoriesEventsHandler {

	@Autowired
	private BrandsRepo brandsRepo;
	
	@EventHandler
	public CategoryEvent categoryEventHandler(CategoryEvent event) {
		log.info("event recived in categoryEventHandler is "+event.toString());
		
		Set<CategoryBean> updatedCatsList = new HashSet<CategoryBean>();
		
		brandsRepo.findAll().stream().forEach(brand->{
			if(!CollectionUtils.isEmpty(brand.getCategories())) {
				brand.getCategories().forEach(cat->{
					if(event.getId() == cat.getId()) {
						CategoryBean updatedCatBean = new CategoryBean();
						updatedCatBean.setId(event.getId());
						updatedCatBean.setEnabled(event.isEnabled());
						updatedCatBean.setAlias(event.getAlias());
						updatedCatBean.setName(event.getName());
						updatedCatBean.setPcategory(event.getPcategory());						
						updatedCatsList.add(updatedCatBean);
					}else {
						updatedCatsList.add(cat);
					}
				});
				brand.setCategories(updatedCatsList);
				brandsRepo.save(brand);
			}
		});
		
		
		return null;
	}
	
	@QueryHandler
	public CategoryEvent queryCategoryEvents(CategoryEvent event) {
		log.info("event recived in queryCategoryEvents is "+event.toString());
		return null;
	}
}
