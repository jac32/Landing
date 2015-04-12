
import java.awt.image.*;
import javax.imageio.ImageIO;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.*;
import java.util.ArrayList;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

// should create an image from a number of bytes
class ImageProcessor {
    //static final int X = 3962, Y = 6495;
    //public static boolean rgba = false;

<<<<<<< HEAD
    public static void convertToSound(String filePath) {

        try {
            SoundFileWriter out = new SoundFileWriter("sound/out.wav");

            out.writeToBuffer(readImage(filePath));
=======
    static final String BASE_URL = "https://api.twitter.com/1.1/search/q=%23mlhtest123";
    
   
    public static void main(String[] args) {

        try {
            //authenticateTwitter();
            
            SoundFileReader in = new SoundFileReader("sound/sandstorm.wav");

            createImage(in);

            SoundFileWriter out = new SoundFileWriter("sound/out.wav");

            out.writeToBuffer(readImage("test.png"));

            createOverlayImage();
>>>>>>> Twitter

            out.writeFromBufferToFile();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

<<<<<<< HEAD
    public static void convertToImage(String filePath) throws UnsupportedAudioFileException {

        try {
            SoundFileReader in = new SoundFileReader(filePath);

            createImage(in);

        } catch (UnsupportedAudioFileException e) {
            throw new UnsupportedAudioFileException();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

=======
     public static void authenticateTwitter(){
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
        .setOAuthConsumerKey("jXHtxyF58tmDFrZqFJlp3BYI4")
        .setOAuthAccessToken("S6ORLtignd2apPiFXV2ALHcmLSc%3D")
        .setOAuthAccessTokenSecret("7b33ed536d8248c989939132281ab6ff");
        TwitterFactory tf = new TwitterFactory(cb.build());
        Twitter twitter = tf.getInstance();
    }
    
>>>>>>> Twitter
    public static void createImage(SoundFileReader reader) throws IOException {
        int length = (int) reader.getFileSizeInBytes();

        int n = (int) Math.ceil(Math.sqrt(length / 3));

        BufferedImage I = new BufferedImage(n, n, BufferedImage.TYPE_INT_RGB);
        WritableRaster wr = I.getRaster();

        mainLoop:
        for (int j = 0; j < n; j++) {
            for (int i = 0; i < n; i++) {
                int[] a = new int[3];
                try {
                    a[0] = (int) reader.readByte() + 128 + i * 255 / n;
                    a[1] = (int) reader.readByte() + 128 + j * 255 / n;
                    a[2] = (int) reader.readByte() + 128 + j * 255 / n;

                } catch (EOFException e) {
                    wr.setPixel(i, j, a);
                    break mainLoop;
                }
                wr.setPixel(i, j, a);

            }
        }
        //applyOverLay(wr);
        try {
            ImageIO.write(I, "png", new File("test.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void applyOverLay(WritableRaster wr) {

        int height = wr.getHeight();

        int width = wr.getWidth();

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                int[] rgb = new int[3];
                wr.getPixel(j, i, rgb);
                /*if (rgb[0] - j * 255 / width > 0 && rgb[1] -i * 255 / height > 0 && rgb[2] - i * 255 / height > 0) {
                 wr.setPixel(i, j, rgb);
                 } else {
                 int[] newrgb = {255, 255, 255};
                 wr.setPixel(i, j, newrgb);
                 }*/
                rgb[0] += j / width * 255;
                rgb[1] += i / height * 255;
                rgb[2] += i / height * 255;

            }
        }
    }

    public static void createOverlayImage() throws IOException {

        int N = 2000;

        BufferedImage I = new BufferedImage(N, N, BufferedImage.TYPE_INT_RGB);
        WritableRaster wr = I.getRaster();

        mainLoop:
        for (int j = 0; j < N; j++) {
            for (int i = 0; i < N; i++) {
                int[] a = {i * 255 / N, j * 255 / N, j * 255 / N};

                wr.setPixel(i, j, a);

            }
        }

        try {
            ImageIO.write(I, "png", new File("testpattern.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static byte[] readImage(String imageFilePath) {
        BufferedImage in = null;

        try {
            in = ImageIO.read(new File(imageFilePath));
        } catch (Exception e) {
            System.out.println("File not available!");
            System.exit(-1);
        }

        WritableRaster raster = in.getRaster();
        int height = raster.getHeight();
        System.out.println(height);
        int width = raster.getWidth();
        System.out.println(width);

        ArrayList<Byte> output = new ArrayList<>();

        //if (rgba) {
        //    for (int i = 0; i < width; i++) {
        //        for (int j = 0; j < height; j++) {
        //            int[] rgb = new int[4];
        //             raster.getPixel(i, j, rgb);
        //            for (int k = 0; k < rgb.length; k++) {
        //                output.add(new Byte((byte) (rgb[k] - 127)));
        //            }
        //        }
        //    }
        //} else {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                int[] rgb = new int[3];
                raster.getPixel(j, i, rgb);
                output.add(new Byte((byte) (rgb[0] - 128 - j * 255 / width)));
                output.add(new Byte((byte) (rgb[1] - 128 - i * 255 / height)));
                output.add(new Byte((byte) (rgb[2] - 128 - i * 255 / height)));
            }
        }
        //}

        byte[] out = new byte[output.size()];
        int index = 0;
        for (Byte item : output) {
            out[index] = item;
            index++;
        }

        return out;
    }
}

/*
 if (rgba) {

 int X = (int) Math.ceil(Math.sqrt(input.length));
 int Y = X;

 J = new BufferedImage(X, Y, BufferedImage.TYPE_4BYTE_ABGR);
 WritableRaster wr = J.getRaster();
 int x = X, y = Y;
 int[] a = new int[4];
 for (int j = 0; j < x; j++) {
 for (int i = 0; i < y; i++) {

 a[0] = (int) input[counter] + 127;
 a[1] = (int) input[counter++] + 127;
 a[2] = (int) input[counter++] + 127;
 a[3] = (int) input[counter++] + 127;

 wr.setPixel(j, i, a);

 //for (int k = 0; k < a.length; k++) {
 //    out.println(a[k]);
 //}
 }
 }
 } else {
 */
