package edu.ntnu.idi.bidata;

/**
 * <h1>Author.</h1>
 *
 * <p>Represents an author who writes diary entries.</p>
 */
public class Author {

  private static final int MIN_NAME_LENGTH = 1;
  private static final int MAX_NAME_LENGTH = 50;

  private int id;
  private String name;

  /**
   * Constructs a new author with the specified ID and name.
   *
   * @param id   The ID of the author.
   * @param name The name of the author.
   * @throws IllegalArgumentException If ID is not a positive number.
   */
  public Author(int id, String name) {
    if (id <= 0) {
      throw new IllegalArgumentException("ID must be a positive number.");
    }

    this.id = id;
    this.name = name;
  }

  
}
