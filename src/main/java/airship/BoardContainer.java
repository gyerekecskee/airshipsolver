package airship;

/**
 * Container class for Board.
 */
public class BoardContainer {
  private static final BoardContainer INSTANCE = new BoardContainer();
  private Board board;

  private BoardContainer() {

  }

  public static BoardContainer getInstance() {
    return INSTANCE;
  }

  public void setBoard(Board board) {
    this.board = board;
  }

  public Board getBoard() {
    return board;
  }
}
