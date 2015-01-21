package org.han.lsb_shop.util;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;

import android.util.Log;

public class HTTP {

	/**
	 * 返回请求网络字符串数据
	 * 
	 * @param url
	 *            请求地址
	 * @param params
	 *            请求参数
	 * @param method
	 *            请求方法
	 * @return 字符串数据
	 */
	public static String getString(String url, Map<String, String> params, String method) {
		try {
			HttpClient httpClient = new DefaultHttpClient();
			if (url.contains("m_newMsg")) {
				Loger.d("HTTP", url + "" + params);
			} else {
				Loger.d("TAG_HTTP", url + "" + params);
			}
			HttpPost post = new HttpPost(url);
			HttpParams httpParameters = new BasicHttpParams();
			// 设置请求超时
			int timeoutConnection = 3 * 1000;
			HttpConnectionParams.setConnectionTimeout(httpParameters, timeoutConnection);
			// 设置响应超时
			int timeoutSocket = 3 * 1000;
			HttpConnectionParams.setSoTimeout(httpParameters, timeoutSocket);
			if (params != null && params.size() > 0) {
				Iterator<String> iterator = params.keySet().iterator();
				List<BasicNameValuePair> requestParam = new ArrayList<BasicNameValuePair>();
				String param = "";
				String value = "";
				while (iterator.hasNext()) {
					param = iterator.next();
					value = params.get(param);
					requestParam.add(new BasicNameValuePair(param, value));
				}
				post.setEntity(new UrlEncodedFormEntity(requestParam, "UTF-8"));

			}
			HttpResponse response = httpClient.execute(post);

			if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
				return null;
			}
			HttpEntity entity = response.getEntity();
			if (entity == null) {
				return null;
			}
			if (entity != null) {
				return EntityUtils.toString(entity, "UTF-8");
			}
		} catch (Exception e) {
			Log.e("EMNET", "请求网络异常=>", e);
		}
		return null;
	}

	/**
	 * 使用 HttpURLConnection Get方法请求网络返回网络流
	 * 
	 * @param urlStr
	 *            请求地址
	 * @return 网络流
	 */
	public static InputStream httpGet(String urlStr) {
		try {
			URL url = new URL(urlStr);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestProperty("connection", "Keep-Alive");
			connection.setRequestProperty("Charsert", "UTF-8");
			connection.setConnectTimeout(1000 * 20);
			connection.setReadTimeout(1000 * 20);
			connection.setRequestMethod("GET");
			if (connection.getResponseCode() != HttpStatus.SC_OK) {
				return null;
			}
			InputStream inStream = connection.getInputStream();
			if (inStream != null) {
				return inStream;
			}
		} catch (Exception e) {
			Log.e("EMNET", "请求网络异常=>", e);
			return null;
		}
		return null;
	}

	/***
	 * 
	 * @param uploadUrl请求地址
	 * @param param参数
	 * @param fieldName上传文件的地址参数
	 * @param fileByte上传的文件
	 * @param fileName上传文件的名称
	 * @return
	 */
	public static String upload(String uploadUrl, HashMap<String, String> param, String fieldName, byte[] fileByte, String fileName) {
		StringBuilder result = null;
		try {
			String boundary = "---------------------------Esa000000000001";
			URL url = new URL(uploadUrl);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setUseCaches(false);
			connection.setConnectTimeout(1000 * 30);
			connection.setReadTimeout(1000 * 30);
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Connection", "Keep-Alive");
			connection.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
			connection.setRequestProperty("Charsert", "UTF-8");

			StringBuilder sbf = new StringBuilder();
			sbf.append("--" + boundary + "\r\n");
			sbf.append("Content-Disposition: form-data; name=\"" + fieldName + "\"; filename=\"" + fileName + "\"\r\n");
			sbf.append("Content-Type: application/octet-stream" + "\r\n\r\n");
			byte[] fz = sbf.toString().getBytes();
			StringBuilder sb = new StringBuilder();
			sb.append("\r\n\r\n");
			Iterator<String> iterator = param.keySet().iterator();
			while (iterator.hasNext()) {
				String key = iterator.next();
				String value = param.get(key);
				sb.append("--" + boundary + "\r\n");
				sb.append("Content-Disposition: form-data; name=\"" + key + "\"\r\n");
				sb.append("\r\n");
				sb.append(value);
			}
			byte[] before = sb.toString().getBytes();
			byte[] after = ("\r\n--" + boundary + "--\r\n").getBytes();
			connection.setRequestProperty("content-length", (before.length + fz.length + fileByte.length + after.length) + "");
			DataOutputStream dos = new DataOutputStream(connection.getOutputStream());
			dos.write(fz);
			dos.write(fileByte);
			dos.write(before);
			dos.write(after);
			dos.flush();
			connection.connect();
			int code = connection.getResponseCode();
			Loger.i("EMNET", "文件上传返回状态码 " + code);
			InputStream stream = connection.getInputStream();
			byte[] b = new byte[1024 * 10];
			int l;
			result = new StringBuilder();
			while ((l = stream.read(b)) != -1) {
				result.append(new String(b, 0, l, "utf-8"));
			}
			dos.close();
			stream.close();
		} catch (MalformedURLException e) {
			Loger.e("EMNET---文件上传异常=>", e);
		} catch (ProtocolException e) {
			Loger.e("EMNET---文件上传异常=>", e);
		} catch (IOException e) {
			Loger.e("EMNET---文件上传异常=>", e);
		} finally {

		}
		if (result == null) {
			return null;
		} else {
			return result.toString();
		}
	}

	/**
	 * 上传文件
	 * 
	 * @param uploadUrl
	 *            上传地址
	 * @param param
	 *            参数
	 * @param filepath
	 *            文件路径
	 * @return 结果
	 */
	public static String upload(String uploadUrl, HashMap<String, String> param, String filepath) throws Exception, IOException, MalformedURLException, ProtocolException {
		StringBuilder result = null;
		try {
			String boundary = "---------------------------Esa000000000001";
			URL url = new URL(uploadUrl);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setUseCaches(false);
			connection.setConnectTimeout(1000 * 50);
			connection.setReadTimeout(1000 * 50);
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Connection", "Keep-Alive");
			connection.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
			connection.setRequestProperty("Charsert", "UTF-8");

			File file = new File(filepath);
			StringBuilder sbf = new StringBuilder();
			sbf.append("--" + boundary + "\r\n");
			sbf.append("Content-Disposition: form-data; name=\"img\"; filename=\"" + file.getName() + "\"\r\n");
			sbf.append("Content-Type: application/octet-stream" + "\r\n\r\n");
			byte[] fz = sbf.toString().getBytes();
			StringBuilder sb = new StringBuilder();
			sb.append("\r\n\r\n");
			Iterator<String> iterator = param.keySet().iterator();
			while (iterator.hasNext()) {
				String key = iterator.next();
				String value = param.get(key);
				sb.append("--" + boundary + "\r\n");
				sb.append("Content-Disposition: form-data; name=\"" + key + "\"\r\n");
				sb.append("\r\n");
				sb.append(value + "\r\n");
			}
			byte[] before = sb.toString().getBytes();
			byte[] after = ("\r\n--" + boundary + "--\r\n").getBytes();
			connection.setRequestProperty("content-length", (before.length + fz.length + file.length() + after.length) + "");
			DataOutputStream dos = new DataOutputStream(connection.getOutputStream());
			dos.write(fz);
			FileInputStream fis = new FileInputStream(file);
			byte[] buffer = new byte[1024 * 10];
			int len;
			while ((len = fis.read(buffer)) != -1) {
				dos.write(buffer, 0, len);
			}
			dos.write(before);
			dos.write(after);
			dos.flush();
			connection.connect();
			int code = connection.getResponseCode();
			Loger.i("EMNET---文件上传返回状态码 " + code);
			InputStream stream = connection.getInputStream();
			byte[] b = new byte[1024 * 10];
			int l;
			result = new StringBuilder();
			while ((l = stream.read(b)) != -1) {
				result.append(new String(b, 0, l, "utf-8"));
			}
			fis.close();
			dos.close();
			stream.close();
		} catch (MalformedURLException e) {
			Loger.e("EMNET---文件上传异常=>", e);
		} catch (ProtocolException e) {
			Loger.e("EMNET---文件上传异常=>", e);
		} catch (IOException e) {
			Loger.e("EMNET---文件上传异常=>", e);
		} catch (Exception e) {
			Loger.e("EMNET---文件上传异常=>", e);
		} finally {

		}
		if (result == null) {
			return null;
		} else {
			return result.toString();
		}
	}
}
