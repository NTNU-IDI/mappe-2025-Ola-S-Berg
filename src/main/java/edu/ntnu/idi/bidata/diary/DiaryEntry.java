package edu.ntnu.idi.bidata.diary;

import edu.ntnu.idi.bidata.author.Author;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

/**
 * <h1>Diary Entry.</h1>
 *
 * <p>Abstract base class representing a diary entry. This class provides common functionality
 * for all types of diary entries and serves as a superclass for specialized entry types</p>
 *
 * <p>Subclasses can use the templateFields map to store and display specialized fields.</p>
 */
public abstract class DiaryEntry {

  private static final int MIN_TITLE_LENGTH = 1;
  private static final int MAX_TITLE_LENGTH = 100;
  private static final int MIN_CONTENT_LENGTH = 1;
  private static final int MAX_CONTENT_LENGTH = 5000;

  protected final Map<String, String> templateFields;
  private final int id;
  private final Author author;
  private final LocalDateTime timestamp;
  private final String title;
  private String content;
  private String category;

  /**
   * Constructs a new diary entry.
   *
   * @param id        The ID of the entry.
   * @param author    The author of the entry.
   * @param timestamp The date and time when the entry was created.
   * @param title     The title of the entry.
   * @param content   The content of the entry.
   * @param category  The category of the entry.
   * @throws IllegalArgumentException If ID is a negative number, and if timestamp or author is
   *                                  null.
   */
  public DiaryEntry(int id, Author author, LocalDateTime timestamp,
      String title, String content, String category) {
    if (id <= 0) {
      throw new IllegalArgumentException("ID must be a positive number");
    }
    if (author == null) {
      throw new IllegalArgumentException("Author must not be null");
    }
    if (timestamp == null) {
      throw new IllegalArgumentException("Timestamp cannot be null");
    }

    this.id = id;
    this.author = author;
    this.timestamp = timestamp;
    this.title = validateTitle(title);
    this.content = validateContent(content);
    this.category = validateCategory(category);
    this.templateFields = new HashMap<>();
  }

  /**
   * Returns the type of this diary entry. This method must be implemented by all subclasses.
   *
   * @return The entry type as a string.
   */
  public abstract String getEntryType();

  /**
   * Gets the template-specific fields for this entry. Returns an empty map for StandardEntry, but
   * contains specialized fields.
   *
   * @return A map of field names to field values.
   */
  public Map<String, String> getTemplateFields() {
    return new HashMap<>(templateFields);
  }

  /**
   * Validates whether the diary entry title format is valid.
   *
   * @param title The title to validate.
   * @return The trimmed title.
   */
  private String validateTitle(String title) {
    if (title == null || title.trim().isEmpty()) {
      throw new IllegalArgumentException("Title cannot be null or empty");
    }

    String trimmedTitle = title.trim();
    if (trimmedTitle.length() > MAX_TITLE_LENGTH) {
      throw new IllegalArgumentException(String.format("Title must be between %d and %d symbols",
          MIN_TITLE_LENGTH, MAX_TITLE_LENGTH));
    }
    return trimmedTitle;
  }

  /**
   * Validates whether the diary entry content format is valid.
   *
   * @param content The content to validate.
   * @return The trimmed content.
   */
  private String validateContent(String content) {
    if (content == null || content.trim().isEmpty()) {
      throw new IllegalArgumentException("Content cannot be null or empty");
    }

    String trimmedContent = content.trim();
    if (trimmedContent.length() > MAX_CONTENT_LENGTH) {
      throw new IllegalArgumentException(String.format("Content must be between %d and %d symbols",
          MIN_CONTENT_LENGTH, MAX_CONTENT_LENGTH));
    }
    return trimmedContent;
  }

  /**
   * Validates whether the diary entry category format is valid.
   *
   * @param category The category to validate.
   * @return The trimmed category.
   */
  private String validateCategory(String category) {
    if (category == null || category.trim().isEmpty()) {
      throw new IllegalArgumentException("Category cannot be null or empty");
    }
    return category.trim();
  }

  /**
   * Gets the ID this diary entry.
   *
   * @return The ID of the entry.
   */
  public int getId() {
    return id;
  }

  /**
   * Gets the author of this diary entry.
   *
   * @return The author.
   */
  public Author getAuthor() {
    return author;
  }

  /**
   * Gets the author's name.
   *
   * @return The name of the author.
   */
  public String getAuthorName() {
    return author.name();
  }

  /**
   * Gets the timestamp of this diary entry.
   *
   * @return The timestamp.
   */
  public LocalDateTime getTimestamp() {
    return timestamp;
  }

  /**
   * Gets the formatted timestamp of this diary entry. Format: Day.Month.Year Hour:Minute
   *
   * @return The formatted timestamp.
   */
  public String getFormattedTimestamp() {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
    return timestamp.format(formatter);
  }

  /**
   * Gets the title of this diary entry.
   *
   * @return The title.
   */
  public String getTitle() {
    return title;
  }

  /**
   * Gets the content of this diary entry.
   *
   * @return The content.
   */
  public String getContent() {
    return content;
  }

  /**
   * Sets the content of this diary entry.
   *
   * @param content The content to set.
   */
  public void setContent(String content) {
    this.content = validateContent(content);
  }

  /**
   * Gets the category of this diary entry.
   *
   * @return The category.
   */
  public String getCategory() {
    return category;
  }

  /**
   * Sets the category of this diary entry.
   *
   * @param category The category to set.
   */
  public void setCategory(String category) {
    this.category = validateCategory(category);
  }
}