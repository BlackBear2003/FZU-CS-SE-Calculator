package host.luke.calculator;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import org.python.core.PyException;
import org.python.util.PythonInterpreter;

public class Calculator {

  private final PythonInterpreter interpreter;
  private JFrame frame;
  private JTextField textField;
  private String lastResult = "";
  private boolean isRad = false;
  private JButton evalBtn;
  private JButton clearBtn;
  private JButton switchRadDgrBtn;

  public Calculator() {

    initFrame();

    System.setProperty("python.import.site", "false");
    interpreter = new PythonInterpreter();
    interpreter.exec("import math");
  }

  void initFrame() {
    this.frame = new JFrame("计算器");
    this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.frame.setSize(400, 400);
    this.frame.setLayout(new BorderLayout());

    this.textField = new JTextField();
    this.textField.setHorizontalAlignment(JTextField.RIGHT);
    this.textField.setFont(new Font("Arial", Font.PLAIN, 24));
    this.frame.add(this.textField, BorderLayout.NORTH);

    JPanel buttonPanel = new JPanel();
    buttonPanel.setLayout(new GridLayout(5, 5));

    String[] buttonLabels = {"AC", "(", ")", "/", "sin", "7", "8", "9", "*", "cos", "4", "5", "6",
        "+", "tan", "1", "2", "3", "-", "!", "0", ".", "^", "=", "dgr"};

    for (String label : buttonLabels) {
      JButton button = new JButton(label);
      button.setFont(new Font("Arial", Font.PLAIN, 18));
      button.addActionListener(new ButtonClickListener());
      buttonPanel.add(button);
      if (label.equals("=")) {
        this.evalBtn = button;
      }
      if (label.equals("AC")) {
        this.clearBtn = button;
      }
      if (label.equals("dgr")) {
        this.switchRadDgrBtn = button;
      }
    }

    this.frame.add(buttonPanel, BorderLayout.CENTER);
    this.frame.setVisible(true);
  }

  String toEvaluableFactorialExpression(String expression) {
    // 定义正则表达式模式
    String pattern = "(\\d+)!";

    // 创建Pattern对象
    Pattern regex = Pattern.compile(pattern);

    // 创建Matcher对象
    Matcher matcher = regex.matcher(expression);

    // 使用正则表达式进行替换
    StringBuffer output = new StringBuffer();
    while (matcher.find()) {
      int num = Integer.parseInt(matcher.group(1));
      String replacement = "math.factorial(" + num + ")";
      matcher.appendReplacement(output, replacement);
    }
    matcher.appendTail(output);

    // 打印转换后的表达式
    return output.toString();
  }

  public JTextField getTextField() {
    return this.textField;
  }

  public String getLastResult() {
    return this.lastResult;
  }
  public JButton getEvalBtn() {
    return evalBtn;
  }

  public JButton getClearBtn() {
    return this.clearBtn;
  }

  public JButton getSwitchRadDgrBtn() {
    return switchRadDgrBtn;
  }

  public class ButtonClickListener implements ActionListener {

    public void actionPerformed(ActionEvent e) {
      String command = e.getActionCommand();
      Object source = e.getSource();

      if (command.equals("=")) {
        String expression = textField.getText();
        // 在用户输入的表达式中添加 "Math."
        expression = expression.replaceAll("\\^", " ** ");
        if (isRad) {
          expression = expression.replaceAll("(sin|cos|tan)\\(", "math.$1(");
        } else {
          expression = expression.replaceAll("(sin|cos|tan)\\(", "math.$1( math.pi / 180.0 *");
        }
        expression = toEvaluableFactorialExpression(expression);

        System.out.println(expression);
        Object result = null;
        try {
          result = interpreter.eval(expression);
        } catch (PyException ex) {
          result = ex.getMessage().split(":")[0];
        }
        lastResult = result.toString();
        if (lastResult.endsWith("L")) {
          lastResult = lastResult.substring(0, lastResult.length()-1);
        }

        textField.setText(lastResult);
      } else if (command.equals("AC")) {
        textField.setText("");
        lastResult = "";
      } else if (command.equals("rad") || command.equals("dgr")) {
        isRad = !isRad;
        JButton clickedButton = (JButton) source;
        clickedButton.setText(isRad ? "rad" : "dgr");
      } else {
        if (!lastResult.isEmpty()) {
          textField.setText(lastResult + command);
          lastResult = "";
        } else {
          textField.setText(textField.getText() + command);
        }
      }
    }
  }
}
