package com.weezee.newsappstage2.pojo;

public class StoryItem {
    private String title;
    private String  sectionName;
    private String date;
    private String authorName;
    private String imageURL;
    private String bodyText;
    private String webURL;
    private String trailText;

    public StoryItem(String title, String sectionName, String date, String authorName, String imageURL, String bodyText, String webURL, String trailText) {
        this.title = title;
        this.sectionName = sectionName;
        this.date = date;
        this.authorName = authorName;
        this.imageURL = imageURL;
        this.bodyText = bodyText;
        this.webURL = webURL;
        this.trailText=trailText;
    }

    public String getTitle() {
        return title;
    }

    public String getSectionName() {
        return sectionName;
    }

    public String getDate() {
        return date;
    }

    public String getAuthorName() {
        return authorName;
    }

    public String getImageURL() {
        return imageURL;
    }

    public String getBodyText() {
        return bodyText;
    }

    public String getWebURL() {
        return webURL;
    }

    public String getTrailText() {
        return trailText;
    }

    public boolean hasNoImageURL(){
        return (this.imageURL.equals("No thumbnail"));
    }



}