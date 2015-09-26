package com.breadbin.festival.news.model;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;

public class ArticleTest {

  private static final String AUTHOR = "author";
  private static final String CATEGORY = "category";
  private static final String DESCRIPTION = "description";
  private static final String GUID = "guid";
  private static final String LINK = "link";
  private static final String PUB_DATE = "pubDate";
  private static final String TITLE = "title";

  private Article article;

  @Before
  public void setUp() throws Exception {
    article = new Article.Builder()
        .withAuthor(AUTHOR)
        .withCategory(CATEGORY)
        .withDescription(DESCRIPTION)
        .withGuid(GUID)
        .withLink(LINK)
        .withPubDate(PUB_DATE)
        .withTitle(TITLE)
        .build();
  }

  @Test
  public void shouldCreateArticleUsingBuilder() throws Exception {
    assertEquals(AUTHOR, article.getAuthor());
    assertEquals(CATEGORY, article.getCategory());
    assertEquals(DESCRIPTION, article.getDescription());
    assertEquals(GUID, article.getGuid());
    assertEquals(LINK, article.getLink());
    assertEquals(PUB_DATE, article.getPubDate());
    assertEquals(TITLE, article.getTitle());
  }
}