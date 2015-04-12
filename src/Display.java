import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;


public class Display extends JPanel implements ActionListener {

        private ImagePanel imagePanel;
        private JFileChooser fileChooser;
        private JButton openButton;
        private BufferedReader br;
        private File file;
        int returnVal;
        String currentLine;

        public Display(){
            imagePanel = new ImagePanel();
            fileChooser = new JFileChooser();
            openButton = new JButton("Select");

            setPreferredSize(new Dimension(278, 179));
            setLayout(null);


            add(imagePanel);
            add(openButton);
//            imagePanel.setBounds(0,0,200,200);
            openButton.setBounds(84, 145, 100, 25);
            openButton.addActionListener(this);
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


