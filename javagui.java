import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.plaf.basic.BasicScrollBarUI;

public class javagui {
    private static DecimalFormat df = new DecimalFormat("#.##");
    // Disability pension
    private static int numOfJobs;
    private static int age;
    private static int lvl;
    private static int staj = 0;
    private static double pensionReslt;

    // variables for old age pension
    private static int Tl = 34;
    private static int Tmin = 15;
    private static int k;
    private static float Pmin = 2777.86F;
    private static float[] years;
    private static float[] months;
    private static float[] salary;
    private static int choice;
    private static float Ta_years = 0;
    private static float Ta_months = 0;
    private static JTextField g1;
    private static JTextField g2;

    // variables for 1999
    private static int numberOfJobs;
    private static int[] years_n;
    private static int[] months_n;
    private static int[] Cpr_1;
    

    static {
        System.loadLibrary("native");
    }

    private native double pension(int n, int year, int staj, int k, double sal[]);
    private native double calculatePension(int Tl, int Tmin, float Pmin, int k, float years[], float months[], float salary[], int choice, float Ta_years, float Ta_months);
    private native double cp(int n, int years_n[], int months_n[], int Cpr_1[]);

    public static void main(String[] args) {

        JFrame frame = new JFrame("Pension calculator");
        frame.getContentPane().setBackground(Color.decode("#6D87A1"));

        // Creating all panels
        JPanel outPanel = new JPanel();

        // Change the preferred size of the outPanel
        outPanel.setPreferredSize(new Dimension(700, 100)); // Set the preferred size to be 700 width and 100 height


        outPanel.setLayout(new GridBagLayout());
        outPanel.setBackground(Color.decode("#6D87A1"));


        // Creating a label for output
        JLabel outLabel = new JLabel("<html><div style='text-align: center;'>Welcome!</div></html>");
        // Change font and size
        outLabel.setFont(new Font("Arial", Font.BOLD, 30));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER; // Set anchor to center
        outPanel.add(outLabel, gbc);

        // Create a main panel and add subpanels to it
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        //mainPanel.setBackground(Color.decode("#263543"));

        // Create a panel for menu components (disability pension)
        JPanel menuComponentsPanel = new JPanel();
        menuComponentsPanel.setLayout(new BoxLayout(menuComponentsPanel, BoxLayout.Y_AXIS));
        menuComponentsPanel.setBackground(Color.decode("#263543"));

        //Create a penel for age pension
        JPanel oldAgePensionPanel = new JPanel();
        oldAgePensionPanel.setLayout(new BoxLayout(oldAgePensionPanel, BoxLayout.Y_AXIS));
        oldAgePensionPanel.setBackground(Color.decode("#263543"));

        JPanel selPanel2 = new JPanel();
        selPanel2.setLayout(new GridLayout(7, 1));
        selPanel2.setBackground(Color.decode("#263543"));

        // Create a panel for 1999 pensions
        JPanel ussrJPanel = new JPanel();
        ussrJPanel.setLayout(new BoxLayout(ussrJPanel, BoxLayout.Y_AXIS));
        ussrJPanel.setBackground(Color.decode("#263543"));


        // Here is building the menu for age pension

        // Creating all necessary panels
        JPanel selPanel = new JPanel();
        selPanel.setLayout(new GridLayout(7, 1));
        selPanel.setBackground(Color.decode("#263543"));

        JPanel maleFemPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        maleFemPanel.setBackground(Color.decode("#263543"));

        JPanel jobSetJPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        jobSetJPanel.setBackground(Color.decode("#263543"));

        JPanel jobsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        jobsPanel.setLayout(new BoxLayout(jobsPanel, BoxLayout.Y_AXIS));
        jobsPanel.setBackground(Color.decode("#263543"));

        JScrollPane scrollField = new JScrollPane(jobsPanel);
        scrollField.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollField.setBorder(null);
        scrollField.setBackground(Color.decode("#263543"));
        scrollField.getVerticalScrollBar().setBackground(Color.decode("#263543"));
        scrollField.getVerticalScrollBar().setUI(new BasicScrollBarUI() {
            @Override
            protected void configureScrollBarColors() {
                this.thumbColor = Color.decode("#263543");
            }
        });

        JPanel calcButton = new JPanel(new FlowLayout(FlowLayout.CENTER));
        calcButton.setBackground(Color.decode("#263543"));

        JPanel overworkPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        overworkPanel.setBackground(Color.decode("#263543"));


        // Creating the radio buttons:
        JLabel retLabel = new JLabel("Have you worked after retirement age?");
        retLabel.setForeground(Color.decode("#B9A57C"));       
        JRadioButton maleButton = new JRadioButton("Yes");
        maleButton.setForeground(Color.decode("#B9A57C")); 
        JRadioButton femaleButton = new JRadioButton("No");
        femaleButton.setForeground(Color.decode("#B9A57C")); 

        ButtonGroup group = new ButtonGroup();
        group.add(maleButton);
        group.add(femaleButton);

        
        // Adding radio buttons to the panel
        maleFemPanel.add(retLabel);
        maleFemPanel.add(maleButton);
        maleFemPanel.add(femaleButton);

        // Creating textfield for number of jobs and set button
        JLabel jobsNumber = new JLabel("Enter Number of employments: ");
        jobsNumber.setForeground(Color.decode("#B9A57C")); 
        JTextField jobsTextField = new JTextField(7);
        jobsTextField.setBackground(Color.decode("#6D87A1"));
        JButton setButton = new JButton("set");
        setButton.setBackground(Color.decode("#B9A57C"));
        

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
                k = number;

                for (int i = 0; i < number; i++) {
                    JPanel fPanel = new JPanel();
                    fPanel.setBackground(Color.decode("#263543"));
                    fPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
                    
                     
                    JLabel lbl = new JLabel("Years in job " + (i + 1) + ": ");
                    lbl.setForeground(Color.decode("#B9A57C")); 
                    JTextField tF = new JTextField(5);
                    tF.setBackground(Color.decode("#6D87A1"));
                    tF.setPreferredSize(new Dimension(150, 20));
                    JLabel lbl2 = new JLabel("months: ");
                    lbl2.setForeground(Color.decode("#B9A57C")); 
                    JTextField tF2 = new JTextField(5);
                    tF2.setBackground(Color.decode("#6D87A1")); 
                    JLabel lbl3 = new JLabel("salary: ");
                    lbl3.setForeground(Color.decode("#B9A57C")); 
                    JTextField tF3 = new JTextField(5);
                    tF3.setBackground(Color.decode("#6D87A1"));


                    fPanel.add(lbl);
                    fPanel.add(tF);
                    fPanel.add(lbl2);
                    fPanel.add(tF2);
                    fPanel.add(lbl3);
                    fPanel.add(tF3);
                    jobsPanel.add(fPanel);
                }
                // Refresh the panel
                jobsPanel.revalidate();
                jobsPanel.repaint();
                frame.revalidate();
                frame.repaint();
            }
        });

        maleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                overworkPanel.removeAll();
                JLabel gg = new JLabel("Input how many years and months have you worked after retirement age: ");
                gg.setForeground(Color.decode("#B9A57C")); 
                g1 = new JTextField(5);
                g1.setBackground(Color.decode("#6D87A1"));
                g2 = new JTextField(5);
                g2.setBackground(Color.decode("#6D87A1"));

                choice = 1;

                overworkPanel.add(gg);
                overworkPanel.add(g1);
                overworkPanel.add(g2);

                overworkPanel.revalidate();
                overworkPanel.repaint();
                frame.revalidate();
                frame.repaint();

            }
        });

        femaleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                choice = 0;
                overworkPanel.removeAll();
                overworkPanel.revalidate();
                overworkPanel.repaint();
                frame.revalidate();
                frame.repaint();
            }
        });

        // Calculate button
        JButton cButton = new JButton("calculate");
        calcButton.add(cButton);

        cButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (choice == 1) {
                    String g1Text = g1.getText();
                    String g2Text = g2.getText();

                    if (!g1Text.isEmpty() && !g2Text.isEmpty()) {
                        Ta_years = Float.parseFloat(g1Text);
                        Ta_months = Float.parseFloat(g2Text);
                    } else {
                        JOptionPane.showMessageDialog(frame, "Please fill in both years and months with valid numbers", "Error", JOptionPane.ERROR_MESSAGE);
                        return; 
                    }
                }

                years = new float[k];
                months = new float[k];
                salary = new float[k];

                Component[] components = jobsPanel.getComponents();
                for (int i = 0; i < k; i++) {
                    JPanel jobJPanel = (JPanel) components[i];
                    JTextField yearsField = (JTextField) jobJPanel.getComponent(1);
                    JTextField monthsField = (JTextField) jobJPanel.getComponent(3);
                    JTextField salaryField = (JTextField) jobJPanel.getComponent(5);

                    String yString = yearsField.getText();
                    String mString = monthsField.getText();
                    String sString = salaryField.getText();
                    if (!yString.isEmpty() && !mString.isEmpty() && !sString.isEmpty()) {
                        years[i] = Float.parseFloat(yString);
                        months[i] = Float.parseFloat(mString);
                        salary[i] = Float.parseFloat(sString);
                    }
                    else {
                        JOptionPane.showMessageDialog(frame, "Please fill in all fields with valid numbers", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    
                }
                System.out.println("Years: " + Arrays.toString(years));
                System.out.println("Months: " + Arrays.toString(months));
                System.out.println("Salary: " + Arrays.toString(salary));
                System.out.println("Choice: " + choice);
                System.out.println("Ta_years: " + Ta_years);
                System.out.println("Ta_months: " + Ta_months);
                System.out.println("k: " + k);

                pensionReslt = new javagui().calculatePension(Tl, Tmin, Pmin, k, years, months, salary, choice, Ta_years, Ta_months);
                String formattedNumber = df.format(pensionReslt);
                outLabel.setText("<html><div style='text-align: center;'>" + formattedNumber + "</div></html>");

                System.out.println("Your pension will be: " + pensionReslt);
            }
        });


        // Grouping all panels in one
        oldAgePensionPanel.add(selPanel);
        oldAgePensionPanel.add(jobSetJPanel);
        oldAgePensionPanel.add(scrollField);
        oldAgePensionPanel.add(maleFemPanel);
        oldAgePensionPanel.add(overworkPanel);
        oldAgePensionPanel.add(calcButton);
        


        // End of building the menu for age pension


        // Here is building the menu for disability pension

        // Create a label and text field for entering the number
        JLabel numberLabel = new JLabel("Enter Number of employments:");
        numberLabel.setForeground(Color.decode("#B9A57C"));
        JTextField numberTextField = new JTextField(5);
        numberTextField.setBackground(Color.decode("#6D87A1"));

        // Create a panel for number input
        JPanel numberPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        numberPanel.add(numberLabel);
        numberPanel.add(numberTextField);
        numberPanel.setBackground(Color.decode("#263543"));

        // Create a panel to hold dynamically added text fields
        JPanel textFieldPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        textFieldPanel.setLayout(new BoxLayout(textFieldPanel, BoxLayout.Y_AXIS));
        textFieldPanel.setBackground(Color.decode("#263543"));

        // Create a scroll pane and add the text field panel to it
        JScrollPane scrollPane = new JScrollPane(textFieldPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setBorder(null);
        scrollPane.setBackground(Color.decode("#263543"));
        scrollPane.getVerticalScrollBar().setBackground(Color.decode("#263543"));
        scrollPane.getVerticalScrollBar().setUI(new BasicScrollBarUI() {
            @Override
            protected void configureScrollBarColors() {
                this.thumbColor = Color.decode("#263543");
            }
        });

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
                    fieldPanel.setBackground(Color.decode("#263543"));
                    fieldPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

                    JLabel label = new JLabel("Job " + (i + 1) + ": ");
                    label.setForeground(Color.decode("#B9A57C"));
                    JTextField textField = new JTextField(5);
                    textField.setBackground(Color.decode("#6D87A1"));
                    textField.setPreferredSize(new Dimension(150, 20));

                    JLabel label2 = new JLabel("Salary: ");
                    label2.setForeground(Color.decode("#B9A57C"));
                    JTextField textField2 = new JTextField(5);
                    textField2.setBackground(Color.decode("#6D87A1"));

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
        agePanel.setBackground(Color.decode("#263543"));
        JLabel ageLabel = new JLabel("Your age: ");
        ageLabel.setForeground(Color.decode("#B9A57C"));
        JTextField ageTextField = new JTextField(5);
        ageTextField.setBackground(Color.decode("#6D87A1"));
        agePanel.add(ageLabel);
        agePanel.add(ageTextField);

        // Create a panel to store the level of disability
        JPanel disPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        disPanel.setBackground(Color.decode("#263543"));
        JLabel disLabel = new JLabel("Your level of disability: ");
        disLabel.setForeground(Color.decode("#B9A57C"));
        JTextField disTextField = new JTextField(5);
        disTextField.setBackground(Color.decode("#6D87A1"));
        disPanel.add(disLabel);
        disPanel.add(disTextField);

        JPanel butPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        butPanel.setBackground(Color.decode("#263543"));
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
                String formattedNumber = df.format(pensionReslt);
                outLabel.setText("<html><div style='text-align: center;'>" + formattedNumber + "</div></html>");

                System.out.println("Your pension will be: " + pensionReslt);
            }
        });

        // Add components to the menu components panel
        menuComponentsPanel.add(selPanel2);
        menuComponentsPanel.add(numberPanel);
        menuComponentsPanel.add(scrollPane);
        menuComponentsPanel.add(agePanel);
        menuComponentsPanel.add(disPanel);
        menuComponentsPanel.add(butPanel);

        // End of creation of disability menu



        // 1999 Pensions
        //Panels declaration
        JPanel optionsPanel = new JPanel();
        optionsPanel.setLayout(new GridLayout(7, 1));
        optionsPanel.setBackground(Color.decode("#263543"));

        JPanel nJobsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        nJobsPanel.setBackground(Color.decode("#263543"));

        JPanel jobsPanel2 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        jobsPanel2.setLayout(new BoxLayout(jobsPanel2, BoxLayout.Y_AXIS));
        jobsPanel2.setBackground(Color.decode("#263543"));

        JScrollPane scrollField2 = new JScrollPane(jobsPanel2);
        scrollField2.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollField2.setBorder(null);
        scrollField2.setBackground(Color.decode("#263543"));
        scrollField2.getVerticalScrollBar().setBackground(Color.decode("#263543"));
        scrollField2.getVerticalScrollBar().setUI(new BasicScrollBarUI() {
            @Override
            protected void configureScrollBarColors() {
                this.thumbColor = Color.decode("#263543");
            }
        });

        JPanel lbutton = new JPanel(new FlowLayout(FlowLayout.CENTER));
        lbutton.setBackground(Color.decode("#263543"));

        

        JLabel pLevels = new JLabel("Professional levels:");
        JLabel firstButton = new JLabel("1. For agricultural workers, handymen (I, II qualification category) and unskilled support staff");
        JLabel secondButton = new JLabel("2. For workers of average qualification (III, IV qualification category)");
        JLabel thirdButton = new JLabel("3. For highly qualified workers (V, VI, VII, VIII qualification category) and specialists with secondary specialized education");
        JLabel fourthButton = new JLabel("4. For specialists with higher education");
        JLabel fifthButton = new JLabel("5. For managers at the level of a structural unit");
        JLabel sixthButton = new JLabel("6. For heads of enterprises and their deputies");

        pLevels.setForeground(Color.decode("#B9A57C"));
        firstButton.setForeground(Color.decode("#B9A57C"));
        secondButton.setForeground(Color.decode("#B9A57C"));
        thirdButton.setForeground(Color.decode("#B9A57C"));
        fourthButton.setForeground(Color.decode("#B9A57C"));
        fifthButton.setForeground(Color.decode("#B9A57C"));
        sixthButton.setForeground(Color.decode("#B9A57C"));

        optionsPanel.add(pLevels);
        optionsPanel.add(firstButton);
        optionsPanel.add(secondButton);
        optionsPanel.add(thirdButton);
        optionsPanel.add(fourthButton);
        optionsPanel.add(fifthButton);
        optionsPanel.add(sixthButton);


        JLabel nEmpsLabel = new JLabel("Enter Number of employments: ");
        nEmpsLabel.setForeground(Color.decode("#B9A57C"));
        JTextField nTextField = new JTextField(5);
        nTextField.setBackground(Color.decode("#6D87A1"));
        JButton nButton = new JButton("set");

        nJobsPanel.add(nEmpsLabel);
        nJobsPanel.add(nTextField);
        nJobsPanel.add(nButton);

        nButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jobsPanel2.removeAll();
        
                String numOfFields = nTextField.getText();
                int number = 0;
                if (!numOfFields.isEmpty()) number = Integer.parseInt(numOfFields);
                numberOfJobs = number;
        
                for (int i = 0; i < number; i++) {
                    JPanel fPanel2 = new JPanel();
                    fPanel2.setBackground(Color.decode("#263543"));
                    fPanel2.setLayout(new FlowLayout(FlowLayout.LEFT));
        
                    JLabel lbl2 = new JLabel("Years in job:" + (i + 1) + ": ");
                    lbl2.setForeground(Color.decode("#B9A57C"));
                    JTextField tF2 = new JTextField(7);
                    tF2.setBackground(Color.decode("#6D87A1"));
                    JLabel mmonths = new JLabel("months:");
                    mmonths.setForeground(Color.decode("#B9A57C"));
                    JTextField ttF = new JTextField(7);
                    ttF.setBackground(Color.decode("#6D87A1"));
                    JLabel lbl22 = new JLabel("Prof. lvl: ");
                    lbl22.setForeground(Color.decode("#B9A57C"));
                    JComboBox<Integer> tF22 = new JComboBox<>(new Integer[]{1, 2, 3, 4, 5, 6});
        
                    fPanel2.add(lbl2);
                    fPanel2.add(tF2);
                    fPanel2.add(mmonths);
                    fPanel2.add(ttF);
                    fPanel2.add(lbl22);
                    fPanel2.add(tF22);
                    jobsPanel2.add(fPanel2);
                }
                // Refresh the panel
                jobsPanel2.revalidate();
                jobsPanel2.repaint();
                frame.revalidate();
                frame.repaint();
            }
        });
        
        JButton calBut = new JButton("calculate");
        lbutton.add(calBut);

        calBut.addActionListener(new ActionListener() {
            @SuppressWarnings("unchecked")
            @Override
            public void actionPerformed(ActionEvent e) {
                // Initialize arrays to store data
                years_n = new int[numberOfJobs];
                months_n = new int[numberOfJobs];
                Cpr_1 = new int[numberOfJobs];
        
                // Loop through each panel
                for (int i = 0; i < numberOfJobs; i++) {
                    // Get the panel at index i from jobsPanel2
                    JPanel panel = (JPanel) jobsPanel2.getComponent(i);
        
                    // Get the text field for years from the panel
                    JTextField yearsTextField = (JTextField) panel.getComponent(1);
                    // Parse the text and store in years_n array
                    years_n[i] = Integer.parseInt(yearsTextField.getText());
        
                    // Get the text field for months from the panel
                    JTextField monthsTextField = (JTextField) panel.getComponent(3);
                    // Parse the text and store in months_n array
                    months_n[i] = Integer.parseInt(monthsTextField.getText());
        
                    // Get the combo box for professional level from the panel
                    JComboBox<Integer> professionalLevelComboBox = (JComboBox<Integer>) panel.getComponent(5);
                    // Get the selected item and store in Cpr_1 array
                    Cpr_1[i] = (int) professionalLevelComboBox.getSelectedItem();
                }
                System.out.println("Number of Jobs: " + numberOfJobs);
                System.out.println("Years: " + Arrays.toString(years_n));
                System.out.println("Months: " + Arrays.toString(months_n));
                System.out.println("Qualification: " + Arrays.toString(Cpr_1));
                
                pensionReslt = new javagui().cp(numberOfJobs, years_n, months_n, Cpr_1);
                String formattedNumber = df.format(pensionReslt);
                outLabel.setText("<html><div style='text-align: center;'>" + formattedNumber + "</div></html>");

                System.out.println("Your pension will be: " + pensionReslt);

            }
        });
        


        //panels grouping
        ussrJPanel.add(optionsPanel);
        ussrJPanel.add(nJobsPanel);
        ussrJPanel.add(scrollField2);
        ussrJPanel.add(lbutton);
        //End of this section




        // Create a tabbed pane with two tabs
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Golden Years", oldAgePensionPanel); // First tab with an empty panel
        tabbedPane.addTab("Till 1999", ussrJPanel);
        tabbedPane.addTab("Ability Aid", menuComponentsPanel); // Third tab with the menu panel

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
