package com.breadbin.festival.api.rss;

import android.os.AsyncTask;

import com.breadbin.festival.api.Callback;
import com.model.error.ErrorResponse;
import com.model.news.Article;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class RssConnector {

	private final String rssEndpoint;

	public RssConnector(String rssEndpoint) {
		this.rssEndpoint = rssEndpoint;
	}

	public void getRssArticles(Callback callback) {
		new RssTask(callback).execute(rssEndpoint);
	}

	private class RssTask extends AsyncTask<String, Void, ResponseWrapper> {

		private Callback callback;

		public RssTask(Callback callback) {
			this.callback = callback;
		}

		@Override
		protected ResponseWrapper doInBackground(String... params) {
			String feedUrl = params[0];
			URL url;

			ResponseWrapper responseWrapper = new ResponseWrapper();
			try {
				SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
				SAXParser saxParser = saxParserFactory.newSAXParser();
				XMLReader xmlReader = saxParser.getXMLReader();

				url = new URL(feedUrl);
				RssHandler rssHandler = new RssHandler();

				xmlReader.setContentHandler(rssHandler);
				xmlReader.parse(new InputSource(url.openStream()));

				responseWrapper.setArticles(rssHandler.getArticleList());
			} catch (ParserConfigurationException e) {
				e.printStackTrace();
				responseWrapper.setErrorResponse(new ErrorResponse(-1, e.getMessage()));
			} catch (MalformedURLException e) {
				e.printStackTrace();
				responseWrapper.setErrorResponse(new ErrorResponse(-1, e.getMessage()));
			} catch (SAXException e) {
				e.printStackTrace();
				responseWrapper.setErrorResponse(new ErrorResponse(-1, e.getMessage()));
			} catch (IOException e) {
				e.printStackTrace();
				responseWrapper.setErrorResponse(new ErrorResponse(-1, e.getMessage()));
			}

			return responseWrapper;
		}

		@Override
		protected void onPostExecute(ResponseWrapper responseWrapper) {
			List<Article> articles = responseWrapper.getArticles();

			if (articles != null && articles.size() > 0) {
				callback.onSuccess(articles);
			}

			if (responseWrapper.getErrorResponse() != null) {
				callback.onFailure(responseWrapper.getErrorResponse());
			}

			callback.onFinish();
		}
	}

	private class ResponseWrapper {

		private List<Article> articles;

		private ErrorResponse errorResponse;

		public List<Article> getArticles() {
			return articles;
		}

		public void setArticles(List<Article> articles) {
			this.articles = articles;
		}

		public ErrorResponse getErrorResponse() {
			return errorResponse;
		}

		public void setErrorResponse(ErrorResponse errorResponse) {
			this.errorResponse = errorResponse;
		}
	}

}
