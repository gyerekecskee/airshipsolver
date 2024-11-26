package airship;

/**
 * Main class.
 */
public class Main {
  public static final MyFXML FXML = new MyFXML();

  /**
   * Main method, starts the app.
   *
   * @param args arguments to the program
   */
  public static void main(String[] args) {
    try {
      GUI.launch(GUI.class, args);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}