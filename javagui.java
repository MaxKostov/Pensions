import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class javagui {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Pension calculator");

        //  Creating all panels
        JPanel outPanel = new JPanel();

        JPanel menuPanel = new JPanel();
        menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.Y_AXIS));
        JScrollPane scrollPaneMenu = new JScrollPane(menuPanel);
        scrollPaneMenu.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        // Creating a textfield for output
        JLabel outLabel = new JLabel("Here will be the output");
        outPanel.add(outLabel);

        // Create a label and text field for entering the number
        JLabel numberLabel = new JLabel("Enter Number of employments:");
        JTextField numberTextField = new JTextField(10);

        // Create a panel for number input
        JPanel numberPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        numberPanel.add(numberLabel);
        numberPanel.add(numberTextField);

        // Create a panel to hold dynamically added text fields
        JPanel textFieldPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        textFieldPanel.setLayout(new BoxLayout(textFieldPanel, BoxLayout.Y_AXIS));
    
        // Create a scroll pane and add the text field panel to it
        JScrollPane scrollPane = new JScrollPane(textFieldPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);


        JButton button = new JButton("set");
        // Add action listener to the number text field
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Clear existing text fields
                textFieldPanel.removeAll();

                // Get the number entered by the user
                String inputText = numberTextField.getText();
                int numFields = 0;
                if (!inputText.isEmpty()) numFields = Integer.parseInt(inputText);

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
                    textFieldPanel.add(fieldPanel);
                }

                // Refresh the panel
                textFieldPanel.revalidate();
                textFieldPanel.repaint();
            }
        });

        // Create a panel to store the age
        JPanel agePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel ageLabel = new JLabel("Your age: ");
        JTextField ageTextField = new JTextField(10);
        agePanel.add(ageLabel);
        agePanel.add(ageTextField);

        // Create a panel to store the lvl of disability
        JPanel disPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel disLabel = new JLabel("Your level of disability: ");
        JTextField disTextField = new JTextField(10);
        disPanel.add(disLabel);
        disPanel.add(disTextField);

        JPanel butPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton button2 = new JButton("calculate");
        butPanel.add(button2);

        // Create a main panel and add subpanels to it
        JPanel mainPanel = new JPanel();
        numberPanel.add(button);
        mainPanel.setLayout(new BorderLayout());

        menuPanel.add(numberPanel);
        menuPanel.add(scrollPane);
        menuPanel.add(agePanel);
        menuPanel.add(disPanel);
        menuPanel.add(butPanel);

        mainPanel.add(outPanel, BorderLayout.NORTH);
        mainPanel.add(scrollPaneMenu, BorderLayout.CENTER);
        
        // Add main panel to the frame
        frame.getContentPane().add(mainPanel);

        // Pack the frame to adjust its size according to the content
        frame.setSize(700, 400);;
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}