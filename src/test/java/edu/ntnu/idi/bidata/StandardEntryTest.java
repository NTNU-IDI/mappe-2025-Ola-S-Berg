package edu.ntnu.idi.bidata;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import edu.ntnu.idi.bidata.author.Author;
import edu.ntnu.idi.bidata.diary.StandardEntry;
import java.time.LocalDateTime;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test class for StandardEntry.
 */
class StandardEntryTest {

  private Author author;
  private LocalDateTime timestamp;

  @BeforeEach
  void setUp() {
    author = new Author(1, "Test Author");
    timestamp = LocalDateTime.of(2025, 12, 4, 14, 30);
  }

  @Test
  void testCreateValidStandardEntry() {
    int id = 1;
    String title = "My First Entry";
    String content = "This is my first diary entry.";
    String category = "Personal";

    StandardEntry entry = new StandardEntry(id, author, timestamp, title, content, category);

    assertEquals(1, entry.getId());
    assertEquals("Test Author", entry.getAuthor().name());
    assertEquals(timestamp, entry.getTimestamp());
    assertEquals("My First Entry", entry.getTitle());
    assertEquals("This is my first diary entry.", entry.getContent());
    assertEquals("Personal", entry.getCategory());
    assertEquals("Standard", entry.getEntryType());
  }

  @Test
  void testStandardEntryHasNoTemplateFields() {
    StandardEntry entry = new StandardEntry(1, author, timestamp, "Title", "Content", "Category");

    Map<String, String> fields = entry.getTemplateFields();
    assertTrue(fields.isEmpty());
  }

  @Test
  void testGetFormattedTimestamp() {
    StandardEntry entry = new StandardEntry(1, author, timestamp, "Title", "Content", "Category");

    String formatted = entry.getFormattedTimestamp();

    assertEquals("04.12.2025 14:30", formatted);
  }

  @Test
  void testCreateEntryWithWhitespaceInFields() {
    String title = "  Title with spaces  ";
    String content = "  Content with spaces  ";
    String category = "  Category  ";

    StandardEntry entry = new StandardEntry(1, author, timestamp, title, content, category);

    assertEquals("Title with spaces", entry.getTitle());
    assertEquals("Content with spaces", entry.getContent());
    assertEquals("Category", entry.getCategory());
  }

  @Test
  void testCreateEntryWithMaxTitleLength() {
    String title = "A".repeat(100);

    StandardEntry entry = new StandardEntry(1, author, timestamp, title, "Content", "Category");

    assertEquals(100, entry.getTitle().length());
  }

  @Test
  void testCreateEntryWithMaxContentLength() {
    String content = "A".repeat(5000);

    StandardEntry entry = new StandardEntry(1, author, timestamp, "Title", content, "Category");

    assertEquals(5000, entry.getContent().length());
  }


  @Test
  void testCreateEntryWithNegativeId() {
    int id = -1;

    IllegalArgumentException exception = assertThrows(
        IllegalArgumentException.class,
        () -> new StandardEntry(id, author, timestamp, "Title", "Content", "Category")
    );
    assertEquals("ID must be a positive number", exception.getMessage());
  }

  @Test
  void testCreateEntryWithZeroId() {
    int id = 0;

    IllegalArgumentException exception = assertThrows(
        IllegalArgumentException.class,
        () -> new StandardEntry(id, author, timestamp, "Title", "Content", "Category")
    );
    assertEquals("ID must be a positive number", exception.getMessage());
  }

  @Test
  void testCreateEntryWithNullAuthor() {

    IllegalArgumentException exception = assertThrows(
        IllegalArgumentException.class,
        () -> new StandardEntry(1, null, timestamp, "Title", "Content", "Category")
    );
    assertEquals("Author must not be null", exception.getMessage());
  }

