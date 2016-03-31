package com.tiendd58.news;

import java.io.IOException;

public class Main {
	public static void main(String[] args) throws IOException {
		ParseHomePage home = new ParseHomePage();
		home.parseCategory();
		home.getDataForCategory();
	}
}
