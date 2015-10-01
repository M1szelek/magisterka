package util;

import java.io.Serializable;

public class Config implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String lang;
	private String country;
	public Config(String lang, String country) {
		super();
		this.lang = lang;
		this.country = country;
	}
	public String getLang() {
		return lang;
	}
	public void setLang(String lang) {
		this.lang = lang;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	
	
}
