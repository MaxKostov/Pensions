import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;

public class javagui {
    private static int numOfJobs;
    private static int  age;
    private static int lvl;
    private static int staj = 0;
    private static double pensionReslt;

    static {
        System.loadLibrary("native");
    }

    private native double pension(int n, int year, int staj, int k, double sal[]);

    public static void main(String[] args) {

        JFrame frame = new JFrame("Pension calculator");

        //  Creating all panels
        JPanel outPanel = new JPanel();

        // Change the preferred size of the outPanel
        outPanel.setPreferredSize(new Dimension(700, 100)); // Set the preferred size to be 700 width and 100 height

        // Change the layout manager of outPanel to FlowLayout with center alignment
        outPanel.setLayout(new GridBagLayout()); 

        
        JPanel menuPanel = new JPanel();
        menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.Y_AXIS));

        // Creating a label for output
        JLabel outLabel = new JLabel("<html><div style='text-align: center;'>Welcome!</div></html>");
        // Change font and size
        outLabel.setFont(new Font("Arial", Font.BOLD, 24));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER; // Set anchor to center
        outPanel.add(outLabel, gbc);

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
                numOfJobs = numFields;

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
                frame.revalidate();
                frame.repaint();
            }
        });

        // Create a panel to store the age
        JPanel agePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel ageLabel = new JLabel("Your age: ");
        JTextField ageTextField = new JTextField(10);
        agePanel.add(ageLabel);
        agePanel.add(ageTextField);

        // Create a panel to store the level of disability
        JPanel disPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel disLabel = new JLabel("Your level of disability: ");
        JTextField disTextField = new JTextField(10);
        disPanel.add(disLabel);
        disPanel.add(disTextField);

        JPanel butPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton button2 = new JButton("calculate");
        butPanel.add(button2);

        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Create an ArrayList to store the text from validated text fields
                ArrayList<String> textFieldValues = new ArrayList<>();

                // Iterate over components of textFieldPanel
                Component[] components = textFieldPanel.getComponents();
                for (Component component : components) {
                    if (component instanceof JPanel) {
                        // For each JPanel (which contains JLabel and JTextField), get the JTextField component
                        Component[] subComponents = ((JPanel) component).getComponents();
                        //String jobName = null;
                        //double salary = 0.0;
        
                        for (Component subComponent : subComponents) {
                            if (subComponent instanceof JTextField) {
                                String text = ((JTextField) subComponent).getText();
                                if (text.matches("^\\d*\\.?\\d+$")) { // Regex to check for positive integer or double
                                    textFieldValues.add(text);
                                } else {
                                    // Raise an error message if input is invalid
                                    JOptionPane.showMessageDialog(frame, "Invalid input: Please enter positive numbers", "Error", JOptionPane.ERROR_MESSAGE);
                                    return; // Exit the method
                                }
                            }
                        }
                    }
                }
        
                // Convert the ArrayList to an array if needed
                String[] textFieldValuesArray = textFieldValues.toArray(new String[textFieldValues.size()]);

                // Declare an array to store the products
                double[] medSal = new double[textFieldValuesArray.length / 2];

                // Initialize an index for the medSal array
                int k = 0;

                // Calculate the product of consecutive pairs of numbers
                for (int i = 0; i < textFieldValuesArray.length - 1; i += 2) {
                    double num1 = Double.parseDouble(textFieldValuesArray[i]);
                    double num2 = Double.parseDouble(textFieldValuesArray[i + 1]);
                    medSal[k] = num1 * num2;
                    staj += num1;
                    k++; // Increment the index
                }


                String ageEntered = ageTextField.getText();
                if (!ageEntered.isEmpty()) age = Integer.parseInt(ageEntered);

                String lvlEntered = disTextField.getText();
                if (!lvlEntered.isEmpty()) lvl = Integer.parseInt(lvlEntered);
        
                // Display the array for testing
                System.out.println("Number of jobs: " + numOfJobs);
                System.out.println(Arrays.toString(medSal));
                System.out.println("Age: " + age);
                System.out.println("Lvl of disability: " + lvl);
                pensionReslt = new javagui().pension(numOfJobs, age, staj, lvl, medSal);
                outLabel.setText("<html><div style='text-align: center;'>" + pensionReslt + "</div></html>");

                System.out.println("Your pension will be: " + pensionReslt);
            }
        });
        
        
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
        mainPanel.add(menuPanel, BorderLayout.CENTER);

        // Add main panel to the frame
        frame.getContentPane().add(mainPanel);

        // Pack the frame to adjust its size according to the content
        frame.setSize(650, 500);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
