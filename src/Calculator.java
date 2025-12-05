import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import javax.swing.*;

public class Calculator {
    int lebar = 600;
    int tinggi = 560;

    Color customAbuCerah = new Color(212, 212, 210);
    Color customAbuGelap = new Color(80,80,80);
    Color customHitam = new Color(28, 28, 28);
    Color customOrange = new Color(255, 149, 0);

    String[] tombolStrings = {
        "AC", "+/-", "%", "÷", 
        "7", "8", "9", "×", 
        "4", "5", "6", "-",
        "1", "2", "3", "+",
        "0", ".", "√", "="
    };
    String[] simbolKanan = {"÷", "×", "-", "+", "="};
    String[] simbolAtas = {"AC", "+/-", "%"};

    JFrame frame= new JFrame("Calculator");
    JLabel tampilanLabel = new JLabel();
    JPanel tampilanPanel = new JPanel();
    JPanel tombolPanel = new JPanel();

    String A = "0";
    String operator = null;
    String B = null;

    DefaultListModel<String> historyModel = new DefaultListModel<>();
    JList<String> historyList = new JList<>(historyModel);

    public Calculator() {
        frame.setVisible(true);
        frame.setSize(lebar, tinggi);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        frame.setLayout(new BorderLayout());

        JPanel historyPanel = new JPanel(new BorderLayout());
        historyPanel.setBackground(customHitam);

        historyPanel.setPreferredSize(new Dimension(170, 0)); 
        historyList.setBackground(customHitam);
        historyList.setForeground(Color.white);
        historyList.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        historyList.setFocusable(false);

        JScrollPane scrollHistory = new JScrollPane(historyList);
        scrollHistory.setPreferredSize(new Dimension(170, 0)); // lebar panel history

        JLabel historyLabel = new JLabel("  History");
        historyLabel.setForeground(Color.white);
        historyLabel.setBackground(customHitam);
        historyLabel.setOpaque(true);
        historyPanel.add(historyLabel, BorderLayout.NORTH);
        historyPanel.add(scrollHistory, BorderLayout.CENTER);

        frame.add(historyPanel, BorderLayout.WEST);


        tampilanLabel.setBackground(customHitam);
        tampilanLabel.setForeground(Color.white);
        tampilanLabel.setFont(new Font("Times New Roman", Font.PLAIN, 80));
        tampilanLabel.setHorizontalAlignment(JLabel.RIGHT);
        tampilanLabel.setText("0");
        tampilanLabel.setOpaque(true);

        tampilanPanel.setLayout(new BorderLayout());
        tampilanPanel.add(tampilanLabel);
        frame.add(tampilanPanel, BorderLayout.NORTH);

        tombolPanel.setLayout(new GridLayout(5, 4, 8, 8));
        tombolPanel.setBackground(customHitam);
        tombolPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        frame.add(tombolPanel);

        for (int i = 0; i < tombolStrings.length; i++) {
            String tombolString = tombolStrings[i];
            RoundedButton tombol = new RoundedButton(tombolString);
            tombol.setFocusable(false);

            if (Arrays.asList(simbolAtas).contains(tombolString)) {
                tombol.setBackground(customAbuCerah);
                tombol.setForeground(customHitam);
            }
            else if (Arrays.asList(simbolKanan).contains(tombolString)) {
                tombol.setBackground(customOrange);
                tombol.setForeground(Color.white); 
            }
    else {
        tombol.setBackground(customAbuGelap);
        tombol.setForeground(Color.white);
    }

    tombolPanel.add(tombol);


            tombol.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    JButton tombol = (JButton) e.getSource();
                    String tombolStrings = tombol.getText();
                    if (Arrays.asList(simbolKanan).contains(tombolStrings)) {
                        if (tombolStrings == "=") {
                            if (A != null) {
                                B = tampilanLabel.getText();
                                double nomorA = Double.parseDouble(A);
                                double nomorB = Double.parseDouble(B);
                                if (operator == "+") {
                                    tampilanLabel.setText(hapus0Desimal(nomorA + nomorB));
                                }
                                else if (operator == "-") {
                                    tampilanLabel.setText(hapus0Desimal(nomorA - nomorB));
                                }
                                else if (operator == "×") {
                                    tampilanLabel.setText(hapus0Desimal(nomorA * nomorB));
                                }
                                else if (operator == "÷") {
                                    tampilanLabel.setText(hapus0Desimal(nomorA / nomorB));
                                }

                                String ekspresi = A + " " + operator + " " + B + " = " + tampilanLabel.getText();
                                addHistory(ekspresi);

                                clearAll();
                            }
                        }
                        else if ("+-×÷".contains(tombolStrings)) {
                            if (operator == null) {
                                A = tampilanLabel.getText();
                                tampilanLabel.setText("0");
                                B = "0";
                            }
                            operator = tombolStrings;
                        }

                    }
                    else if (Arrays.asList(simbolAtas).contains(tombolStrings)) {
                        if (tombolStrings == "AC") {
                            clearAll();
                            tampilanLabel.setText("0");
                        }
                        else if (tombolStrings == "+/-") {
                            double tampilanNomor = Double.parseDouble(tampilanLabel.getText());
                            tampilanNomor *= -1;
                            tampilanLabel.setText(hapus0Desimal(tampilanNomor));
                        }
                        else if (tombolStrings == "%") {
                            double tampilanNomor = Double.parseDouble(tampilanLabel.getText());
                            tampilanNomor /= 100;
                            tampilanLabel.setText(hapus0Desimal(tampilanNomor));
                        }

                    }
                    else {
                        if (tombolStrings == "√") {
                            try {
                                double nilai = Double.parseDouble(tampilanLabel.getText());

                                if (nilai < 0) {
                                    tampilanLabel.setText("Error");   // bebas mau apa
                                } 
                                else {
                                    double hasil = Math.sqrt(nilai);
                                    tampilanLabel.setText(hapus0Desimal(hasil));
                                    String ekspresi = "√(" + hapus0Desimal(nilai) + ") = " + hapus0Desimal(hasil);
                                    addHistory(ekspresi);
                                }
                            }
                            catch (NumberFormatException ex) {

                            }
                        }
                        else if (tombolStrings == ".") {
                            if (!tampilanLabel.getText().contains(tombolStrings)) {
                                tampilanLabel.setText(tampilanLabel.getText() + tombolStrings);
                            }

                        }
                        else if ("0123456789".contains(tombolStrings)) {
                            if (tampilanLabel.getText() == "0") {
                                tampilanLabel.setText(tombolStrings);
                            }
                            else {
                                tampilanLabel.setText(tampilanLabel.getText() + tombolStrings);
                            }
                        }
                    }
                }
            });
        }
        frame.setVisible(true);
    }

    void clearAll() {
        A = "0";
        operator = null;
        B = null;
    }

    String hapus0Desimal (double tampilanNomor) {
        if (tampilanNomor % 1 == 0) {
            return Integer.toString((int) tampilanNomor);
        }
        return Double.toString(tampilanNomor);
    }

    void addHistory(String text) {
    historyModel.addElement(text);
    historyList.ensureIndexIsVisible(historyModel.getSize() - 1);
}

}
