package com.uestc.se.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;


public class UrlLocation implements ResourceLocation{

	private boolean resourceExists(String ref){
		try {
			HttpURLConnection.setFollowRedirects(false);
			HttpURLConnection con = (HttpURLConnection)new URL(ref).openConnection();
			con.setRequestMethod("HEAD");
			if(con.getResponseCode() == HttpURLConnection.HTTP_OK){
				return true;
			}
			return false;
		} catch (Exception e) {
			return false;
		}
		
	}
	@Override
	public InputStream getResourceAsStream(String ref) {
		try {
			if(resourceExists(ref)){
				URL url = new URL(ref);
				URLConnection urlCon = url.openConnection();
				return urlCon.getInputStream();
			}
			return null;
		} catch (IOException e) {
			printErr("Url connection failed, ref = " + ref);
			return null;
		}
	}

	@Override
	public URL getResource(String ref) {
		try {
			if(resourceExists(ref)){
				URL url = new URL(ref);
				URLConnection urlCon = url.openConnection();
				if(urlCon != null){
					return url;
				}
			}
			return null;
		} catch (IOException e) {
			return null;
		}
	}

	private void printErr(String errMsg){
		System.out.println("Err@UrlLocation: " + errMsg);
	}
}
