package com.agustinventura.apiproxy.dtos;

import org.springframework.http.HttpStatus;

public class FilterProcessResponse {

	private boolean mustFilter;
	private String error;
	private HttpStatus status;
	
	public FilterProcessResponse (boolean mustFilter,String error,HttpStatus status) {
		this.mustFilter = mustFilter;
		this.error = error;
		this.status = status;
	}
	
	public boolean isMustFilter() {
		return mustFilter;
	}
	public void setMustFilter(boolean mustFilter) {
		this.mustFilter = mustFilter;
	}
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}
	public HttpStatus getStatus() {
		return status;
	}
	public void setStatus(HttpStatus status) {
		this.status = status;
	}
	
}
