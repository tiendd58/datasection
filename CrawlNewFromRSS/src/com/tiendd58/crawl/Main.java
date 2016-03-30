package com.tiendd58.crawl;

import java.io.IOException;

public class Main {

	public static void main(String[] args) throws IOException {
		String url = "http://vnexpress.net/rss/thoi-su.rss";
		GetDataFromRSS getData = new GetDataFromRSS(url);
		getData.parseRSS();
		getData.updateContent();
		getData.showResult();
	}
}
