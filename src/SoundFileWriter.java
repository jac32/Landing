
import java.io.*;
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
public class SoundFileWriter {
    byte[] buffer;
    AudioInputStream audioWriter;
    AudioFormat audioFormat;
    
    File outFile;
    ByteArrayOutputStream writerBuffer;
    
    public SoundFileWriter(String filePath){
        outFile = new File(filePath);        
        
        writerBuffer = new ByteArrayOutputStream();
    }
    
    public void writeToBuffer(byte data){
        writerBuffer.write(data);
    }
    
    public void writeToBuffer(byte[] data) throws IOException{
        writerBuffer.write(data);
    }   
    
    public void setAudioFormat(){
        float sampleRate = 22050.0f;
        int sampleSizeInBits = 16;
        int channels = 2;
        boolean signed = true;
        boolean bigEndian = true;
        audioFormat = new AudioFormat(sampleRate, sampleSizeInBits,
                                             channels, signed, bigEndian);
    }
    
    public void setAudioFormat(AudioFormat audioFormat){
        this.audioFormat = audioFormat;
    }
    
    public void writeFromBufferToFile() throws UnsupportedAudioFileException, IOException, LineUnavailableException{
        ByteArrayInputStream bais = new ByteArrayInputStream(writerBuffer.toByteArray());
        setAudioFormat();
       
        audioWriter = new AudioInputStream(bais, audioFormat, writerBuffer.toByteArray().length/audioFormat.getFrameSize());
        
        AudioSystem.write(audioWriter, AudioFileFormat.Type.WAVE, outFile);
    }
    
    public void writeFromBufferToFile(AudioFormat audioFormat) throws UnsupportedAudioFileException, IOException, LineUnavailableException{
        ByteArrayInputStream bais = new ByteArrayInputStream(writerBuffer.toByteArray());
        setAudioFormat(audioFormat);
       
        audioWriter = new AudioInputStream(bais, audioFormat, writerBuffer.toByteArray().length/audioFormat.getFrameSize());
        
        AudioSystem.write(audioWriter, AudioFileFormat.Type.WAVE, outFile);
    }
    
}
