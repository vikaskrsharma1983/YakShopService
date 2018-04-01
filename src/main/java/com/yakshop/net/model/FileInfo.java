package com.yakshop.net.model;

public class FileInfo {
	private String fileName;
	private String message;
	 public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	private long fileSize;
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public long getFileSize() {
		return fileSize;
	}
	public void setFileSize(long fileSize) {
		this.fileSize = fileSize;
	}
}
