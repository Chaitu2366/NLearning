package com.snc.surf.marketing.NLearning.utils.Entitlements;

public class webassessorObject {
	
		 private String requestType;
		 private String securityToken;
		 private String returnFormat;
		 private String login;

		 // Getter Methods 
		 webassessorObject (String requestType,String securityToken, String returnFormat,String login){
			 
			 setRequestType(requestType);
			 setSecurityToken(securityToken);
			 setReturnFormat(returnFormat);
			 setLogin(login);
		 }
		 public String getRequestType() {
		  return requestType;
		 }

		 public String getSecurityToken() {
		  return securityToken;
		 }

		 public String getReturnFormat() {
		  return returnFormat;
		 }

		 public String getLogin() {
		  return login;
		 }

		 // Setter Methods 

		 public void setRequestType(String requestType) {
		  this.requestType = requestType;
		 }

		 public void setSecurityToken(String securityToken) {
		  this.securityToken = securityToken;
		 }

		 public void setReturnFormat(String returnFormat) {
		  this.returnFormat = returnFormat;
		 }

		 public void setLogin(String login) {
		  this.login = login;
		 }
		
}
