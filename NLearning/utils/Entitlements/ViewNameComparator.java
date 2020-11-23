package com.snc.surf.marketing.NLearning.utils.Entitlements;

import java.util.*;

import com.snc.selenium.core.SNCTest;

public class ViewNameComparator extends SNCTest implements Comparator{

	public int compare(Object o1,Object o2){  
		CardObject s1=(CardObject)o1;  
		CardObject s2=(CardObject)o2;  
		  
		if(s1.views==s2.views)  
		return s1.name.compareTo(s2.name);  
		else if(s1.views>s2.views)  
		return -1;  
		else  
		return 1;  
		}  


}