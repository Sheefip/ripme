package com.rarchives.ripme.ripper.rippers;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.rarchives.ripme.ripper.AbstractHTMLRipper;
import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.rarchives.ripme.utils.Http;

public class Z0rRipper extends AbstractHTMLRipper {

	public Z0rRipper(URL url) throws IOException {
		super(url);
	}

	@Override
	public String getDomain() {
		return "z0r.de";
	}

	@Override
	public String getHost() {
		return "z0r";
	}

	@Override
	public boolean canRip(URL url) {
		return url.getHost().endsWith(this.getDomain());
	}

	@Override
	public URL sanitizeURL(URL url) throws MalformedURLException {
		return url;
	}

	@Override
	public Document getFirstPage() throws IOException {
		return Http.url(url).get();
	}

	@Override
	public void downloadURL(URL url, int index) {
		addURLToDownload(url, getPrefix(index));
	}

	@Override
	public String getGID(URL url) throws MalformedURLException {
		Pattern p = Pattern.compile("^https?://z0r.de/(\\d*).*$");
		Matcher m = p.matcher(url.toExternalForm());

		if (m.matches())
			return m.group(1);

		throw new MalformedURLException();
	}

	@Override
	public Document getNextPage(Document doc) throws IOException {
		throw new IOException("No more pages");
	}

	@Override
	public List<String> getURLsFromPage(Document doc) {
		List<String> result = new ArrayList<>();
		try {
			result.add("https://z0r.de/L/z0r-de_" + this.getGID(this.url) + ".swf");
		} catch (MalformedURLException e) {
		}
		return result;
	}
}
