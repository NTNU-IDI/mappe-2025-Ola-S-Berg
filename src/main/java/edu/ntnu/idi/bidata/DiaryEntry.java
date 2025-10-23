package edu.ntnu.idi.bidata;

import java.time.LocalDateTime;

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
  private final String content;
  private final String category;

  public DiaryEntry(int id, String author, String title, String content, String category) {
    this(id, author, LocalDateTime.now(), title, content, category);
  }

  public DiaryEntry(int id, String author, LocalDateTime timestamp,
      String title, String content, String category) {
    if (id <= 0) {
      throw new IllegalArgumentException("ID must be a positive number");
    }
    if (timestamp == null) {
      throw new IllegalArgumentException("Timestamp cannot be null");
    }

    this.id = id;
    this.author = null;
    this.timestamp = timestamp;
    this.title = null;
    this.content = null;
    this.category = null;
  }
}
