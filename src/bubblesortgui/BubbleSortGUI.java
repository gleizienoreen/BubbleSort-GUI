/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package bubblesortgui;

/**
 *
 * @author admin
 */
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class BubbleSortGUI extends JFrame {
    private JTextField inputField;
    private JRadioButton ascendingButton;
    private JRadioButton descendingButton;
    private JButton sortButton;
    private JTextArea outputArea;
    private JLabel passesLabel;

    public BubbleSortGUI() {
        setTitle("Bubble Sort");
        setSize(800, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(240, 240, 240));

        JLabel titleLabel = new JLabel("Bubble Sort", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setForeground(new Color(50, 100, 150));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        add(titleLabel, BorderLayout.NORTH);

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(5, 1, 5, 5));
        inputPanel.setBackground(new Color(240, 240, 240));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        inputField = new JTextField(15);
        inputPanel.add(new JLabel("Enter numbers (with comma):"));
        inputPanel.add(inputField);

        inputPanel.add(new JLabel("Choose which order:"));

        JPanel radioPanel = new JPanel();
        ascendingButton = new JRadioButton("Ascending");
        descendingButton = new JRadioButton("Descending");
        ascendingButton.setSelected(true); 
        ButtonGroup group = new ButtonGroup();
        group.add(ascendingButton);
        group.add(descendingButton);
        radioPanel.add(ascendingButton);
        radioPanel.add(descendingButton);
        radioPanel.setBackground(new Color(240, 240, 240));
        inputPanel.add(radioPanel);

        sortButton = new JButton("Sort");
        sortButton.setBackground(new Color(100, 150, 250));
        sortButton.setForeground(Color.WHITE);
        sortButton.setFocusPainted(false);
        inputPanel.add(sortButton);

        outputArea = new JTextArea(10, 50);
        outputArea.setEditable(false);
        outputArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        outputArea.setBackground(Color.WHITE);
        outputArea.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));

        passesLabel = new JLabel("Passes: 0");
        passesLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        passesLabel.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));

        add(inputPanel, BorderLayout.CENTER);
        add(new JScrollPane(outputArea), BorderLayout.EAST);
        add(passesLabel, BorderLayout.SOUTH);

        sortButton.addActionListener(new SortButtonListener());
    }

    private class SortButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String input = inputField.getText();
            String[] numbers = input.split(",");
            int[] array = new int[numbers.length];

            try {
                for (int i = 0; i < numbers.length; i++) {
                    array[i] = Integer.parseInt(numbers[i].trim());
                }
            } catch (NumberFormatException ex) {
                outputArea.setText("Please enter valid integers.");
                return;
            }

            boolean ascending = ascendingButton.isSelected();

            outputArea.setText("Input array: " + arrayToString(array) + "\n\n");

            new Thread(() -> bubbleSort(array, ascending)).start();
        }
    }

    private void bubbleSort(int[] array, boolean ascending) {
        int n = array.length;
        int passes = 0;

        for (int i = 0; i < n - 1; i++) {
            passes++;
            boolean swapped = false;

            for (int j = 0; j < n - 1 - i; j++) {
                if ((ascending && array[j] > array[j + 1]) ||
                    (!ascending && array[j] < array[j + 1])) {

                    int temp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = temp;
                    swapped = true;
           
                outputArea.append(formatArray(array) + "\n");
                }
            }
 
            if (!swapped) {
                break; 
            }
        }

        outputArea.append("\nSorted array: " + arrayToString(array) + "\n");
        passesLabel.setText("Passes: " + passes);
    }

    private String formatArray(int[] array) {
        StringBuilder sb = new StringBuilder();
        for (int num : array) {
            sb.append(" (").append(num).append(") ");
        }
        return sb.toString();
    }

    private String arrayToString(int[] array) {
        StringBuilder sb = new StringBuilder();
        for (int num : array) {
            sb.append(num).append(" ");
        }
        return sb.toString().trim();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            BubbleSortGUI frame = new BubbleSortGUI();
            frame.setVisible(true);
        });
    }
}
