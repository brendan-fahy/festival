package com.breadbin.festival.api.rss;

import com.breadbin.festival.model.error.ErrorResponse;
import com.breadbin.festival.model.news.Article;

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

import rx.Observable;
import rx.Subscriber;

public class RssConnector {

	private final String rssEndpoint;

	public RssConnector(String rssEndpoint) {
		this.rssEndpoint = rssEndpoint;
	}

	public Observable<List<Article>> getRssArticles() {
    return Observable.create(
        new Observable.OnSubscribe<List<Article>>() {
          @Override
          public void call(Subscriber<? super List<Article>> subscriber) {
            URL url;

            ResponseWrapper responseWrapper = new ResponseWrapper();
            try {
              SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
              SAXParser saxParser = saxParserFactory.newSAXParser();
              XMLReader xmlReader = saxParser.getXMLReader();

              url = new URL(rssEndpoint);
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

            List<Article> articles = responseWrapper.getArticles();

            if (articles != null && articles.size() > 0) {
              subscriber.onNext(articles);
              subscriber.onCompleted();
            }

            if (responseWrapper.getErrorResponse() != null) {
              subscriber.onError(new IOException(responseWrapper.getErrorResponse().getMessage()));
            }
            subscriber.onCompleted();
          }
        });
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
