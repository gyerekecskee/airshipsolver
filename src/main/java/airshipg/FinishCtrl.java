package airshipg;

import com.graph.AdjacencyMapGraph;
import com.graph.Edge;
import com.graph.Graph;
import com.graph.PositionalList;
import com.graph.ProbeHashMap;
import com.graph.Vertex;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.GridPane;

public class FinishCtrl implements Initializable {

  private static final Graph<Board> reachedPositions = new AdjacencyMapGraph<>();
  private BoardContainer boardContainer = BoardContainer.getInstance();
  private List<Board> solution;
  private int index = 0;

  @FXML
  public Label inf;
  @FXML
  public Button next;
  @FXML
  public Button prev;
  @FXML
  public Button last;
  @FXML
  public Button first;
  @FXML
  public GridPane gPane;
  @FXML
  public Label l00;
  @FXML
  public Label l10;
  @FXML
  public Label l20;
  @FXML
  public Label l01;
  @FXML
  public Label l11;
  @FXML
  public Label l21;
  @FXML
  public Label l02;
  @FXML
  public Label l12;
  @FXML
  public Label l22;


  public void solve() {
    Board startingPosition = boardContainer.getBoard();

    Queue<Vertex<Board>> unFinished = new LinkedList<>();
    Vertex<Board> inserted = reachedPositions.insertVertex(startingPosition.copy());
    unFinished.add(inserted);
    Vertex<Board> insertedNew;
    Vertex<Board> current = inserted;
    while (!unFinished.isEmpty() && !current.getElement().canAirshipEscape()) {
      current = unFinished.remove();
      for (Board successor : current.getElement().getAllValidSuccessors()) {
        airshipg.Pair<Boolean, Vertex<Board>> result = reachedPositions.contains(successor);
        if (result.getFirst()) {
          if (!areTheyConnected(current, result.getSecond())) {
            reachedPositions.insertEdge(current, result.getSecond());
          }
        } else {
          insertedNew = reachedPositions.insertVertex(successor.copy());
          reachedPositions.insertEdge(current, insertedNew);
          unFinished.add(insertedNew);
        }
      }
      System.out.println("Size of airshipg.graph: " + reachedPositions.numVertices());
      System.out.println("Number of unchecked vertices: " + unFinished.size());
    }
    System.out.println("!unFinished.isEmpty(): " + !unFinished.isEmpty());
    System.out.println("!current.getElement().canAirshipEscape(): " + !current.getElement().canAirshipEscape());
    System.out.println("Escape: " + current);
    Map<Vertex<Board>, Edge<Board>> forest = new ProbeHashMap<>();
    reachedPositions.BFS(inserted, new HashSet<>(), forest);
    PositionalList<Edge<Board>> bSolution = reachedPositions.constructPath(inserted, current, forest);
    List<Board> gSolution = new ArrayList<>();
    for (Edge<Board> edge : bSolution) {
      gSolution.add(edge.getEndpoints().get(0).getElement());
    }
    gSolution.add(current.getElement());
    solution = gSolution;
    System.out.println(solution);
    System.out.println(solution.size());

    toFirst();

    inf.setVisible(false);
    next.setVisible(true);
//    KeyCombination right = new KeyCodeCombination(KeyCode.RIGHT);
//    Mnemonic mr = new Mnemonic(next, right);
//    Scene scene = next.getScene();
//    scene.addMnemonic(mr);
    prev.setVisible(true);
//    KeyCombination left = new KeyCodeCombination(KeyCode.D);
//    Mnemonic ml = new Mnemonic(prev, left);
//    scene.addMnemonic(ml);
    last.setVisible(true);
    first.setVisible(true);
    gPane.setVisible(true);
  }

  public void toFirst() {
    index = 0;
    updateDisplay();
  }

  public void toLast() {
    index = solution.size() - 1;
    updateDisplay();
  }

  public void toNext() {
    index = Math.min(index + 1, solution.size() - 1);
    updateDisplay();
  }

  public void toPrev() {
    index = Math.max(index - 1, 0);
    updateDisplay();
  }

  private static boolean areTheyConnected(Vertex<Board> v1, Vertex<Board> v2) {
    for (Edge<Board> edge : v1.getEdges()) {
      if (reachedPositions.opposite(v1, edge).equals(v2)) {
        return true;
      }
    }
    return false;
  }

  private void updateDisplay() {
    Board current = solution.get(index);
    l00.setText(current.getTile(0, 0).toString());
    l10.setText(current.getTile(1, 0).toString());
    l20.setText(current.getTile(2, 0).toString());
    l01.setText(current.getTile(0, 1).toString());
    l11.setText(current.getTile(1, 1).toString());
    l21.setText(current.getTile(2, 1).toString());
    l02.setText(current.getTile(0, 2).toString());
    l12.setText(current.getTile(1, 2).toString());
    l22.setText(current.getTile(2, 2).toString());
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    next.setFocusTraversable(false);
    prev.setFocusTraversable(false);
    first.setFocusTraversable(false);
    last.setFocusTraversable(false);
  }

  public void setBackground(Background background) {
    gPane.setBackground(background);
  }
}
