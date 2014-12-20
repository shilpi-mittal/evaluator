import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNull;

public class evaluatorTest {
  evaluator e;

  @Before
  public void initialiseClass() {
    e = new evaluator();
  }

  @Test
  public void testWhenInputBlank() {
    assert(e.evaluateExpression("").equals(""));
  }

  @Test
  public void testWhenOnlyParenthesis() {
    assert(e.evaluateExpression("()").equals(""));
  }

  @Test
  public void testWhenUnaryOperators() {
    assert(e.evaluateExpression("-8-9+2").equals("-15.0"));
    assert(e.evaluateExpression("-8-(+9)+2").equals("-15.0"));
    assert(e.evaluateExpression("-8-(-9)+2").equals("3.0"));
    assert(e.evaluateExpression("-8*(-9)+2").equals("74.0"));
  }

  @Test
  public void testWhenFloatInput() {
    assert(e.evaluateExpression("2.555*2+7/3").equals("7.44"));
    assert(e.evaluateExpression("52.1/4*2").equals("26.06"));
  }

  @Test
  public void testWhenNoParenthesis() {
    assert(e.evaluateExpression("5+9").equals("14.0"));
  }

  @Test
  public void testWhenComplexInput() {
    assert(e.evaluateExpression("5*(9+1)*2").equals("100.0"));
  }

  @Test
  public void testWhenDivideByZeroScenario() {
    assertNull(e.evaluateExpression("9/(7-3-4)"));
  }

  @Test
  public void testWhenInvalidInput() {
    assertNull(e.evaluateExpression("foo+9,"));
    assertNull(e.evaluateExpression("'*9"));
    assertNull(e.evaluateExpression("(89("));
    assertNull(e.evaluateExpression("(7)(9)"));
    assertNull(e.evaluateExpression("7(9)"));
    assertNull(e.evaluateExpression("++9"));
    assertNull(e.evaluateExpression("9++"));
    assertNull(e.evaluateExpression("9+"));
    assertNull(e.evaluateExpression("-8+-9+2"));
  }
}
