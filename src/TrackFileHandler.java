/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author jessicathompson
 */

import org.xml.sax.*;

public class TrackFileHandler
extends mckay.utilities.xml.ParseFileHandler
{
     /* FIELDS ****************************************************************/




     /**
      * The url of the mp3 stream
      */
     private	String		streamurl;
     private   String         length;



      /**
      * Identifies what tag has been found
      */
     private	boolean				isStream;


     /* PUBLIC METHODS ********************************************************/
     public String getStream(){
         return streamurl;
     }

     /**
      * This method is called when the start of the XML file to be parsed is
      * reached. Instantiates the root_classifications field, sets other fielsds
      * to null and sets the count to 0.
      */
     public void startDocument()
     {

     }


     /**
      * This method is called when the start of an XML element is encountered.
      * Instantiates new objects when necessary and lets the characters method
      * know what kind of action to take.
      *
      * @param  namespace
      * @param	name		Name of the element that is encountered.
      * @param	atts		The attributes encountered.
      * @throws	SAXException	Exception thrown if is wrong type of XML file.
      */
     public void startElement(String namespace, String name, String qName, Attributes atts)
     throws SAXException
     {
          // Identify the type of tag

          if (name.equals("stream"))
          {
               isStream = true;
               length = atts.getValue("length");
          }
          else
          {
              isStream = false;
          }

     }


     /**
      * This method responds to the contents of tags in a way determined by the
      * name of the tag (as determined by the startElement method).
      */
     public void characters(char[] ch, int start, int length)
     {
          String contents = new String(ch, start, length);
           if (isStream)
           {
               streamurl = contents;
           }

     }

     /**
      * This method is called when the end tag of an XML element is encountered.
      * Adds the current_definition to the definitions list when the feature end
      * tag is encountered.
      *
      * @param	name	Name of the element that is encountered.
      */
    public void endElement(String namespace, String name, String qName)
    {


    }

     /**
      * This method is called when the end tag of an XML element is encountered.
      * Fills the parsed_file_contents field with the SegmentedClassifications.
      */
     public void endDocument()
     {
          parsed_file_contents[0] = streamurl;
          parsed_file_contents[1] = length;
     }
}