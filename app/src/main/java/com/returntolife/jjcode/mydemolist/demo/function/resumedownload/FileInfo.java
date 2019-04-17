package com.returntolife.jjcode.mydemolist.demo.function.resumedownload;

import java.io.Serializable;


public class FileInfo implements Serializable {
	private int id;   //标识id
	private String url; //下载地址
	private String fileName; //文件名
	private int length; //文件长度
	private int finished; //下载进度

	public FileInfo() {
		super();
	}

	/**
	 * 
	 * @param id
	 * @param url
	 * @param fileName
	 * @param length
	 * @param finished
	 */
	public FileInfo(int id, String url, String fileName, int length, int finished) {
		super();
		this.id = id;
		this.url = url;
		this.fileName = fileName;
		this.length = length;
		this.finished = finished;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public int getFinished() {
		return finished;
	}

	public void setFinished(int finished) {
		this.finished = finished;
	}

	@Override
	public String toString() {
		return "FileInfo [id=" + id + ", url=" + url + ", fileName=" + fileName + ", length=" + length + ", finished="
				+ finished + "]";
	}

}
