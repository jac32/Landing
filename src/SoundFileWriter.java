
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
    Mixer outMixer;
    TargetDataLine writerLine;
    File outFile;
    
    public SoundFileWriter(String filePath){
        
    }
}
