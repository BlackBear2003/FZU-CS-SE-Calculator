package host.luke.calculator;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class CalculatorTest {

  private Calculator calculator;

  @Before
  public void setUp() {
    calculator = new Calculator();
  }

  @Test
  public void testAddition() {
    calculator.getTextField().setText("2+3");

    calculator.getEvalBtn().doClick();
    assertEquals("5", calculator.getLastResult());
  }

  @Test
  public void testSubtraction() {
    calculator.getTextField().setText("5-3");
    calculator.getEvalBtn().doClick();
    assertEquals("2", calculator.getLastResult());
  }

  @Test
  public void testMultiplication() {
    calculator.getTextField().setText("4*5");
    calculator.getEvalBtn().doClick();
    assertEquals("20", calculator.getLastResult());
  }

  @Test
  public void testDivision() {
    calculator.getTextField().setText("10/2");
    calculator.getEvalBtn().doClick();
    assertEquals("5", calculator.getLastResult());
  }

  @Test
  public void testFactorial() {
    calculator.getTextField().setText("5!");
    calculator.getEvalBtn().doClick();
    assertEquals("120", calculator.getLastResult());
  }

  @Test
  public void testPow1() {
    calculator.getTextField().setText("2 ^ 4");
    calculator.getEvalBtn().doClick();
    assertEquals("16", calculator.getLastResult());
  }

  @Test
  public void testPow2() {
    calculator.getTextField().setText("(-2) ^ 4");
    calculator.getEvalBtn().doClick();
    assertEquals("16", calculator.getLastResult());
  }

  @Test
  public void testPow3() {
    calculator.getTextField().setText("(-2) ^ 3");
    calculator.getEvalBtn().doClick();
    assertEquals("-8", calculator.getLastResult());
  }

  @Test
  public void testPow4() {
    calculator.getTextField().setText("- 2 ^ 4");
    calculator.getEvalBtn().doClick();
    assertEquals("-16", calculator.getLastResult());
  }

  @Test
  public void testPow5() {
    calculator.getTextField().setText("4 ^ (1.0 / 2)");
    calculator.getEvalBtn().doClick();
    assertEquals("2.0", calculator.getLastResult());
  }


  @Test
  public void testSwitchSinAndDgr() {
    setUp();
    assertEquals("dgr", calculator.getSwitchRadDgrBtn().getText());
    calculator.getSwitchRadDgrBtn().doClick();
    assertEquals("rad", calculator.getSwitchRadDgrBtn().getText());
    calculator.getSwitchRadDgrBtn().doClick();
    assertEquals("dgr", calculator.getSwitchRadDgrBtn().getText());
  }

  @Test
  public void testSinOfDgr() {
    setUp();
    calculator.getTextField().setText("sin(30)");
    calculator.getEvalBtn().doClick();
    assertEquals("0.5", calculator.getLastResult());
  }

  @Test
  public void testSinOfRad() {
    setUp();
    calculator.getTextField().setText("sin(30)");
    calculator.getSwitchRadDgrBtn().doClick();
    calculator.getEvalBtn().doClick();
    assertEquals("-0.988031624093", calculator.getLastResult());
  }

  @Test
  public void testCosOfDgr() {
    setUp();
    calculator.getTextField().setText("cos(60)");
    calculator.getEvalBtn().doClick();
    assertEquals("0.5", calculator.getLastResult());
  }

  @Test
  public void testCosOfRad() {
    setUp();
    calculator.getTextField().setText("cos(60)");
    calculator.getSwitchRadDgrBtn().doClick();
    calculator.getEvalBtn().doClick();
    assertEquals("-0.952412980415", calculator.getLastResult());
  }

  @Test
  public void testException() {
    calculator.getTextField().setText("5 / 0");
    calculator.getEvalBtn().doClick();

    assertEquals("ZeroDivisionError", calculator.getLastResult());
  }

  @Test
  public void testAllClean() {
    calculator.getTextField().setText("dirty data");
    calculator.getClearBtn().doClick();
    assertEquals("", calculator.getLastResult());
  }

  @Test
  public void testUsingLastResult() {
    calculator.getTextField().setText("1 + 1");
    calculator.getEvalBtn().doClick();
    calculator.getEvalBtn().doClick();
    assertEquals("2", calculator.getLastResult());
  }

}
