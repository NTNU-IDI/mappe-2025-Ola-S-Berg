package edu.ntnu.idi.bidata.ui;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

/**
 * <h1>Input reader.</h1>
 *
 * <p>Handles all user input operations with built-in validation and error handling. Provides a
 * centralized way to read input from the user, ensuring consistent data validation and error
 * messages throughout the application.</p>
 *
 * <p>Responsibilities:</p>
 * <ul>
 *   <li>Read and validate integer input with automatic error handling</li>
 *   <li>Read and trim string input</li>
 *   <li>Parse and validate date input in "dd.MM.yyyy" format</li>
 *   <li>Handle yes/no confirmation prompts</li>
 *   <li>Provide consistent error messages for invalid input</li>
 * </ul>
 */
public record InputReader(Scanner scanner) {

  private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy");

  /**
   * Reads an integer from user with error handling.
   *
   * @param prompt The prompt to display.
   * @return The integer entered by the user.
   */
  public int readInt(String prompt) {
    while (true) {
      try {
        System.out.print(prompt);
        String input = scanner.nextLine().trim();
        return Integer.parseInt(input);
      } catch (NumberFormatException e) {
        System.out.println("Invalid input. Please enter a number.");
      }
    }
  }

  /**
   * Reads a string from user.
   *
   * @param prompt The prompt to display.
   * @return The trimmed string entered by the user.
   */
  public String readString(String prompt) {
    System.out.print(prompt);
    return scanner.nextLine().trim();
  }

  /**
   * Reads a date from user with validation.
   *
   * @param prompt The prompt to display.
   * @return The parsed LocalDate, or null if invalid.
   */
  public LocalDate readDate(String prompt) {
    System.out.print(prompt);
    String dateStr = scanner.nextLine().trim();

    try {
      return LocalDate.parse(dateStr, DATE_FORMATTER);
    } catch (Exception e) {
      System.out.println("Invalid date format. Please use dd.MM.yyyy");
      return null;
    }
  }

  /**
   * Reads a yes/no confirmation from user.
   *
   * @param prompt The prompt to display.
   * @return true if user confirms, false otherwise.
   */
  public boolean readConfirmation(String prompt) {
    System.out.print(prompt);
    String confirmation = scanner.nextLine().trim().toLowerCase();
    return confirmation.equals("y") || confirmation.equals("yes");
  }

  /**
   * Closes the scanner.
   */
  public void close() {
    scanner.close();
  }

  /**
   * Gets the date formatter used by this reader.
   *
   * @return The date formatter.
   */
  public DateTimeFormatter getDateFormatter() {
    return DATE_FORMATTER;
  }
}