package com.gt.utils;

import javax.servlet.http.HttpServletRequest;

/**
 * 获取IP地址
 * @author： hhr
 * @date：2017-12-18
 */
public class GetIpUtil {
	/** 
	 * 获取访问用户的客户端IP（适用于公网与局域网）. 
	 */  
	public static final String getIpAddr(final HttpServletRequest request)  
	        throws Exception {  
	    if (request == null) {  
	        throw (new Exception("getIpAddr method HttpServletRequest Object is null"));  
	    }  
	    String ipString = request.getHeader("x-forwarded-for");  
	    if (PbUtils.isEmpty(ipString) || "unknown".equalsIgnoreCase(ipString)) {  
	        ipString = request.getHeader("Proxy-Client-IP");  
	    }  
	    if (PbUtils.isEmpty(ipString) || "unknown".equalsIgnoreCase(ipString)) {  
	        ipString = request.getHeader("WL-Proxy-Client-IP");  
	    }  
	    if (PbUtils.isEmpty(ipString) || "unknown".equalsIgnoreCase(ipString)) {  
	        ipString = request.getRemoteAddr();  
	    }  
	  
	    // 多个路由时，取第一个非unknown的ip  
	    final String[] arr = ipString.split(",");  
	    for (final String str : arr) {  
	        if (!"unknown".equalsIgnoreCase(str)) {  
	            ipString = str;  
	            break;  
	        }  
	    }  
	  
	    return ipString;  
	}  
}
