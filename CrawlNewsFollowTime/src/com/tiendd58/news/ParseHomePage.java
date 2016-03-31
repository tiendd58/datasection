package com.tiendd58.news;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class ParseHomePage {
	public static final String LINK_HOME = "http://news.zing.vn";
	private ArrayList<ManageCategory> listManageCategories;

	public ParseHomePage() {
		listManageCategories = new ArrayList<ManageCategory>();
	}

	public void parseCategory() throws IOException {
		try {
			Document doc = Jsoup.connect(LINK_HOME).get();
			Element curLiTag = doc.select("li[class~=current]").first()
					.nextElementSibling();
			String nextCategoryLink = "";
			while (curLiTag != null) {
				nextCategoryLink = LINK_HOME
						+ curLiTag.select("a").attr("href");
				listManageCategories.add(new ManageCategory(nextCategoryLink));
				curLiTag = curLiTag.nextElementSibling();
			}
		} catch (SocketTimeoutException e) {
			System.out.println("time out ");
		}

	}

	public void getDataForCategory() {
		for (ManageCategory category : listManageCategories) {
			System.out.println(category.getUrlFirst());
			try {
				category.getAllPost();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.out.println("DONE!");
	}

}
