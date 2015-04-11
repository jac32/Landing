import java.awt.image.*;
import javax.imageio.ImageIO;
import java.io.*;
import java.util.ArrayList;

// should create an image from a number of bytes
class ImageProcessor {
    //static final int X = 3962, Y = 6495;
    //public static boolean rgba = false;

    public static void main(String[] args) {

        try {
            SoundFileReader in = new SoundFileReader("sound/sandstorm.wav");

            createImage(in);
            
            SoundFileWriter out = new SoundFileWriter("sound/out.wav");
            
            out.writeToBuffer(readImage("test.png"));
            
            out.writeFromBufferToFile();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void createImage(SoundFileReader reader) throws IOException {
        int length = (int) reader.getFileSizeInBytes();

        int X = (int) Math.ceil(Math.sqrt(length / 3));
        int Y = X;

        BufferedImage I = new BufferedImage(X, Y, BufferedImage.TYPE_INT_RGB);
        WritableRaster wr = I.getRaster();
        int x = X, y = Y;

        mainLoop:
        for (int j = 0; j < x; j++) {
            for (int i = 0; i < y; i++) {
                int[] a = new int[3];
                try {
                    a[0] = (int) reader.readByte() + 127;
                    a[1] = (int) reader.readByte() + 127;
                    a[2] = (int) reader.readByte() + 127;
                } catch (EOFException e) {
                    wr.setPixel(j, i, a);
                    break mainLoop;
                }
                wr.setPixel(j, i, a);

            }
        }

        try {
            ImageIO.write(I, "png", new File("test.png"));
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
            for (int i = 0; i < width; i++) {
                for (int j = 0; j < height; j++) {
                    int[] rgb = new int[3];
                    raster.getPixel(i, j, rgb);
                    for (int k = 0; k < rgb.length; k++) {
                        output.add(new Byte((byte) (rgb[k] - 127)));
                    }
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