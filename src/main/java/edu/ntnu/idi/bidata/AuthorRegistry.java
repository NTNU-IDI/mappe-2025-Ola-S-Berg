package edu.ntnu.idi.bidata;

import java.util.HashMap;

/**
 * <h1>Author Registry.</h1>
 *
 * <p>Registry for managing a collection of authors.</p>
 */
public class AuthorRegistry {

  private final HashMap<Integer, Author> authors;
  private int nextId;

  public AuthorRegistry() {
    this.authors = new HashMap<>();
    this.nextId = 0;
  }
}
