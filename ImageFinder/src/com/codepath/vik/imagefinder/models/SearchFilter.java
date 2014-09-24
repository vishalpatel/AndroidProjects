package com.codepath.vik.imagefinder.models;

import java.io.Serializable;

import android.net.Uri;


public class SearchFilter implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8066158976075393340L;
	
	public String size, color, type, site;
	private static final String validSizes ="#icon#small#medium#large#xlarge#";
	private static final String validColors="#black#blue#brown#gray#green#orange#pink#purple#red#teal#white#yellow#";
	private static final String validTypes ="#face#photo#clipart#lineart#";
	
	public SearchFilter() {
		size = color = type = site = "";
	}
	
	public String getEncodedURIString(){
		String retval = "";
		if (this.size != null && this.size.length() > 0)
			retval +="&imgsz="+size;
		if (this.color != null && this.color.length() > 0)
			retval += "&imgcolor=" + this.color;
		if (this.type != null && this.type.length() > 0)
			retval += "&imgtype=" + this.type;
		if (this.site != null && this.site.length() > 0)
			retval += "&as_sitesearch="+ Uri.encode(this.site);
		
		return retval;
	}
	public Boolean isValid() {
		if (!validColors.contains("#"+this.color+"#"))
			return false;
		
		if (!validSizes.contains("#"+this.size+"#"))
			return false;
		
		if (!validTypes.contains("#"+this.type+"#"))
			return false;
		
		
		return true;
	}
}
