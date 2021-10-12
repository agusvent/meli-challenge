package com.agustinventura.apiproxy.services;

import java.awt.List;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class ProxyServicesImpl implements ProxyServices{

	private ArrayList<String> unwishedIpsList = new ArrayList<>();

	final Logger logger = LoggerFactory.getLogger(ProxyServicesImpl.class);
	
	public ArrayList<String> getAllUnwishedIps() {
		return unwishedIpsList;
	}
	
	public Boolean setUnwishedIp(String ip) {
		if(this.findIp(ip)==-1) {
			return unwishedIpsList.add(ip);
		}else {
			return false;
		}
	}
	
	private Integer findIp(String ip) {
		Integer indexOfIP = -1;
			indexOfIP = unwishedIpsList.indexOf(ip);
		return indexOfIP;
	}
	
	public Boolean removeUnwishedIp(String ip) {
		return unwishedIpsList.remove(ip);
	}
	
	public Boolean removeAllUnwishedIps() {
		return unwishedIpsList.removeAll(this.unwishedIpsList);
	}
	
	public Boolean isIpUnwished(String ip) {
		boolean isIpUnwished = false;
			if(this.findIp(ip)>-1) {
				isIpUnwished = true;
			}
		return isIpUnwished;
	}
}