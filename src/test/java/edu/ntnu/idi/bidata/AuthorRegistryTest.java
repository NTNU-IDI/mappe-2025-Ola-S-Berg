package edu.ntnu.idi.bidata
;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import edu.ntnu.idi.bidata.author.Author;
import edu.ntnu.idi.bidata.author.AuthorRegistry;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test class for AuthorRegistry.
 */
class AuthorRegistryTest {

  private AuthorRegistry registry;

  @BeforeEach
  void setUp() {
    registry = new AuthorRegistry();
  }


  @Test
  void testCreateEmptyRegistry() {

    List<Author> authors = registry.getAllAuthors();

    assertNotNull(authors);
    assertTrue(authors.isEmpty());
  }

  @Test
  void testCreateAndAddAuthor() {
    String name = "John Doe";

    Author author = registry.createAndAddAuthor(name);

    assertNotNull(author);
    assertEquals(1, author.id());
    assertEquals("John Doe", author.name());
    assertEquals(1, registry.getAllAuthors().size());
  }

  @Test
  void testCreateMultipleAuthorsWithIncrementingIds() {
    String name1 = "John Doe";
    String name2 = "Jane Smith";
    String name3 = "Bob Johnson";

    Author author1 = registry.createAndAddAuthor(name1);
    Author author2 = registry.createAndAddAuthor(name2);
    Author author3 = registry.createAndAddAuthor(name3);

    assertEquals(1, author1.id());
    assertEquals(2, author2.id());
    assertEquals(3, author3.id());
    assertEquals(3, registry.getAllAuthors().size());
  }

  @Test
  void testAddAuthorManually() {
    Author author = new Author(10, "Test Author");

    registry.addAuthor(author);

    assertEquals(1, registry.getAllAuthors().size());
    assertEquals(author, registry.findAuthorById(10));
  }

  @Test
  void testFindAuthorById() {
    registry.createAndAddAuthor("Ola Nordmann");
    registry.createAndAddAuthor("Kari Nordmann");

    Author found = registry.findAuthorById(2);

    assertNotNull(found);
    assertEquals("Kari Nordmann", found.name());
  }

  @Test
  void testFindAuthorByNameExactMatch() {
    registry.createAndAddAuthor("Ola Nordmann");
    registry.createAndAddAuthor("Kari Nordmann");

    List<Author> results = registry.findAuthorByName("Ola Nordmann");

    assertEquals(1, results.size());
    assertEquals("Ola Nordmann", results.getFirst().name());
  }

  @Test
  void testFindAuthorByNamePartialMatch() {
    registry.createAndAddAuthor("Ola Nordmann");
    registry.createAndAddAuthor("Ola Svenskmann");
    registry.createAndAddAuthor("Kari Nordmann");

    List<Author> results = registry.findAuthorByName("Ola");

    assertEquals(2, results.size());
  }

  @Test
  void testFindAuthorByNameCaseInsensitive() {
    registry.createAndAddAuthor("Ola Nordmann");

    List<Author> results = registry.findAuthorByName("Ola Nordmann");

    assertEquals(1, results.size());
    assertEquals("Ola Nordmann", results.getFirst().name());
  }

  @Test
  void testDeleteAuthorById() {
    Author author = registry.createAndAddAuthor("Ola Nordmann");
    int id = author.id();

    boolean deleted = registry.deleteAuthorById(id);

    assertTrue(deleted);
    assertNull(registry.findAuthorById(id));
    assertTrue(registry.getAllAuthors().isEmpty());
  }

  @Test
  void testGetAllAuthors() {
    registry.createAndAddAuthor("Ola Nordmann");
    registry.createAndAddAuthor("Kari Nordmann");
    registry.createAndAddAuthor("Ola Svenskmann");

    List<Author> authors = registry.getAllAuthors();

    assertEquals(3, authors.size());
  }


  @Test
  void testAddNullAuthor() {

    IllegalArgumentException exception = assertThrows(
        IllegalArgumentException.class,
        () -> registry.addAuthor(null)
    );
    assertEquals("Author cannot be null", exception.getMessage());
  }

  @Test
  void testAddDuplicateAuthorId() {
    Author author1 = new Author(1, "Ola Nordmann");
    Author author2 = new Author(1, "Kari Nordmann");
    registry.addAuthor(author1);

    IllegalArgumentException exception = assertThrows(
        IllegalArgumentException.class,
        () -> registry.addAuthor(author2)
    );
    assertEquals("Author with ID 1 already exists", exception.getMessage());
  }

  @Test
  void testFindAuthorByIdNonExistent() {
    registry.createAndAddAuthor("Ola Nordmann");

    Author found = registry.findAuthorById(999);

    assertNull(found);
  }

  @Test
  void testFindAuthorByNullName() {

    IllegalArgumentException exception = assertThrows(
        IllegalArgumentException.class,
        () -> registry.findAuthorByName(null)
    );
    assertEquals("Author name cannot be null or empty", exception.getMessage());
  }

  @Test
  void testFindAuthorByEmptyName() {
    String name = "";

    IllegalArgumentException exception = assertThrows(
        IllegalArgumentException.class,
        () -> registry.findAuthorByName(name)
    );
    assertEquals("Author name cannot be null or empty", exception.getMessage());
  }

  @Test
  void testFindAuthorByWhitespaceName() {
    String name = "   ";

    IllegalArgumentException exception = assertThrows(
        IllegalArgumentException.class,
        () -> registry.findAuthorByName(name)
    );
    assertEquals("Author name cannot be null or empty", exception.getMessage());
  }

  @Test
  void testFindAuthorByNameNoMatches() {
    registry.createAndAddAuthor("Ola Nordmann");
    registry.createAndAddAuthor("Kari Nordmann");

    List<Author> results = registry.findAuthorByName("Test");

    assertTrue(results.isEmpty());
  }

  @Test
  void testDeleteAuthorByIdNonExistent() {
    registry.createAndAddAuthor("Ola Nordmann");

    boolean deleted = registry.deleteAuthorById(999);

    assertFalse(deleted);
    assertEquals(1, registry.getAllAuthors().size());
  }

  @Test
  void testCreateAndAddAuthorWithInvalidName() {
    String name = "";

    assertThrows(
        IllegalArgumentException.class,
        () -> registry.createAndAddAuthor(name)
    );
  }
}