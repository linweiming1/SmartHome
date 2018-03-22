package com.lwm.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

/**
 * 浏览器请求类
 * @类名	HttpUtil.java
 * @版本 V 1.0
 */
public class HttpUtil {

	/**
	 * GET 连接
	 * @param urlStr
	 * @return
	 */
	public static String httpGet(String urlStr) {
		try {
		    URL url = new URL(urlStr);
		    //远程url
			URLConnection conn = url.openConnection();
		    BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(),"utf-8"));
		    String line = null;
		    StringBuffer buffer = new StringBuffer();
		    while ((line = reader.readLine()) != null){
		    	buffer.append(line);
		    }
		    reader.close();  
		    return buffer.toString();
		} catch (Exception e) {
		}
		return null;
	}
	
	/**
	 * POST 连接
	 * @param urlStr
	 * @return
	 */
	public static String httpPost(String urlStr, String queryString) {
		try {
			URL url = new URL(urlStr);
			HttpURLConnection http = (HttpURLConnection) url.openConnection();
			http.setRequestMethod("POST");
			http.setConnectTimeout(0);
			http.setInstanceFollowRedirects(true);
			http.setRequestProperty("Content-Type","application/json");
			http.setDefaultUseCaches(false);
			http.setDoOutput(true);
					
			PrintWriter out = new PrintWriter(http.getOutputStream());
			out.print(queryString);//传入参数
			out.close();
			http.connect();//连接
			BufferedReader reader = new BufferedReader(new InputStreamReader(http.getInputStream(),"utf-8"));
		    String line = null;
		    StringBuffer buffer = new StringBuffer();
		    while ((line = reader.readLine()) != null){
		    	buffer.append(line);
		    }
		    reader.close(); 
		    http.disconnect();
		    return buffer.toString();
		} catch (Exception e) {
		}
		return null;
	}
	
	/** 
     * UTF-8编码 加密
     * @param source 
     * @return 
     */  
    public static String urlEncodeUTF8(String source) {  
        String result = source;  
        try {  
            result = java.net.URLEncoder.encode(source, "UTF-8");  
        } catch (UnsupportedEncodingException e) {  
            e.printStackTrace();  
        }  
        return result;  
    }  
    
    /** 
     * UTF-8编码 解密
     * @param source 
     * @return 
     */  
    public static String urlDecoderUTF8(String source) {  
        String result = source;  
        try {  
            result = java.net.URLDecoder.decode(result, "UTF-8");  
        } catch (UnsupportedEncodingException e) {  
            e.printStackTrace();  
        }  
        return result;  
    }
    
    /** 
     * 浏览器编码转
     * @param source 
     * @return 
     */  
    public static String urlToUTF8(String source) {  
        String result = source;  
        try {  
        	result = new String(result.getBytes("iso8859-1"), "UTF-8");
        } catch (UnsupportedEncodingException e) {  
            e.printStackTrace();  
        }  
        return result;  
    }
    
    /**
     * unicode转为本地语言
     * @param str
     * @return
     */
	public static String ascii2Native(String str) {   
		StringBuilder sb = new StringBuilder();   
		int begin = 0;   
		int index = str.indexOf("\\u");   
		while (index != -1) {   
			sb.append(str.substring(begin, index));   
			sb.append(ascii2Char(str.substring(index, index + 6)));   
			begin = index + 6;   
			index = str.indexOf("\\u", begin);   
		}   
		sb.append(str.substring(begin));   
		return sb.toString();   
	}  
  	 
	private static char ascii2Char(String str) {   
		if (str.length() != 6) {   
			throw new IllegalArgumentException(   
					"Ascii string of a native character must be 6 character.");   
		}   
		if (!"\\u".equals(str.substring(0, 2))) {   
			throw new IllegalArgumentException(   
					"Ascii string of a native character must start with \"\\u\".");   
		}   
		String tmp = str.substring(2, 4);   
		int code = Integer.parseInt(tmp, 16) << 8;   
		tmp = str.substring(4, 6);   
		code += Integer.parseInt(tmp, 16);   
		return (char) code;   
	}  
	
	public static void main(String[] args) {
		String reqJson = "{\"username\":1,\"password\":1}";
		String responseContent = httpPost("http://ssh.q3z3.com/w01/app/login", reqJson);
		System.err.println(responseContent);
	}
    
}
