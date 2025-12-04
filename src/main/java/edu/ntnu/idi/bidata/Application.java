package edu.ntnu.idi.bidata;

import edu.ntnu.idi.bidata.ui.UserInterface;

/**
 * <h1>Application.</h1>
 *
 * <p>Main entry point for the diary application. This class is responsible only for
 * starting the application by initializing and launching the user interface.</p>
 */
public class Application {

  /**
   * The main entry point of the application.
   *
   * @param args The command line arguments.
   */
  public static void main(String[] args) {
    UserInterface ui = new UserInterface();
    ui.init();
    ui.start();
  }
}