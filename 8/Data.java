import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

class Data {
  private String directions;
  private HashMap<String, Node> lookup;

  Data(String filePath) throws IOException {
    lookup = new HashMap<>();

    try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
      String line;
      int lineNum = 0;

      while ((line = reader.readLine()) != null) {
        if (lineNum == 0) {
          directions = line;
        }
        if (lineNum > 1) {
          String[] parts = line.split(" ");
          String name = parts[0];
          String leftName = parts[2].substring(1, parts[2].length() - 1);
          String rightName = parts[3].substring(0, parts[3].length() - 1);

          Node left = lookup.get(leftName);
          if (left == null) {
            left = new Node(leftName);
            lookup.put(leftName, left);
          }
          Node right = lookup.get(rightName);
          if (right == null) {
            right = new Node(rightName);
            lookup.put(rightName, right);
          }

          Node n = lookup.get(name);
          if (n == null) {
            n = new Node(name);
            lookup.put(name, n);
          }

          n.setLeft(left);
          n.setRight(right);
        }

        lineNum++;
      }
    } catch (IOException e) {
      throw e;
    }
  }

  public void solve1() throws Exception {
    int steps = 0;
    Node n = lookup.get("AAA");

    while (!n.getName().equals("ZZZ")) {
      char c = directions.charAt(steps % directions.length());

      switch (c) {
        case 'L':
          n = n.getLeft();
          break;
        case 'R':
          n = n.getRight();
          break;
        default:
          throw new Exception();
      }

      steps++;
    }

    System.out.println(steps);
  }
}
