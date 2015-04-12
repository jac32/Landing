
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.sound.sampled.LineUnavailableException;
import twitter4j.MediaEntity;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;

public class Display extends JPanel implements ActionListener {

    private ImagePanel imagePanel;
    private JLabel logo;
    private JFileChooser fileChooser;
    private JButton openButton;
    private JButton twitterButton;
    private JComboBox twitterDropDown;
    private BufferedReader br;
    private File file;
    private JButton midiButton;
    private JButton waveButton;
    int returnVal;
    String currentLine;
    ArrayList<MediaEntity> images;

    public Display() throws IOException {
        try {
            images = getTweetURLs(autenticateTwitter());
        } catch (TwitterException ex) {
            System.out.println(ex.getMessage());
        }
        imagePanel = new ImagePanel();
        fileChooser = new JFileChooser();
        openButton = new JButton("Browse");
        twitterButton = new JButton("Twitter");
        midiButton = new JButton("MIDI");
        waveButton = new JButton("Convert");
        logo = new JLabel(new ImageIcon("soundstormLogo.png"));

        setPreferredSize(new Dimension(650, 300));
        setLayout(null);

        add(imagePanel);
        add(openButton);
        add(twitterButton);
        add(midiButton);
        add(waveButton);
        add(logo);
//            imagePanel.setBounds(0,0,200,200);
        openButton.setBounds(144, 245, 100, 25);
        openButton.addActionListener(this);
        twitterButton.setBounds(34, 245, 100, 25);
        twitterButton.addActionListener(this);
        
        midiButton.setBounds(254, 245, 100, 25);
        midiButton.addActionListener(this);
        waveButton.setBounds(364, 245, 100, 25);
        waveButton.addActionListener(this);
        
        logo.setBounds(10, 0, 600, 200);
    }

    public void actionPerformed(ActionEvent e) {
        try {
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            if (e.getSource() == openButton) {
                returnVal = fileChooser.showOpenDialog(null);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    file = fileChooser.getSelectedFile();
                    System.out.println(file.getName());
                }
            }
            if (e.getSource() == twitterButton) {
                URL url = null;
                try {
                    url = new URL(images.get(0).getMediaURLHttps());
                    System.out.println(url.toString());
                } catch (MalformedURLException ex) {
                    Logger.getLogger(Display.class.getName()).log(Level.SEVERE, null, ex);
                }
                BufferedImage img = null;
                try {
                    img = ImageIO.read(url);
                } catch (IOException ex) {
                    Logger.getLogger(Display.class.getName()).log(Level.SEVERE, null, ex);
                }
                file = new File("downloaded.png");
                try {
                    ImageIO.write(img, "png", file);
                } catch (IOException ex) {
                    Logger.getLogger(Display.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            if (e.getSource() == midiButton) {
                ImageProcessor.createMusicFromImage(file.getAbsolutePath());
            }
            if (e.getSource() == waveButton) {
                //Read the file!
                try {
                    System.out.println(file.getName());
                    ImageProcessor.convertToImage(file.getAbsolutePath());
                } catch (UnsupportedAudioFileException f) {
                    try {
                        ImageProcessor.convertToSound(file.getAbsolutePath());
                    } catch (UnsupportedAudioFileException ex) {
                        Logger.getLogger(Display.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (LineUnavailableException ex) {
                        Logger.getLogger(Display.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } catch (Exception error) {
                    error.printStackTrace();
                }
            }
        } finally {
            this.setCursor(Cursor.getDefaultCursor());
        }
    }

    public ArrayList<MediaEntity> getTweetURLs(Twitter twitter) throws IOException, TwitterException {
        ArrayList<MediaEntity> images = new ArrayList<>();

        Query q = new Query("#mlhsoundstorm");
        QueryResult result;

        result = twitter.search(q);

        mainloop:
        for (Status status : result.getTweets()) {
            System.out.println(status.getText());
            MediaEntity[] media = status.getMediaEntities();
            for (MediaEntity medium : media) {
                images.add(medium);
                /*SoundFileWriter out = new SoundFileWriter("sound/out.wav");

                 out.writeToBuffer(ImageProcessor.readImage(new URL(medium.getMediaURL())));
                 break mainloop;*/
            }
        }
        return images;
    }

    public static Twitter autenticateTwitter() throws TwitterException, MalformedURLException {

        TwitterFactory tf = new TwitterFactory();

        AccessToken accessToken = new AccessToken("3133629375-889JO9uXWs9f0TVyl6uDg5x495p6SqJMOKTdSTZ", "xLPisaRAjgeL2gmzQRAimRYkgpfaMDq9U5ZemWsKZ6UfW");

        Twitter twitter = tf.getInstance();
        twitter.setOAuthConsumer("jXHtxyF58tmDFrZqFJlp3BYI4", "5iGUk87bqXgn9tdTr5yvdPnlELVEH6lB6ODUwDN1duSw2EPCKm");
        twitter.setOAuthAccessToken(accessToken);

        return twitter;
    }
}
