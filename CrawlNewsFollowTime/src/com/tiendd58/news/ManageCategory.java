package com.tiendd58.news;

import java.io.IOException;
import java.util.ArrayList;

public class ManageCategory {
	private ArrayList<Category> listPage = new ArrayList<Category>();
	private int totalPage = 2;
	private String urlFirst;

	public ManageCategory(String url) {
		this.urlFirst = url;
	}

	public String getUrlFirst() {
		return urlFirst;
	}

	public void getAllPost() throws IOException {
		Category first = new Category(this.urlFirst);
		first.parseCategory();
		listPage.add(first);
		int count = 0;
		while (count < totalPage) {
			Category next = new Category(first.getNextLink());
			next.parseCategory();
			listPage.add(next);
			first = next;
			count++;
		}
		for (Category category : listPage) {
			try {
				category.getAllPostInPage();
			} catch (IOException e) {
				e.printStackTrace();
			}
			System.out.println(category.getNextLink());
		}
		for (Category category : listPage) {
			try {
				category.showResult();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void printResult() {
		for (Category category : listPage) {
			try {
				category.showResult();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
