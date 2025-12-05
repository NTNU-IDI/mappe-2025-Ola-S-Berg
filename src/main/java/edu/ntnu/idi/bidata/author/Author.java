package edu.ntnu.idi.bidata.author;

/**
 * <h1>Author.</h1>
 *
 * <p>Represents an author who writes diary entries. This is an immutable record that stores
 * author identification and name information.</p>
 *
 * <p>Responsibilities:</p>
 * <ul>
 *   <li>Store author identification (ID)</li>
 *   <li>Store author name with validation</li>
 *   <li>Ensure immutability of author data</li>
 *   <li>Validate author name length and format</li>
 *   <li>Provide a string representation for display</li>
 * </ul>
 */
public record Author(int id, String name) {

  private static final int MAX_NAME_LENGTH = 50;

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
    this.name = validateName(name);

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
  @Override
  public int id() {
    return id;
  }

  /**
   * Gets the name of the author.
   *
   * @return The author name.
   */
  @Override
  public String name() {
    return name;
  }

  @Override
  public String toString() {
    return "Author [id=" + id + ", name=" + name + "]";
  }
}
