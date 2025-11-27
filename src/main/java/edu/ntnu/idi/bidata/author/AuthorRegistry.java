package edu.ntnu.idi.bidata.author;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * <h1>Author Registry.</h1>
 *
 * <p>Registry for managing a collection of authors.</p>
 */
public class AuthorRegistry {

  private final HashMap<Integer, Author> authors;
  private int nextId;

  /**
   * Constructs a new empty author registry.
   */
  public AuthorRegistry() {
    this.authors = new HashMap<>();
    this.nextId = 1;
  }

  /**
   * Adds a new author to the registry.
   *
   * @param author The author to add.
   * @throws IllegalArgumentException If author is null or if an author with same ID already
   *                                  exists.
   */
  public void addAuthor(Author author) {
    if (author == null) {
      throw new IllegalArgumentException("Author cannot be null");
    }
    if (this.authors.containsKey(author.id())) {
      throw new IllegalArgumentException("Author with ID " + author.id() + " already exists");
    }
    authors.put(author.id(), author);
  }

  /**
   * Creates and adds a new author with a new ID.
   *
   * @param name The name of the author.
   * @return The Created author.
   */
  public Author createAndAddAuthor(String name) {
    Author author = new Author(nextId++, name);
    addAuthor(author);
    return author;
  }

  /**
   * Finds an author by their ID.
   *
   * @param id The ID of the author to find.
   * @return The author with the specified ID.
   */
  public Author findAuthorById(int id) {
    return authors.get(id);
  }

  /**
   * Searches for authors by name.
   *
   * @param name The name to search for.
   * @return A list of matching authors.
   * @throws IllegalArgumentException If name is null or empty.
   */
  public List<Author> findAuthorByName(String name) {
    if (name == null || name.trim().isEmpty()) {
      throw new IllegalArgumentException("Author name cannot be null or empty");
    }

    String searchName = name.trim().toLowerCase();
    List<Author> results = new ArrayList<>();

    for (Author author : authors.values()) {
      if (author.name().toLowerCase().contains(searchName)) {
        results.add(author);
      }
    }
    return results;
  }

  /**
   * Finds an author by exact name match.
   *
   * @param name The exact name to search for.
   * @return The author with matching name, or null if not found.
   * @throws IllegalArgumentException If name is null or empty.
   */
  public Author findAuthorByExactName(String name) {
    if (name == null || name.trim().isEmpty()) {
      throw new IllegalArgumentException("Author name cannot be null or empty");
    }

    String searchName = name.trim();

    for (Author author : authors.values()) {
      if (author.name().equals(searchName)) {
        return author;
      }
    }
    return null;
  }

  /**
   * Deletes an author from the registry.
   *
   * @param author The author to delete.
   * @return True if the author was found and deleted, false otherwise.
   * @throws IllegalArgumentException If author is null.
   */
  public boolean deleteAuthor(Author author) {
    if (author == null) {
      throw new IllegalArgumentException("Author cannot be null");
    }
    return authors.remove(author.id()) != null;
  }

  /**
   * Deletes an author by their ID.
   *
   * @param id The ID of the author to delete.
   * @return True if the author was found and deleted, false otherwise.
   */
  public boolean deleteAuthorById(int id) {
    return authors.remove(id) != null;
  }

  /**
   * Returns all authors in the registry.
   *
   * @return A list of all authors.
   */
  public List<Author> getAllAuthors() {
    return new ArrayList<>(authors.values());
  }
}
