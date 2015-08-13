package es.bewom.util.mysql;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.commons.io.IOUtils;

public class MySQL {
	
	public final String DB = "http://bewom.es/test/";
	public final String user = "plugin";
	public final String password = "UzjnGApn8bD3TWUV";
	
	public String executeQuery(String e, String c){
		
		String o = "";
		
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
			o = body;
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return o;
	}

}
