package com.tiendd58.crawl;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;

import javax.swing.JOptionPane;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.google.gson.Gson;

public class GetDataFromRSS {
	private static final String FOLDER_outFile = "output";
	private static final String NEWS_NAME = "VNEXPRESS";
	private String url;
	private ArrayList<Item> listItem;
	private Document doc;
	private Document docItem;
	private boolean gotData = false;

	public GetDataFromRSS(String url) {
		this.url = url;
		this.listItem = new ArrayList<Item>();

	}

	public void parseRSS() throws IOException {
		try {
			Connection conn = Jsoup.connect(this.url);
			doc = conn.get();
			Elements items = doc.getElementsByTag("item");
			Iterator<Element> iterator = items.iterator();
			while (iterator.hasNext()) {
				Element t = iterator.next();
				Elements title = t.getElementsByTag("title");
				Elements url = t.getElementsByTag("link");
				Elements time = t.getElementsByTag("pubDate");
				Item item = new Item(url.text(), title.text(), time.text());
				listItem.add(item);
				System.out.println("parse " + url + " complete from rss");
			}

		} catch (UnknownHostException e) {
			JOptionPane.showMessageDialog(null, "Not connect " + url);
		} catch (SocketTimeoutException e1) {
			System.out.println("time out " + url);
		}
		gotData = true;

	}

	public void updateContent() throws IOException {
		for (Item item : listItem) {
			item.parseContentItem(docItem);
		}
	}

	public boolean isGotData() {
		return gotData;
	}

	public void showResult() throws IOException {
		// get curTime
		DateFormat dateFormat = new SimpleDateFormat("HH-mm-ss_yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		// get path project
		File currDir = new File(FOLDER_outFile);
		String path = currDir.getAbsolutePath();
		// constructor file outFile
		File outFile = new File(path + "/" + dateFormat.format(cal.getTime())
				+ "_" + NEWS_NAME + ".tsv");
		if (!outFile.exists()) {
			outFile.createNewFile();
		}
		Gson gson = new Gson();
		BufferedWriter bw = new BufferedWriter(new FileWriter(outFile));
		for (Item item : listItem) {
			bw.write(gson.toJson(item));
			bw.newLine();
		}
		bw.close();
		System.out.println("Done!");
	}

}
