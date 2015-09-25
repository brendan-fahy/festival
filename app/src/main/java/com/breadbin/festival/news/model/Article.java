package com.breadbin.festival.news.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Article implements Parcelable {

  private String title;

  private String description;

  private String pubDate;

  private String guid;

  private String link;

  private String author;

  private String category;

  private Article(String title, String description, String pubDate, String guid, String link,
                  String author, String category) {
    this.title = title;
    this.description = description;
    this.pubDate = pubDate;
    this.guid = guid;
    this.link = link;
    this.author = author;
    this.category = category;
  }

  public String getTitle() {
    return title;
  }

  public String getDescription() {
    return description;
  }

  public String getPubDate() {
    return pubDate;
  }

  public String getGuid() {
    return guid;
  }

  public String getLink() {
    return link;
  }

  public String getAuthor() {
    return author;
  }

  public String getCategory() {
    return category;
  }

  public static class Builder {
    private String title;
    private String description;
    private String pubDate;
    private String guid;
    private String link;
    private String author;
    private String category;

    public Builder withTitle(String title) {
      this.title = title;
      return this;
    }

    public Builder withDescription(String description) {
      this.description = description;
      return this;
    }

    public Builder withPubDate(String pubDate) {
      this.pubDate = pubDate;
      return this;
    }

    public Builder withGuid(String guid) {
      this.guid = guid;
      return this;
    }

    public Builder withLink(String link) {
      this.link = link;
      return this;
    }

    public Builder withAuthor(String author) {
      this.author = author;
      return this;
    }

    public Builder withCategory(String category) {
      this.category = category;
      return this;
    }

    public Article build() {
      return new Article(title, description, pubDate, guid, link, author, category);
    }
  }

  protected Article(Parcel in) {
    title = in.readString();
    description = in.readString();
    pubDate = in.readString();
    guid = in.readString();
    link = in.readString();
    author = in.readString();
    category = in.readString();
  }

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(title);
    dest.writeString(description);
    dest.writeString(pubDate);
    dest.writeString(guid);
    dest.writeString(link);
    dest.writeString(author);
    dest.writeString(category);
  }

  @SuppressWarnings("unused")
  public static final Parcelable.Creator<Article> CREATOR = new Parcelable.Creator<Article>() {
    @Override
    public Article createFromParcel(Parcel in) {
      return new Article(in);
    }

    @Override
    public Article[] newArray(int size) {
      return new Article[size];
    }
  };
}