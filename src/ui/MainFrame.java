package ui;

import controller.Controller;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class MainFrame {

    public static void main(String[] args) {

        JFrame mainFrame = new JFrame("Huffman");

        mainFrame.setIconImage(new ImageIcon(
                "pictures/icon.ico").getImage());

        final JLabel originText = new JLabel("Choose file or folder:");
        originText.setBounds(50, 30, 150, 15);

        final JTextField origin = new JTextField();
        origin.setBounds(50,50, 300,20);

        JButton chooseOrigin = new JButton("Browse..");
        chooseOrigin.setBounds(50,80,95,30);
        chooseOrigin.addActionListener(e -> origin.setText(FileChooser
                .chooseFileWindow(false).getAbsolutePath()));

        final JLabel saveToText = new JLabel("Save to:");
        saveToText.setBounds(50, 130, 150, 15);

        final JTextField saveTo = new JTextField();
        saveTo.setBounds(50,150, 300,20);

        JButton chooseSaveTo = new JButton("Browse..");
        chooseSaveTo.setBounds(50,180,95,30);
        chooseSaveTo.addActionListener(e -> saveTo.setText(FileChooser
                .chooseFileWindow(true).getAbsolutePath()));

        JLabel result = new JLabel("");
        result.setBounds(50, 330, 150, 15);

        JButton run = new JButton("Compress/Decompress");
        run.setBounds(50,290,170,30);
        run.addActionListener(e -> {
            try {
                result.setText("Processing...");
                Controller.processFiles(origin.getText(), saveTo.getText());
                result.setText("Success!");
            } catch (Exception exception) {
                result.setText("Something's wrong!");
            }
        });

        class ImagePanel extends JComponent {
            private Image image;
            public ImagePanel(Image image) {
                this.image = image;
            }
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(image, 0, 0, this);
            }
        }
        try {
            mainFrame.setContentPane(new ImagePanel(ImageIO
                    .read(new File("pictures/back.png"))));
        } catch (IOException e) {
            e.printStackTrace();
        }

        mainFrame.add(originText);
        mainFrame.add(saveToText);
        mainFrame.add(chooseOrigin);
        mainFrame.add(chooseSaveTo);
        mainFrame.add(origin);
        mainFrame.add(saveTo);
        mainFrame.add(result);
        mainFrame.add(run);

        mainFrame.setSize(400,399);
        mainFrame.setLayout(null);
        mainFrame.setVisible(true);

        mainFrame.setResizable(false);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
