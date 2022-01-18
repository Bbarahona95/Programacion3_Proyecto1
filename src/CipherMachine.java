import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Locale;

public class CipherMachine {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Dcode");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1100, 700);
        frame.setResizable(false);

        JPanel panelSelect = new JPanel();
        panelSelect.setLayout(new GridLayout(1, 2));

        JLabel l1 = new JLabel("         Introduzca el texto que desea cifrar");
        JLabel l2 = new JLabel("         Texto cifrado");
        panelSelect.add(l1);
        panelSelect.add(l2);

        JPanel panelTextArea = new JPanel();
        panelTextArea.setLayout(new GridLayout(1, 2));
        panelTextArea.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        JTextArea txtA1 = new JTextArea();
        txtA1.setLineWrap(true);
        txtA1.setWrapStyleWord(true);
        txtA1.setBackground(Color.cyan);
        JTextArea txtA2 = new JTextArea();
        txtA2.setLineWrap(true);
        txtA2.setWrapStyleWord(true);
        txtA2.setBackground(Color.PINK);

        panelTextArea.add(txtA1);
        panelTextArea.add(txtA2);

        JPanel panelOpciones = new JPanel();
        JRadioButton buttonVigenere = new JRadioButton("Cipher Vigenere");
        JLabel l3 = new JLabel("Key");
        JTextField txtKey = new JTextField("Key", 4);
        buttonVigenere.setSelected(true);
        JRadioButton buttonCesar = new JRadioButton("Cipher Cesar");

        JLabel l4 = new JLabel("Shift");
        JTextField txtShift = new JTextField("1", 2);
        buttonVigenere.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(buttonVigenere.isSelected()){
                    txtKey.setEditable(true);
                    txtShift.setEditable(false);
                }
            }
        });
        buttonCesar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(buttonCesar.isSelected()){
                    txtShift.setEditable(true);
                    txtKey.setEditable(false);
                }
            }
        });
        System.out.println();
        JButton btnEncrypt = new JButton("Encrypt");
        JButton btnDecrypt = new JButton("Decrypt");
        btnEncrypt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (buttonCesar.isSelected()) {
                    txtA2.setText(CaesarCipher(txtA1.getText(), Integer.parseInt(txtShift.getText()), "Encode"));
                } else {
                    txtA2.setText(VigenereCipher(txtA1.getText(), "Encode", txtKey.getText()));
                }

            }
        });
        btnDecrypt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (buttonCesar.isSelected()) {
                    txtA2.setText(CaesarCipher(txtA1.getText(), Integer.parseInt(txtShift.getText()), "Decode"));
                } else {
                    txtA2.setText(VigenereCipher(txtA1.getText(), "Decode", txtKey.getText()));
                }


            }
        });

        ButtonGroup bg = new ButtonGroup();
        bg.add(buttonCesar);
        bg.add(buttonVigenere);

        panelOpciones.setLayout(new FlowLayout());

        panelOpciones.add(buttonVigenere);
        panelOpciones.add(l3);
        panelOpciones.add(txtKey);
        panelOpciones.add(buttonCesar);
        panelOpciones.add(l4);
        panelOpciones.add(txtShift);
        panelOpciones.add(btnEncrypt);
        panelOpciones.add(btnDecrypt);

        frame.getContentPane().add(BorderLayout.NORTH, panelSelect);
        frame.getContentPane().add(BorderLayout.CENTER, panelTextArea);
        frame.getContentPane().add(BorderLayout.SOUTH, panelOpciones);

        frame.setVisible(true);

    }

    public static String CaesarCipher(String text, int shift, String type) {
        StringBuilder textValue = new StringBuilder();
        String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        text = text.toUpperCase();

        //String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnñopqrstuvwxyz";
        for (int i = 0; i < text.length(); ++i) {
            if (text.charAt(i) != ' ') {
                for (int j = 0; j < alphabet.length(); ++j) {
                    if (text.charAt(i) == alphabet.charAt(j)) {
                        if (type.equals("Encode")) {
                            if (alphabet.length() - j <= shift) {
                                textValue.append(alphabet.charAt(j - alphabet.length() + shift));
                            } else {
                                textValue.append(alphabet.charAt(j + shift));
                            }
                        } else if (type.equals("Decode")) {
                            if (j < shift) {
                                textValue.append(alphabet.charAt(j + (alphabet.length() - shift)));
                            } else {
                                textValue.append(alphabet.charAt(j - shift));
                            }
                        }
                    }
                }
            } else {
                textValue.append(" ");
            }
        }

        return textValue.toString();
    }

    public static String VigenereCipher(String text, String type, String key) {
        StringBuilder textValue = new StringBuilder();
        key = key.toUpperCase(Locale.ROOT);
        text = text.toUpperCase(Locale.ROOT);
        String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"; // ABCDEFGHIJKLMNÑOPQRSTUVWXYZ ABCDEFGHIJKLMNOPQRSTUVWXYZ

        int cont = 0;
        int coord = 0;

        for (int i = 0; i < text.length(); i++) {
            if (text.charAt(i) != ' ') {
                if (type.equals("Encode")) {
                    coord = ((alphabet.indexOf(text.charAt(i)) + alphabet.indexOf(key.charAt(cont))) % alphabet.length());
                } else if (type.equals("Decode")) {
                    coord = ((alphabet.indexOf(text.charAt(i)) - alphabet.indexOf(key.charAt(cont))) % alphabet.length());
                    if (coord < 0) {
                        coord += 26;
                    }
                }
                textValue.append(alphabet.charAt(coord));
                cont++;
                if (cont == key.length()) {
                    cont = 0;
                }
            } else {
                textValue.append(" ");
            }
        }
        return textValue.toString();
    }
}