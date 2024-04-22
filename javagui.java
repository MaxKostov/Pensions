import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;

public class javagui {
    private static int numOfJobs;
    private static int age;
    private static int lvl;
    private static int staj = 0;
    private static double pensionReslt;

    static {
        System.loadLibrary("native");
    }

    private native double pension(int n, int year, int staj, int k, double sal[]);

    public static void main(String[] args) {

        JFrame frame = new JFrame("Pension calculator");

        // Creating all panels
        JPanel outPanel = new JPanel();

        // Change the preferred size of the outPanel
        outPanel.setPreferredSize(new Dimension(700, 100)); // Set the preferred size to be 700 width and 100 height


        outPanel.setLayout(new GridBagLayout());


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

        // Create a main panel and add subpanels to it
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        // Create a panel for menu components (disability pension)
        JPanel menuComponentsPanel = new JPanel();
        menuComponentsPanel.setLayout(new BoxLayout(menuComponentsPanel, BoxLayout.Y_AXIS));

        //Create a penel for age pension
        JPanel oldAgePensionPanel = new JPanel();
        oldAgePensionPanel.setLayout(new BoxLayout(oldAgePensionPanel, BoxLayout.Y_AXIS));


        // Here is building the menu for age pension

        // Creating all necessary panels
        JPanel selPanel = new JPanel();
        selPanel.setLayout(new GridLayout(7, 1));

        JPanel maleFemPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        JPanel jobSetJPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        JPanel jobsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        jobsPanel.setLayout(new BoxLayout(jobsPanel, BoxLayout.Y_AXIS));

        JScrollPane scrollField = new JScrollPane(jobsPanel);
        scrollField.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        JPanel calcButton = new JPanel(new FlowLayout(FlowLayout.CENTER));


        // Creating the radio buttons:
        JLabel pLevels = new JLabel("Professional levels:");
        JLabel firstButton = new JLabel("1. For agricultural workers, handymen (I, II qualification category) and unskilled support staff");
        JLabel secondButton = new JLabel("2. For workers of average qualification (III, IV qualification category)");
        JLabel thirdButton = new JLabel("3. For highly qualified workers (V, VI, VII, VIII qualification category) and specialists with secondary specialized education");
        JLabel fourthButton = new JLabel("4. For specialists with higher education");
        JLabel fifthButton = new JLabel("5. For managers at the level of a structural unit");
        JLabel sixthButton = new JLabel("6. For heads of enterprises and their deputies");
        
        JRadioButton maleButton = new JRadioButton("Male");
        JRadioButton femaleButton = new JRadioButton("Female");

        ButtonGroup group = new ButtonGroup();
        group.add(maleButton);
        group.add(femaleButton);
        
        // Adding radio buttons to the panel
        selPanel.add(pLevels);
        selPanel.add(firstButton);
        selPanel.add(secondButton);
        selPanel.add(thirdButton);
        selPanel.add(fourthButton);
        selPanel.add(fifthButton);
        selPanel.add(sixthButton);

        maleFemPanel.add(maleButton);
        maleFemPanel.add(femaleButton);

        // Creating textfield for number of jobs and set button
        JLabel jobsNumber = new JLabel("Enter Number of employments: ");
        JTextField jobsTextField = new JTextField(10);
        JButton setButton = new JButton("set");

        jobSetJPanel.add(jobsNumber);
        jobSetJPanel.add(jobsTextField);
        jobSetJPanel.add(setButton);

        setButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jobsPanel.removeAll();

                String numOfFields = jobsTextField.getText();
                int number = 0;
                if (!numOfFields.isEmpty()) number = Integer.parseInt(numOfFields);
                // place for numOfJobs

                for (int i = 0; i < number; i++) {
                    JPanel fPanel = new JPanel();
                    fPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
                    
                     
                    JLabel lbl = new JLabel("Job " + (i + 1) + ": ");
                    JTextField tF = new JTextField(20);
                    tF.setPreferredSize(new Dimension(150, 20));
                    JLabel lbl2 = new JLabel("Professional level: ");
                    /* 
                    JTextField tF2 = new JTextField(20);
                    */
                    JComboBox<Integer> tF2 = new JComboBox<>(new Integer[]{1, 2, 3, 4, 5, 6});



                    fPanel.add(lbl);
                    fPanel.add(tF);
                    fPanel.add(lbl2);
                    fPanel.add(tF2);
                    jobsPanel.add(fPanel);
                }
                // Refresh the panel
                jobsPanel.revalidate();
                jobsPanel.repaint();
                frame.revalidate();
                frame.repaint();
            }
        });

        // Calculate button
        JButton cButton = new JButton("calculate");
        calcButton.add(cButton);


        // Grouping all panels in one
        oldAgePensionPanel.add(selPanel);
        oldAgePensionPanel.add(maleFemPanel);
        oldAgePensionPanel.add(jobSetJPanel);
        oldAgePensionPanel.add(scrollField);
        oldAgePensionPanel.add(calcButton);
        


        // End of building the menu for age pension


        // Here is building the menu for disability pension

        // Create a label and text field for entering the number
        JLabel numberLabel = new JLabel("Enter Number of employments:");
        JTextField numberTextField = new JTextField(10);

        JPanel maleFemPanel2 = new JPanel(new FlowLayout(FlowLayout.LEFT));

        JRadioButton maleButton2 = new JRadioButton("Male");
        JRadioButton femaleButton2 = new JRadioButton("Female");

        ButtonGroup group2 = new ButtonGroup();
        group2.add(maleButton2);
        group2.add(femaleButton2);
        
        maleFemPanel2.add(maleButton2);
        maleFemPanel2.add(femaleButton2);

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

        // Add the 'set' button to the menu components panel
        numberPanel.add(button);
        menuComponentsPanel.add(numberPanel);
        menuComponentsPanel.add(scrollPane);



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
                staj = 0;
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
                pensionReslt = new javagui().pension(lvl, age, staj, numOfJobs, medSal);
                outLabel.setText("<html><div style='text-align: center;'>" + pensionReslt + "</div></html>");

                System.out.println("Your pension will be: " + pensionReslt);
            }
        });

        // Add components to the menu components panel
        menuComponentsPanel.add(maleFemPanel2);
        menuComponentsPanel.add(numberPanel);
        menuComponentsPanel.add(scrollPane);
        menuComponentsPanel.add(agePanel);
        menuComponentsPanel.add(disPanel);
        menuComponentsPanel.add(butPanel);

        // End of creation of disability menu




        // Create a tabbed pane with two tabs
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("old age pension", oldAgePensionPanel); // Second tab with an empty panel
        tabbedPane.addTab("Disability Pension", menuComponentsPanel); // First tab with the menu panel

        mainPanel.add(outPanel, BorderLayout.NORTH);
        mainPanel.add(tabbedPane, BorderLayout.CENTER);

        // Add main panel to the frame
        frame.getContentPane().add(mainPanel);

        // Pack the frame to adjust its size according to the content
        frame.setSize(900, 650);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
