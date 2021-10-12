package com.agustinventura.apiproxy.services;

import java.util.ArrayList;

public interface ProxyServices {
	
	public ArrayList<String> getAllUnwishedIps();
	
	public Boolean setUnwishedIp(String ip);
	
	public Boolean removeUnwishedIp(String ip);
	
	public Boolean removeAllUnwishedIps();
	
	public Boolean isIpUnwished(String ip);
}