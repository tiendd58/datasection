package com.tiendd58.news;

import java.io.IOException;
import java.net.SocketTimeoutException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class News {
	private String title;
	private String time;
	private String content;
	private String description;
	private String url;

	public News(String url) {
		this.url = url;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void parseContent() throws IOException {
		try {
			Document doc = Jsoup.connect(this.url).timeout(4000).get();
			Elements titleD = doc.select("h1[class=the-article-title]");
			this.title = titleD.text();
			Elements descriptionD = doc.select("p[class=the-article-summary]");
			this.description = descriptionD.text();
			Elements timeD = doc.select("li[class=the-article-publish]");
			this.time = timeD.text();
			this.content = doc.select("div[class=the-article-body]")
					.select("p").text();
			System.out.println("reading: " + this.title);
			System.out.println("content: " + this.content);
		} catch (SocketTimeoutException e) {
			System.out.println("time out " + url);
		}
	}

	@Override
	public String toString() {
		return "News [title=" + title + ", time=" + time + ", url=" + url
				+ ", content=" + content + ", description=" + description + "]";
	}
}
