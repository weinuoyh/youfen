package com.moerlong.youfen.Utils;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.net.URL;
import java.net.URLConnection;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class CommonUtil {

	public static String getRtick(){
		long timestamp = new Date().getTime();
		int rnd = (int)(Math.random() * 1000);
		String rtick = timestamp + "" + rnd;
		return rtick;
	}

	/**
	 * 生成验证码
	 * @param number
	 * @return
	 */
	public static String generateCaptcha(int number) {
		String verifyCode = "";
		for (int i = 0; i < number; i++) {
			char c = (char) (randomInt(0, 10) + '0');
			verifyCode += String.valueOf(c);
		}
		return verifyCode;
	}

	public static int randomInt(int from, int to) {
		Random r = new Random();
		return from + r.nextInt(to - from);
	}
	
	/**
	 * 向指定 URL 发送POST方法的请求
	 * 
	 * @param url
	 *            发送请求的 URL
	 * @param param
	 *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
	 * @return 所代表远程资源的响应结果
	 */
	public static String sendPost(String url, String param) {

		PrintWriter out = null;
		BufferedReader in = null;
		String result = "";

		try {
			URL realUrl = new URL(url);
			// 打开和URL之间的连接
			URLConnection conn = realUrl.openConnection();
			// 设置通用的请求属性
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("user-agent",
					"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			// 发送POST请求必须设置如下两行
			conn.setDoOutput(true);
			conn.setDoInput(true);
			// 获取URLConnection对象对应的输出流
			out = new PrintWriter(conn.getOutputStream());
			// 发送请求参数
			out.print(param);
			// flush输出流的缓冲
			out.flush();
			// 定义BufferedReader输入流来读取URL的响应
			in = new BufferedReader(
					new InputStreamReader(conn.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			System.out.println("发送 POST 请求出现异常！" + e);
			e.printStackTrace();
		}
		// 使用finally块来关闭输出流、输入流
		finally {
			try {
				if (out != null) {
					out.close();
				}
				if (in != null) {
					in.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return result;
	}
	
	/**
	 * 把json转成map
	 * @param json
	 * @return
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	public static Map<String,Object> json2map(String json) throws JsonParseException, JsonMappingException, IOException{
		ObjectMapper mapper=new ObjectMapper();
		return mapper.readValue(json, Map.class);
	}
	
	/**
	 * 把json转成object
	 * @param json
	 * @param clazz
	 * @return
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	public static <T> T json2Object(String json,Class<T> clazz) throws JsonParseException, JsonMappingException, IOException{
		ObjectMapper mapper=new ObjectMapper();
		return mapper.readValue(json, clazz);
	}
	
	/**
	 * 把object转成json
	 * @param obj
	 * @return
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	public static String obj2json(Object obj) throws JsonParseException, JsonMappingException, IOException{
		return new ObjectMapper().writeValueAsString(obj);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void typeConvert(Map<String,List> filter,Class c){
		if(filter==null){
			return ;
		}
		for(String key : filter.keySet()){
			try {
				String typeName = c.getDeclaredField(key).getType().getName();
				if("java.lang.Integer".equals(typeName) 
						&& filter.get(key)!=null
						&& filter.get(key).get(0)!=null){
					filter.get(key).set(0,Integer.valueOf(filter.get(key).get(0).toString()));
				}else if("java.lang.Long".equals(typeName) 
						&& filter.get(key)!=null
						&& filter.get(key).get(0)!=null){
					filter.get(key).set(0,Long.valueOf(filter.get(key).get(0).toString()));
				}
			} catch (NoSuchFieldException e) {
				e.printStackTrace();
			} catch (SecurityException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 字符串数据是否为逻辑空
	 * @param str
	 * @return
	 */
	public static boolean isNull(String str) {
		if (str == null || str.trim().equals("")
				|| str.trim().equalsIgnoreCase("null")) {
			return true;
		} else {
			return false;
		}
	}
	
	public static BigDecimal big100 = new BigDecimal("100");
	
	/**
	 * 把元转成分
	 * @param yuan
	 * @param format
	 * @return
	 */
	public static String yuanToCent(String yuan){
		DecimalFormat df = new DecimalFormat("0") ;
		return df.format(new BigDecimal(yuan).multiply(big100));
	}
	
	/**
	 * 把分转成元
	 * @param yuan
	 * @param format
	 * @return
	 */
	public static String centToYuan(String yuan,String format){
		if(format==null){
			format = "0.00";
		}
		DecimalFormat df = new DecimalFormat(format) ;
		return df.format(new BigDecimal(yuan).divide(big100));
	}
	
	/**
	 * 取反
	 * @param src
	 * @return
	 */
	public static byte[] reverseBytes(byte[] src){
		byte[] res = new byte[src.length];
        for(int i=0;i<src.length;i++){
            byte temp = src[i];
            res[i] = (byte) (~temp);
        }
        return res;
	}
	
	/**
	 * 把日期格式化成string
	 * @param pattern	可为空，默认为yyyy-MM-dd HH:mm:ss
	 * @param date	可为空，默认为当天
	 * @return
	 */
	public static String dateFormat(String pattern,Date date) {
		if(pattern == null) {
			pattern = "yyyy-MM-dd HH:mm:ss";
		}
		if(date == null) {
			date = new Date();
		}
		DateFormat df = new SimpleDateFormat(pattern);
		return df.format(date);
	}
	
	/**
	 * 左填充
	 * @param val
	 * @param fillVal
	 * @param maxLen
	 * @return
	 */
	public static String fillLeft(String val,String fillVal,int maxLen) {
		if(val==null) {
			val = "";
		}
		if(fillVal == null) {
			fillVal = "0";
		}
		int len = val.length();
		for(int i=0; i<(maxLen-len); i++) {
			val = fillVal + val;
		}
		return val;
	}
	
	public static String random(int len) {
		String ran = Math.random()+"";
		return ran.substring(ran.length()-len);
	}

}
