import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class test {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Pension calculator");

        // Create a label and text field for entering the number
        JLabel numberLabel = new JLabel("Enter Number of employments:");
        JTextField numberTextField = new JTextField(10);

        // Create a panel for number input
        JPanel numberPanel = new JPanel();
        numberPanel.setLayout(new BoxLayout(numberPanel, BoxLayout.Y_AXIS));
        numberPanel.add(numberLabel);
        numberPanel.add(numberTextField);

        // Create a scroll pane and add the text field panel to it
        JScrollPane scrollPane = new JScrollPane(numberPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        // Add action listener to the number text field
        numberTextField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Clear existing text fields
                numberPanel.removeAll();
                numberPanel.add(numberLabel);
                numberPanel.add(numberTextField);

                // Get the number entered by the user
                int numFields = Integer.parseInt(numberTextField.getText());

                // Create and add the required number of text fields
                for (int i = 0; i < numFields; i++) {
                    JPanel fieldPanel = new JPanel();
                    fieldPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

                    JLabel label = new JLabel("Job " + (i + 1) + ": ");
                    JTextField textField = new JTextField(20);
                    textField.setPreferredSize(new Dimension(150, 20));

                    JLabel label2 = new JLabel("Salary: ");
                    JTextField textField2 = new JTextField(20);

                    fieldPanel.add(label);
                    fieldPanel.add(textField);
                    fieldPanel.add(label2);
                    fieldPanel.add(textField2);
                    numberPanel.add(fieldPanel);
                }

                // Refresh the panel
                numberPanel.revalidate();
                numberPanel.repaint();
            }
        });

        // Create a main panel and add subpanels to it
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        //mainPanel.add(numberPanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // Add main panel to the frame
        frame.getContentPane().add(mainPanel);

        // Set frame properties
        frame.setSize(600, 400);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}