package es.bewom.util.mysql;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;

public class MySQL {
	
	public final String DB = "http://bewom.es/test/";
	public final String user = "plugin";
	public final String password = "UzjnGApn8bD3TWUV";
	
	public List<String> executeQuery(String e, String c){
		
		List<String> o = new ArrayList<String>();
		
		String urls = DB + password + "/" + e + "/" + c;
		urls = urls.replace(" ", "%20");
		
		URL url;
		try {
			url = new URL(urls);
			URLConnection con = url.openConnection();
			InputStream in = con.getInputStream();
			String encoding = con.getContentEncoding();
			encoding = encoding == null ? "UTF-8" : encoding;
			String body = IOUtils.toString(in, encoding);
			o.add(body);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		if(o.get(0).contains(",")){
			List<String> a = new ArrayList<String>();
			String[] splited = o.get(0).split(",", -1);
			for (int i = 0; i < splited.length; i++) {
				a.add(splited[i]);
			}
			o = a;
		}
		return o;
	}
	
	public boolean isBanned(String uuid){
		
		String urls = DB + "public/" + uuid;
		urls = urls.replace(" ", "%20");
		
		URL url;
		try {
			url = new URL(urls);
			URLConnection con = url.openConnection();
			InputStream in = con.getInputStream();
			String encoding = con.getContentEncoding();
			encoding = encoding == null ? "UTF-8" : encoding;
			String body = IOUtils.toString(in, encoding);
			if(body.equals("1")){
				return true;
			}
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return false;
	}

}
