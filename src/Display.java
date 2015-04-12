import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Display extends JPanel implements ActionListener {
private ImagePanel imagePanel;
        private JFileChooser fileChooser;
        private JButton openButton;
        private JButton twitterButton;
        private BufferedReader br;
        private File file;
        int returnVal;
        String currentLine;

        public Display(){
            imagePanel = new ImagePanel();
            fileChooser = new JFileChooser();
            openButton = new JButton("Select");
            twitterButton = new JButton("Query");

            setPreferredSize(new Dimension(800, 600));
            setLayout(null);


            add(imagePanel);
            add(openButton);
            add(twitterButton);
//            imagePanel.setBounds(0,0,200,200);
            openButton.setBounds(144, 545, 100, 25);
            openButton.addActionListener(this);
            twitterButton.setBounds(34, 545, 100, 25);
            twitterButton.addActionListener(this);
        }

        public void actionPerformed(ActionEvent e){
            if(e.getSource() == openButton){
                returnVal = fileChooser.showOpenDialog(null);
                if(returnVal == JFileChooser.APPROVE_OPTION) {
                    file = fileChooser.getSelectedFile();

                    //Read the file!
                    try {
                        System.out.println(file.getName());
                        ImageProcessor.convertToImage(file.getAbsolutePath());
                    } catch (UnsupportedAudioFileException f) {
                        ImageProcessor.convertToSound(file.getAbsolutePath());
                    }catch (Exception error){
                        error.printStackTrace();
                    }

                }
            }
        }
}


