package host.luke;

import host.luke.calculator.Calculator;
import javax.swing.SwingUtilities;

public class Main {

  public static void main(String[] args) {
    SwingUtilities.invokeLater(Calculator::new);
  }
}