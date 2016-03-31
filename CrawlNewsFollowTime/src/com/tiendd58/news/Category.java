package com.tiendd58.news;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.google.gson.Gson;

public class Category {
	private static final String FOLDER_outFile = "output";
	private String url;
	private ArrayList<News> listNews;
	private String nextLink;

	// private String date;

	public Category(String url) {
		this.url = url;
		listNews = new ArrayList<News>();
	}

	public void parseCategory() throws IOException {
		Document doc = Jsoup.connect(this.url).get();
		Elements listNews = doc.select("section[class=cate_content]").select(
				"article");
		Iterator<Element> i = listNews.iterator();
		this.nextLink = ParseHomePage.LINK_HOME
				+ doc.select("p[class=more]").select("span > a").attr("href");
		while (i.hasNext()) {
			Element t = i.next();
			String link = ParseHomePage.LINK_HOME
					+ t.select("div[class=cover]").select("a").attr("href");

			this.listNews.add(new News(link));
		}
	}

	public void getAllPostInPage() throws IOException {
		for (News news : listNews) {
			news.parseContent();
		}
		// get curTime
		DateFormat dateFormat = new SimpleDateFormat("HH-mm-ss_yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		// get path project
		File currDir = new File(FOLDER_outFile);
		String path = currDir.getAbsolutePath();
		// constructor file outFile
		File outFile = new File(path + "/" + dateFormat.format(cal.getTime())
				+ "_" + url.substring(20, 26) + ".tsv");
		if (!outFile.exists()) {
			outFile.createNewFile();
		}
		Gson gson = new Gson();
		BufferedWriter bw = new BufferedWriter(new FileWriter(outFile));
		for (News item : listNews) {
			bw.write(gson.toJson(item));
			bw.newLine();
		}
		bw.close();
		System.out.println("Done " + url);
	}

	public String getNextLink() {
		return nextLink;
	}

	public void showResult() throws IOException {

	}

}
