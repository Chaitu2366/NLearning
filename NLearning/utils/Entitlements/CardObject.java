package com.snc.surf.marketing.NLearning.utils.Entitlements;

import java.util.*;

import com.glide.communications.RemoteGlideRecord;

import org.junit.Assert;

import com.snc.selenium.core.SNCTest;

public class CardObject extends SNCTest {

	public int views,rating,assessmentCount,certificationCount,courseCount;  
	public String name,description,shortDescription,category,about,learningObject,certificationType,tag,duration,badgeNAme,paid,role,level,product,tags;  
	public ArrayList<String> pathAssesments = new ArrayList<String>();
	public ArrayList<String> pathCourses = new ArrayList<String>();
	
	CardObject(int views,String name){  
	this.views=views;  
	this.name=name;  
	}  

	CardObject(String name,String description,String shortDescription,String duration, String badgeNAme
			,String paid,String category,String learningObject,String certificationType, String tag,
			int rating,int assessmentCount,int certificationCount,int courseCount,
			ArrayList<String> pathAssesments,ArrayList<String> pathCourses,String role, String level, String product,String tags){
		
		this.name=name;  
		this.description=description; 
		this.shortDescription=shortDescription;  
		this.duration =duration;
		this.badgeNAme=badgeNAme;
		this.paid=paid; 
		this.category=category;  
		
		this.learningObject=learningObject;  
		this.certificationType=certificationType; 
		this.tag =tag; 
		this.rating=rating;
		this.assessmentCount=assessmentCount;
		this.certificationCount=certificationCount;
		this.courseCount=courseCount;
		this.pathAssesments=pathAssesments;  
		this.pathCourses=pathCourses;
		this.about=description; 
		this.role=role;
		this.level=level;
		this.product=product;
		this.tags=tags;
	}

	
}