  @Test
  void testCreateEntryWithNullTimestamp() {

    IllegalArgumentException exception = assertThrows(
        IllegalArgumentException.class,
        () -> new StandardEntry(1, author, null, "Title", "Content", "Category")
    );
    assertEquals("Timestamp cannot be null", exception.getMessage());
  }

  @Test
  void testCreateEntryWithNullTitle() {

    IllegalArgumentException exception = assertThrows(
        IllegalArgumentException.class,
        () -> new StandardEntry(1, author, timestamp, null, "Content", "Category")
    );
    assertEquals("Title cannot be null or empty", exception.getMessage());
  }

  @Test
  void testCreateEntryWithEmptyTitle() {
    String title = "";

    IllegalArgumentException exception = assertThrows(
        IllegalArgumentException.class,
        () -> new StandardEntry(1, author, timestamp, title, "Content", "Category")
    );
    assertEquals("Title cannot be null or empty", exception.getMessage());
  }

  @Test
  void testCreateEntryWithWhitespaceOnlyTitle() {
    String title = "   ";

    IllegalArgumentException exception = assertThrows(
        IllegalArgumentException.class,
        () -> new StandardEntry(1, author, timestamp, title, "Content", "Category")
    );
    assertEquals("Title cannot be null or empty", exception.getMessage());
  }

  @Test
  void testCreateEntryWithTooLongTitle() {
    String title = "A".repeat(101);

    IllegalArgumentException exception = assertThrows(
        IllegalArgumentException.class,
        () -> new StandardEntry(1, author, timestamp, title, "Content", "Category")
    );
    assertEquals("Title must be between 1 and 100 symbols", exception.getMessage());
  }

  @Test
  void testCreateEntryWithNullContent() {

    IllegalArgumentException exception = assertThrows(
        IllegalArgumentException.class,
        () -> new StandardEntry(1, author, timestamp, "Title", null, "Category")
    );
    assertEquals("Content cannot be null or empty", exception.getMessage());
  }

  @Test
  void testCreateEntryWithEmptyContent() {
    String content = "";

    IllegalArgumentException exception = assertThrows(
        IllegalArgumentException.class,
        () -> new StandardEntry(1, author, timestamp, "Title", content, "Category")
    );
    assertEquals("Content cannot be null or empty", exception.getMessage());
  }

  @Test
  void testCreateEntryWithWhitespaceOnlyContent() {
    String content = "   ";

    IllegalArgumentException exception = assertThrows(
        IllegalArgumentException.class,
        () -> new StandardEntry(1, author, timestamp, "Title", content, "Category")
    );
    assertEquals("Content cannot be null or empty", exception.getMessage());
  }

  @Test
  void testCreateEntryWithTooLongContent() {
    String content = "A".repeat(5001);

    IllegalArgumentException exception = assertThrows(
        IllegalArgumentException.class,
        () -> new StandardEntry(1, author, timestamp, "Title", content, "Category")
    );
    assertEquals("Content must be between 1 and 5000 symbols", exception.getMessage());
  }

  @Test
  void testCreateEntryWithNullCategory() {
    IllegalArgumentException exception = assertThrows(
        IllegalArgumentException.class,
        () -> new StandardEntry(1, author, timestamp, "Title", "Content", null)
    );
    assertEquals("Category cannot be null or empty", exception.getMessage());
  }

  @Test
  void testCreateEntryWithEmptyCategory() {
    String category = "";

    IllegalArgumentException exception = assertThrows(
        IllegalArgumentException.class,
        () -> new StandardEntry(1, author, timestamp, "Title", "Content", category)
    );
    assertEquals("Category cannot be null or empty", exception.getMessage());
  }

  @Test
  void testCreateEntryWithWhitespaceOnlyCategory() {
    String category = "   ";

    IllegalArgumentException exception = assertThrows(
        IllegalArgumentException.class,
        () -> new StandardEntry(1, author, timestamp, "Title", "Content", category)
    );
    assertEquals("Category cannot be null or empty", exception.getMessage());
  }
}