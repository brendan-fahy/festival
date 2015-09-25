package com.breadbin.festival.news.model.api;

import android.text.Html;

import com.breadbin.festival.news.model.Article;

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
  private Article.Builder builder = new Article.Builder();
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
      builder = builder.withTitle(chars.toString());
    } else if (localName.equalsIgnoreCase(DESCRIPTION)){
      builder = builder.withDescription(Normalizer
          .normalize(Html.fromHtml(chars.toString()).toString(), Normalizer.Form.NFD)
          .replaceAll("[^\\p{ASCII}]", ""));
    } else if (localName.equalsIgnoreCase(PUB_DATE)){
      builder = builder.withPubDate(chars.toString());
    } else if (localName.equalsIgnoreCase(GUID)){
      builder = builder.withGuid(chars.toString());
    } else if (localName.equalsIgnoreCase(AUTHOR)){
      builder = builder.withAuthor(chars.toString());
    } else if (localName.equalsIgnoreCase(CATEGORY)){
      builder = builder.withCategory(chars.toString());
    } else if (localName.equalsIgnoreCase(LINK)) {
      builder = builder.withLink(chars.toString());
    }

    // Check if looking for article, and if article is complete
    if (localName.equalsIgnoreCase(ITEM)) {

      articleList.add(builder.build());

      builder = new Article.Builder();

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
