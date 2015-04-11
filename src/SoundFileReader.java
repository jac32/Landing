
import java.io.EOFException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.sound.sampled.*;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Gergely
 */
public class SoundFileReader {

    AudioInputStream inputStream;
    AudioFormat audioFormat;
    byte[] buffer;
    int frameCounter;            

    public long getFileSizeInBytes() {
        return inputStream.getFrameLength() * audioFormat.getFrameSize();
    }

    public long getFrameLength() {
        return inputStream.getFrameLength();
    }

    public long getFrameSize() {
        return audioFormat.getFrameSize();
    }

    public SoundFileReader(String filePath) throws UnsupportedAudioFileException, IOException {

        File file = new File(filePath);

        if (file.canRead()) {
            inputStream = AudioSystem.getAudioInputStream(file);
            audioFormat = inputStream.getFormat();
            buffer = new byte[audioFormat.getFrameSize()];
        } else {
            throw new IOException("Can't read file!");
        }
    }

    public AudioFormat getAudioFormat() {
        return audioFormat;
    }

    /**
     * Reads one frame, and returns the entire frame, or returns null of there
     * is nothing to be read.
     *
     * @return byte array containing the contents of the frame
     * @throws IOException
     */
    public byte[] readFrame() throws IOException {
        int bytesRead = inputStream.read(buffer);

        if (bytesRead != -1) {
            return buffer;
        } else {
            return null;
        }
    }
    
    public byte readByte() throws IOException{
        if(frameCounter == audioFormat.getFrameSize() - 1){
            frameCounter = 0;
            byte[] check = readFrame();
            if(check == null){
                throw new EOFException("End of audio file reached!");
            }
            
        }
        else{
            frameCounter++;     
        }
        
        return buffer[frameCounter];
    }
}
