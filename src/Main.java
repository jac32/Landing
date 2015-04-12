
import javax.swing.*;
import java.awt.*;
import java.awt.image.*;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import twitter4j.MediaEntity;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.URLEntity;
import twitter4j.auth.AccessToken;
import twitter4j.conf.ConfigurationBuilder;

public class Main {

    public static void main(String args[]) throws IOException {
        JFrame frame = new JFrame("SeeTheMusic");
        ImageIcon img = new ImageIcon("monalisa.png");
        frame.setIconImage(img.getImage());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(new Display());
        frame.pack();
        frame.setVisible(true);
        try {
            autenticateTwitter();
        } catch (TwitterException ex) {
            System.out.println(ex.getMessage());
        }

    }

    public static void autenticateTwitter() throws TwitterException, MalformedURLException, IOException {
        
        TwitterFactory tf = new TwitterFactory();
        
        AccessToken accessToken = new AccessToken("3133629375-889JO9uXWs9f0TVyl6uDg5x495p6SqJMOKTdSTZ", "xLPisaRAjgeL2gmzQRAimRYkgpfaMDq9U5ZemWsKZ6UfW");
        
        Twitter twitter = tf.getInstance();
        twitter.setOAuthConsumer("jXHtxyF58tmDFrZqFJlp3BYI4", "5iGUk87bqXgn9tdTr5yvdPnlELVEH6lB6ODUwDN1duSw2EPCKm");
        twitter.setOAuthAccessToken(accessToken);
        
        Query q = new Query("#mlhtest123");
        QueryResult result;
        
        result = twitter.search(q);
        
        mainloop: for (Status status : result.getTweets()) {
            System.out.println(status.getText());
            MediaEntity[] media = status.getMediaEntities();
            for (MediaEntity medium : media) {

                SoundFileWriter out = new SoundFileWriter("sound/out.wav");

            out.writeToBuffer(ImageProcessor.readImage(new URL(medium.getMediaURL())));
                break mainloop;
            }
        }
    }
}
