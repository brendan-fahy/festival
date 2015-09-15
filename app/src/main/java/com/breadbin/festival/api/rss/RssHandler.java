package com.breadbin.festival.api.rss;

import android.text.Html;

import com.breadbin.festival.model.news.Article;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;

public class RssHandler extends DefaultHandler {

  public static final String TITLE = "title";
  public static final String DESCRIPTION = "description";
  public static final String PUB_DATE = "pubDate";
  public static final String GUID = "guid";
  public static final String AUTHOR = "author";
  public static final String CATEGORY = "category";
  public static final String LINK = "link";
  public static final String ITEM = "item";
  private Article currentArticle = new Article();
  private List<Article> articleList = new ArrayList<>();

  private int articlesAdded = 0;

  private static final int ARTICLES_LIMIT = 15;

  StringBuffer chars = new StringBuffer();

  public List<Article> getArticleList() {
    return articleList;
  }

  public void startElement(String uri, String localName, String qName, Attributes atts) {
    chars = new StringBuffer();
  }

  public void endElement(String uri, String localName, String qName) throws SAXException {

    if (localName.equalsIgnoreCase(TITLE)){
      currentArticle.setTitle(chars.toString());
    } else if (localName.equalsIgnoreCase(DESCRIPTION)){
      currentArticle.setDescription(Normalizer
          .normalize(Html.fromHtml(chars.toString()).toString(), Normalizer.Form.NFD)
          .replaceAll("[^\\p{ASCII}]", ""));
    } else if (localName.equalsIgnoreCase(PUB_DATE)){
      currentArticle.setPubDate(chars.toString());
    } else if (localName.equalsIgnoreCase(GUID)){
      currentArticle.setGuid(chars.toString());
    } else if (localName.equalsIgnoreCase(AUTHOR)){
      currentArticle.setAuthor(chars.toString());
    } else if (localName.equalsIgnoreCase(CATEGORY)){
      currentArticle.setCategory(chars.toString());
    } else if (localName.equalsIgnoreCase(LINK)) {
      currentArticle.setLink(chars.toString());
    }

    // Check if looking for article, and if article is complete
    if (localName.equalsIgnoreCase(ITEM)) {

      articleList.add(currentArticle);

      currentArticle = new Article();

      // Lets check if we've hit our limit on number of articleList
      articlesAdded++;
      if (articlesAdded >= ARTICLES_LIMIT) {
        throw new SAXException();
      }
    }
  }

  public void characters(char ch[], int start, int length) {
    chars.append(new String(ch, start, length));
  }
}
