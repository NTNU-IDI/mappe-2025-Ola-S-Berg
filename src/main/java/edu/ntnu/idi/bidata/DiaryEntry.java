package edu.ntnu.idi.bidata;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * <h1>Diary Entry</h1>
 *
 * <p>Represents a single entry in a diary. Has responsibility for saving and handling information
 * related to a single diary entry. Ensures the data saved is valid and consistent.
 * </p>
 */
public class DiaryEntry {

  private static final int MIN_TITLE_LENGTH = 1;
  private static final int MAX_TITLE_LENGTH = 100;
  private static final int MIN_AUTHOR_LENGTH = 1;
  private static final int MAX_AUTHOR_LENGTH = 50;
  private static final int MIN_CONTENT_LENGTH = 1;
  private static final int MAX_CONTENT_LENGTH = 5000;

  private final int id;
  private final String author;
  private final LocalDateTime timestamp;
  private final String title;
  private String content;
  private String category;

  public DiaryEntry(int id, String author, LocalDateTime timestamp,
      String title, String content, String category) {
    if (id <= 0) {
      throw new IllegalArgumentException("ID must be a positive number");
    }
    if (timestamp == null) {
      throw new IllegalArgumentException("Timestamp cannot be null");
    }

    this.id = id;
    this.author = validateAuthor(author);
    this.timestamp = timestamp;
    this.title = validateTitle(title);
    this.content = validateContent(content);
    this.category = validateCategory(category);
  }

  private String validateAuthor(String author) {
    if (author == null || author.trim().isEmpty()) {
      throw new IllegalArgumentException("Author cannot be null or empty");
    }

    String trimmedAuthor = author.trim();
    if (trimmedAuthor.length() > MAX_AUTHOR_LENGTH) {
      throw new IllegalArgumentException(
          String.format("Author name must be between %d and %d symbols",
              MIN_AUTHOR_LENGTH, MAX_AUTHOR_LENGTH)
      );
    }
    return trimmedAuthor;
  }

  private String validateTitle(String title) {
    if (title == null || title.trim().isEmpty()) {
      throw new IllegalArgumentException("Title cannot be null or empty");
    }

    String trimmedTitle = title.trim();
    if (trimmedTitle.length() > MAX_TITLE_LENGTH) {
      throw new IllegalArgumentException(String.format("Title must be between %d and %d symbols",
          MAX_TITLE_LENGTH, MIN_TITLE_LENGTH));
    }
    return trimmedTitle;
  }

  private String validateContent(String content) {
    if (content == null || content.trim().isEmpty()) {
      throw new IllegalArgumentException("Content cannot be null or empty");
    }

    String trimmedContent = content.trim();
    if (trimmedContent.length() > MAX_CONTENT_LENGTH) {
      throw new IllegalArgumentException(String.format("Content must be between %d and %d symbols",
          MAX_CONTENT_LENGTH, MIN_CONTENT_LENGTH));
    }
    return trimmedContent;
  }

  private String validateCategory(String category) {
    if (category == null || category.trim().isEmpty()) {
      throw new IllegalArgumentException("Category cannot be null or empty");
    }
    return category.trim();
  }

  public int getId() {
    return id;
  }

  public String getAuthor() {
    return author;
  }

  public LocalDateTime getTimestamp() {
    return timestamp;
  }

  public String getFormattedTimestamp() {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
    return timestamp.format(formatter);
  }

  public String getTitle() {
    return title;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = validateContent(content);
  }

  public String getCategory() {
    return category;
  }

  public void setCategory(String category) {
    this.category = validateCategory(category);
  }
}
