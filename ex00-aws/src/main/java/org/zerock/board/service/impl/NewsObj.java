package org.zerock.board.service.impl;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class NewsObj {

	public NewsObj(BufferedReader br) {
		try {
			this.url = br.readLine();
			this.title = br.readLine();
			this.content = "";
			for (int i = 0; i < 3; i++) {
				this.content += br.readLine();
			}
			this.path = br.readLine();
			this.keywords = new ArrayList();
			for (int i = 0; i < 10; i++) {
				keywords.add(br.readLine());
			}
			this.similarity = Float.parseFloat(br.readLine());
			this.location = Float.parseFloat(br.readLine());
			this.positive = Float.parseFloat(br.readLine());
			this.negative = Float.parseFloat(br.readLine());

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public ArrayList getKeywords() {
		return keywords;
	}

	public void setKeywords(ArrayList keywords) {
		this.keywords = keywords;
	}

	public float getSimilarity() {
		return similarity;
	}

	public void setSimilarity(float similarity) {
		this.similarity = similarity;
	}

	public float getLocation() {
		return location;
	}

	public void setLocation(float location) {
		this.location = location;
	}

	public float getPositive() {
		return positive;
	}

	public void setPositive(float positive) {
		this.positive = positive;
	}

	public float getNegative() {
		return negative;
	}

	public void setNegative(float negative) {
		this.negative = negative;
	}

	private String url;
	private String title;
	private String content;
	private String path; // 이미지 파일 이름
	private ArrayList keywords;
	private float similarity;
	private float location;
	private float positive;
	private float negative;

}
