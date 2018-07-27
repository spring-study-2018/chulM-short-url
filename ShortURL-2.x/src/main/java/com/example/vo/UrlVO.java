package com.example.vo;

public class UrlVO {

	private int seq;
	private String url;
	private String key;

	public UrlVO() {

	}

	public UrlVO(int seq, String url) {
		super();
		this.seq = seq;
		this.url = url;
	}

	public int getSeq() {
		return seq;
	}

	public void setSeq(int seq) {
		this.seq = seq;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	@Override
	public String toString() {
		return "UrlVO [seq=" + seq + ", url=" + url + ", key=" + key + "]";
	}

}
