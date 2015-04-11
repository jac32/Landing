import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;


public class Display extends JPanel implements ActionListener {

        private JFileChooser fileChooser;
        private JButton openButton;
        private BufferedReader br;
        private File file;
        int returnVal;
        String currentLine;

        public Display(){
            fileChooser = new JFileChooser();
            openButton = new JButton("Select");

            setPreferredSize(new Dimension(278, 179));
            setLayout(null);

            add(openButton);

            openButton.setBounds(84, 145, 100, 25);
            openButton.addActionListener(this);
        }

        public void actionPerformed(ActionEvent e){
            if(e.getSource() == openButton){
                returnVal = fileChooser.showOpenDialog(null);
                if(returnVal == JFileChooser.APPROVE_OPTION){
                    file = fileChooser.getSelectedFile();

                    //Read the file!
                    try{
                        br = new BufferedReader(new FileReader(file));

                        while((currentLine = br.readLine()) != null){
                            System.out.println(currentLine);
                        }
                    } catch (Exception error){
                        error.printStackTrace();
                    }

                }
            }
        }
}


