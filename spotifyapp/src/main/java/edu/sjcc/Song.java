
package edu.sjcc;

public class Song {

  private String name;
  private String artist;
  private String fileName;
  private String year;
  private String genre;
  private boolean isFavorite;
  private String comments;

  // 보너스 필드 추가

  // serializes attributes into a string
  public String toString() {

    // since the object is complex, we return a JSON formatted string
    String s = "{ ";
    s += "name: " + name;
    s += ", ";
    s += "artist: " + artist;
    s += ", ";
    s += "fileName: " + fileName;
    s += ",";
    s += "year:" + year;
    s += ",";
    s += "genre:" + genre;

    s += ",isFavorite " + isFavorite;
    s += ",comments " + (comments == null ? "None" : comments);
    s += "}";

    return s;
  }

  // getters
  public String name() {
    return this.name;
  }

  public String artist() {
    return this.artist;
  }

  public String fileName() {
    return this.fileName;
  }

  public String year() {
    return this.year;
  }

  public String genre() {
    return this.genre;
  }

  public void setComments(String text) {
    this.comments = text;
  }

}
