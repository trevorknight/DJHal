/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.*;
import javax.sound.sampled.*;
import javax.sound.sampled.AudioSystem.*;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.Clip;
import java.net.*;
import java.net.URL;
import org.apache.xerces.parsers.SAXParser;
import org.xml.sax.*;
import org.xml.sax.XMLReader;
import org.xml.sax.SAXException;
/**
 *
 * @author jessicathompson
 */
public class Stream {
    public static String getStreamURL(String id)
    {
        //XMLElement xml = new XMLElement(this, trackurl);
        String streamurl="";
        String trackurl = "http://freemusicarchive.org/services/playlists/embed/track/"+id;
        XMLReader myReader = new SAXParser();
        mckay.utilities.xml.ParseFileHandler handler;
        handler = new TrackFileHandler();
        myReader.setContentHandler(handler);
        //myReader.setContentHandler(handler);
        try{
            myReader.parse(new InputSource(new URL(trackurl).openStream()));
        }catch (MalformedURLException e)
        {
            System.out.println("Error: MalformedURL");
        }catch (IOException e)
        {
            System.out.println("Error: IO");
        }catch (SAXException e)
        {
            System.out.println("Error: SAX");
        }
        streamurl = handler.parsed_file_contents[0].toString();
        return streamurl;
    }

    public static void Play(String url)
    {
        try
        {
            //File file = new File(filename);
            AudioInputStream in = AudioSystem.getAudioInputStream(new URL(url));
            AudioInputStream din = null;
            AudioFormat baseFormat = in.getFormat();
            AudioFormat decodedFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED,
                    baseFormat.getSampleRate(),
                    16,
                    baseFormat.getChannels(),
                    baseFormat.getChannels() * 2,
                    baseFormat.getSampleRate(),
                    false);
            din = AudioSystem.getAudioInputStream(decodedFormat, in);
            // Play now.
            rawplay(decodedFormat, din);
            in.close();
        }
        catch (Exception e)
        {
            //Handle exception.
        }
    }

    private static void rawplay(AudioFormat targetFormat, AudioInputStream din) throws IOException, LineUnavailableException
    {
        byte[] data = new byte[8192];
        SourceDataLine line = getLine(targetFormat);
        if (line != null)
        {
            // Start
            line.start();
            int nBytesRead = 0, nBytesWritten = 0;
            while (nBytesRead != -1)
            {
                nBytesRead = din.read(data, 0, data.length);
                if (nBytesRead != -1)
                {
                    nBytesWritten = line.write(data, 0, nBytesRead);
                }
            }
            // Stop
            line.drain();
            line.stop();
            line.close();
            din.close();
        }
    }

    private static SourceDataLine getLine(AudioFormat audioFormat) throws LineUnavailableException
    {
        SourceDataLine res = null;
        DataLine.Info info = new DataLine.Info(SourceDataLine.class, audioFormat);
        res = (SourceDataLine) AudioSystem.getLine(info);
        res.open(audioFormat);
        return res;
    }

}
