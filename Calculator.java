import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Calculator extends JFrame implements ActionListener {

    private JTextField display;
    private StringBuilder currentInput;
    private double result;
    private String lastOperator;
    private boolean isOperatorPressed;

    public Calculator() {
        setTitle("Beautiful Calculator");
        setSize(400, 550);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Initialize calculator components
        currentInput = new StringBuilder();
        result = 0;
        lastOperator = "";
        isOperatorPressed = false;

        // Create and customize display
        display = new JTextField();
        display.setFont(new Font("Arial", Font.BOLD, 40));
        display.setEditable(false);
        display.setBackground(new Color(240, 240, 240));
        display.setHorizontalAlignment(JTextField.RIGHT);
        display.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(display, BorderLayout.NORTH);

        // Create and customize button panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(5, 4, 10, 10));
        buttonPanel.setBackground(new Color(60, 63, 65));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Button labels for the calculator
        String[] buttons = {
                "C", "/", "*", "Del",
                "7", "8", "9", "-",
                "4", "5", "6", "+",
                "1", "2", "3", "=",
                "0", ".", "+/-", "CE"
        };

        for (String text : buttons) {
            JButton button = new JButton(text);
            button.setFont(new Font("Arial", Font.BOLD, 30));
            button.setForeground(Color.WHITE);
            button.setBackground(new Color(50, 50, 50));
            button.setFocusable(false);
            button.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
            button.addActionListener(this);
            buttonPanel.add(button);
        }

        add(buttonPanel, BorderLayout.CENTER);

        // Set frame visibility and location
        setVisible(true);
        setLocationRelativeTo(null);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        if (command.matches("\\d") || command.equals(".")) {
            if (isOperatorPressed) {
                currentInput.setLength(0);
                isOperatorPressed = false;
            }
            currentInput.append(command);
            display.setText(currentInput.toString());
        } else if (command.equals("C")) {
            clear();
        } else if (command.equals("CE")) {
            currentInput.setLength(0);
            display.setText("");
        } else if (command.equals("Del")) {
            if (currentInput.length() > 0) {
                currentInput.deleteCharAt(currentInput.length() - 1);
                display.setText(currentInput.toString());
            }
        } else if (command.equals("+/-")) {
            toggleSign();
        } else if (command.equals("=")) {
            calculate(lastOperator);
            display.setText(String.valueOf(result));
            currentInput.setLength(0);
            currentInput.append(result);
        } else {
            calculate(command);
            lastOperator = command;
            isOperatorPressed = true;
        }
    }

    private void calculate(String operator) {
        double currentNumber = currentInput.length() > 0 ? Double.parseDouble(currentInput.toString()) : result;

        switch (lastOperator) {
            case "+" -> result += currentNumber;
            case "-" -> result -= currentNumber;
            case "*" -> result *= currentNumber;
            case "/" -> result /= currentNumber;
            default -> result = currentNumber;
        }

        currentInput.setLength(0);
        display.setText(String.valueOf(result));
    }

    private void clear() {
        currentInput.setLength(0);
        result = 0;
        lastOperator = "";
        display.setText("");
    }

    private void toggleSign() {
        if (currentInput.length() > 0) {
            double number = Double.parseDouble(currentInput.toString());
            number *= -1;
            currentInput.setLength(0);
            currentInput.append(number);
            display.setText(currentInput.toString());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Calculator::new);
    }
}
