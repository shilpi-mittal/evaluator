public class evaluator {

  String numbers = "0123456789.";
  String operators = "/*+-";

  public String evaluateExpression(String input) {
    input = "(" + input + ")";
    int startIndex, lastIndex;
    String result, inputToEvaluate;

    if (verifyInput(input)) {
      while (input.contains("(")) {
        startIndex = input.lastIndexOf("(");
        lastIndex = startIndex + input.substring(startIndex).indexOf(")");

        if (startIndex > lastIndex || (startIndex > 0 && (numbers+")").contains(getStringAt(input, startIndex - 1)))) {
          System.out.println("Invalid input");
          return null;
        }

        inputToEvaluate = input.substring(startIndex + 1, lastIndex);
        inputToEvaluate = removeTwoAdjacentOperators(inputToEvaluate);
        result = evaluateWithoutParentheses(inputToEvaluate);

        if (result != null)
          input = input.substring(0, startIndex) + result + input.substring(lastIndex + 1, input.length());
        else
          return null;
      }
      return input;
    }
    else {
      System.out.println("Invalid input");
      return null;
    }
  }

  private boolean verifyInput(String in) {
    String validInputs = numbers + operators + "()";
    String ch;
    for (int i = 0; i < in.length(); i++) {
      ch = getStringAt(in, i);
      if (!validInputs.contains(ch)|| (i>0 && operators.contains(ch) && operators.contains(getStringAt(in, i-1))))
        return false;
    }
    return true;
  }

  private String removeTwoAdjacentOperators(String in) {
    String char1, char2;
    for (int i = 1; i < in.length(); i++) {
      char2 = getStringAt(in, i);
      char1 = getStringAt(in, i-1);
      if (operators.contains(char1) && operators.contains(char2))
        if(char1.equals("-") && char2.equals("-"))
          return in.substring(0,i-1)+"+"+in.substring(i+1,in.length());
        else if(char1.equals("+") && char2.equals("-"))
          return in.substring(0,i-1)+"-"+in.substring(i+1,in.length());
    }
    return in;
  }

  private String evaluateWithoutParentheses(String input) {
    int operatorIndex;

    for (char operator : operators.toCharArray()) {
      for (int i = 0; i < input.length(); i++) {
        if (input.charAt(i) == operator) {
          operatorIndex = i;
          input = evaluateSingleOperatorExpression(operator, input, operatorIndex);
          if(input == null)
            return null;
        }
      }
    }
    return input;
  }

  private String evaluateSingleOperatorExpression(char operator, String input, int operatorIndex) {
    float operand1, operand2;
    float ans = 0;
    int index1, index2;
    String result;

    index1 = getIndexOfFirstOperand(input.substring(0, operatorIndex));
    if (operatorIndex - index1 == 0) {
      if(operator=='-'||operator=='+')
        operand1=0;
      else {
        System.out.println("Invalid input");
        return null;
      }
    }
    else {
      operand1 = Float.parseFloat(input.substring(index1, operatorIndex));
      if (index1 > 0 && input.charAt(index1 - 1) == '-') {
        operand1 = -operand1;
        index1--;
      } else if (index1 > 0 && input.charAt(operatorIndex - 1) == '+')
        index1--;
    }
    if (operatorIndex+1<input.length() && (input.charAt(operatorIndex + 1) == '-' || input.charAt(operatorIndex + 1) == '+')) {
      index2 = getIndexOfSecondOperand(input.substring(operatorIndex + 2, input.length()));
      if (index2 == 0) {
        System.out.println("Invalid input");
        return null;
      }
      index2++;
      operand2 = Float.parseFloat(input.substring(operatorIndex + 2, operatorIndex + index2 + 1));
      if (input.charAt(operatorIndex + 1) == '-')
        operand2 = -operand2;
    }
    else {
      index2 = getIndexOfSecondOperand(input.substring(operatorIndex + 1, input.length()));
      if (index2 == 0) {
        System.out.println("Invalid input");
        return null;
      }
      operand2 = Float.parseFloat(input.substring(operatorIndex + 1, operatorIndex + index2 + 1));
    }

    if (operator == '/') {
      if (operand2 == 0) {
        System.out.println("Invalid input");
        return null;
      }
      ans = operand1 / operand2;
    } else if (operator == '*') {
      ans = operand1 * operand2;
    } else if (operator == '+') {
      ans = operand1 + operand2;
    } else if (operator == '-') {
      ans = operand1 - operand2;
    }
    ans = (Math.round(ans*100));
    result = String.valueOf(ans/100);

    return input.substring(0, index1) + result + input.substring(operatorIndex + index2 + 1, input.length());
  }

  private int getIndexOfFirstOperand(String input) {
    int index = -1;
    for (int i = input.length() - 1; i >= 0; i--) {
      if (!numbers.contains(getStringAt(input, i))) {
        index = i;
        break;
      }
    }
    return index + 1;
  }

  private int getIndexOfSecondOperand(String input) {
    int index = input.length();
    for (int i = 0; i < input.length(); i++) {
      if (!numbers.contains(getStringAt(input, i))) {
        index = i;
        break;
      }
    }
    return index;
  }

  private String getStringAt(String string, int index) {
    return string.substring(index, index + 1);
  }

  public static void main(String[] args) {
    evaluator e = new evaluator();
    System.out.println(e.evaluateExpression(args[0]));
  }
}