package test.com.testretrofit.retrofit.bean;

import com.google.gson.annotations.SerializedName;

public class ExpressMessageItemBean{
	@SerializedName("time")
	private String time;
	@SerializedName("location")
	private String location;
	@SerializedName("context")
	private String context;
	@SerializedName("ftime")
	private String ftime;

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getContext() {
		return context;
	}

	public void setContext(String context) {
		this.context = context;
	}

	public String getFtime() {
		return ftime;
	}

	public void setFtime(String ftime) {
		this.ftime = ftime;
	}

}
