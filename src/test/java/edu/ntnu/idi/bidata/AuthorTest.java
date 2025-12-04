package edu.ntnu.idi.bidata;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import edu.ntnu.idi.bidata.author.Author;
import org.junit.jupiter.api.Test;

/**
 * Test class for Author.
 */
class AuthorTest {

  @Test
  void testCreateValidAuthor() {
    int id = 1;
    String name = "Ola Nordmann";

    Author author = new Author(id, name);

    assertEquals(1, author.id());
    assertEquals("Ola Nordmann", author.name());
  }

  @Test
  void testCreateAuthorWithWhitespace() {
    int id = 2;
    String name = "  Kari Nordmann  ";

    Author author = new Author(id, name);

    assertEquals(2, author.id());
    assertEquals("Kari Nordmann", author.name());
  }

  @Test
  void testCreateAuthorWithMaxNameLength() {
    int id = 3;
    String name = "A".repeat(50);

    Author author = new Author(id, name);

    assertEquals(3, author.id());
    assertEquals(50, author.name().length());
  }

  @Test
  void testAuthorToString() {
    Author author = new Author(1, "Test Author");

    String result = author.toString();

    assertTrue(result.contains("id=1"));
    assertTrue(result.contains("name=Test Author"));
  }

  @Test
  void testCreateAuthorWithNegativeId() {
    int id = -1;
    String name = "Ola Nordmann";

    IllegalArgumentException exception = assertThrows(
        IllegalArgumentException.class,
        () -> new Author(id, name)
    );
    assertEquals("ID must be a positive number.", exception.getMessage());
  }

  @Test
  void testCreateAuthorWithZeroId() {
    int id = 0;
    String name = "Ola Nordmann";

    IllegalArgumentException exception = assertThrows(
        IllegalArgumentException.class,
        () -> new Author(id, name)
    );
    assertEquals("ID must be a positive number.", exception.getMessage());
  }

  @Test
  void testCreateAuthorWithNullName() {
    int id = 1;
    String name = null;

    IllegalArgumentException exception = assertThrows(
        IllegalArgumentException.class,
        () -> new Author(id, name)
    );
    assertEquals("Name must not be null or empty.", exception.getMessage());
  }

  @Test
  void testCreateAuthorWithEmptyName() {
    int id = 1;
    String name = "";

    IllegalArgumentException exception = assertThrows(
        IllegalArgumentException.class,
        () -> new Author(id, name)
    );
    assertEquals("Name must not be null or empty.", exception.getMessage());
  }

  @Test
  void testCreateAuthorWithWhitespaceOnlyName() {
    int id = 1;
    String name = "   ";

    IllegalArgumentException exception = assertThrows(
        IllegalArgumentException.class,
        () -> new Author(id, name)
    );
    assertEquals("Name must not be null or empty.", exception.getMessage());
  }

  @Test
  void testCreateAuthorWithTooLongName() {
    int id = 1;
    String name = "A".repeat(51);

    IllegalArgumentException exception = assertThrows(
        IllegalArgumentException.class,
        () -> new Author(id, name)
    );
    assertEquals("Name must not be longer than 50.", exception.getMessage());
  }
}