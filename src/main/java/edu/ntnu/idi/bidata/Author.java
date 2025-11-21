package edu.ntnu.idi.bidata;

/**
 * <h1>Author.</h1>
 *
 * <p>Represents an author who writes diary entries.</p>
 */
public class Author {

  private static final int MIN_NAME_LENGTH = 1;
  private static final int MAX_NAME_LENGTH = 50;

  private final int id;
  private final String name;

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

  /**
   * Validates and trims the author name.
   *
   * @param name The name to validate.
   * @return The trimmed name.
   * @throws IllegalArgumentException If the name is null or longer than the maximum length.
   */
  private String validateName(String name) {
    if (name == null || name.trim().isEmpty()) {
      throw new IllegalArgumentException("Name must not be null or empty.");
    }

    String trimmedName = name.trim();
    if (trimmedName.length() > MAX_NAME_LENGTH) {
      throw new IllegalArgumentException("Name must not be longer than " + MAX_NAME_LENGTH + ".");
    }
    return trimmedName;
  }

  /**
   * Gets the ID of the author.
   *
   * @return The author ID.
   */
  public int getId() {
    return id;
  }

  /**
   * Gets the name of the author.
   *
   * @return The author name.
   */
  public String getName() {
    return name;
  }

  @Override
  public String toString() {
    return "Author [id=" + id + ", name=" + name + "]";
  }
}
