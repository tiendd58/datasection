package com.tiendd58.crawl;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class Item {
	private static final int TIME_OUT = 4000;
	private String url, title, content, time;

	public Item(String url, String title, String time) {
		this.url = url;
		this.title = title;
		this.time = time;
		this.content = "";
	}

	public String getUrl() {
		return url;
	}

	public String getTitle() {
		return title;
	}

	public String getTime() {
		return time;
	}

	public String getContent() {
		return content;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void parseContentItem(Document doc) throws IOException {
		try {
			doc = Jsoup.connect(this.url).timeout(TIME_OUT).get();
			Elements description = doc.select("div[class=short_intro txt_666]");
			Elements content = doc.select("p[class=Normal");
			this.content += description.text() + "\t" + content.text();
			System.out.println("get content from " + url);
		} catch (UnknownHostException e) {
			System.out.println("Not connect " + url);
		} catch (SocketTimeoutException e1) {
			System.out.println("time out " + url);
		}
	}

}
