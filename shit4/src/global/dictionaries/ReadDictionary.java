package global.dictionaries;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.CodingErrorAction;
import java.util.EnumMap;
import java.util.Map;
import java.util.Scanner;

public class ReadDictionary{
	
	private static final String directory = "dictionaries/";
	
	public static String delimiter = "\u0009";	
	
	
	public class LanguageNotSupportedException extends Exception{
		private static final long serialVersionUID = 1L;
	} 
	
	
	
	public static Map<Messages, String> readDictionary(String filename){
		try{
			Scanner in = new Scanner(new File(directory + filename), "UTF8");
			String s, key, value;
			
			EnumMap<Messages, String> dict = new EnumMap<Messages, String>(Messages.class);
			
			while(in.hasNext()){
				s = in.nextLine();
				String[] parts = s.split(delimiter, 2);
				key = parts[0];
				value = parts[1];
				dict.put(Messages.valueOf(filterNonAscii(key)), value);
			}
			
			return dict;
		}
		catch(IOException e){}
		return null;
	}
	
	
	private static String filterNonAscii(String inString) {
		// Create the encoder and decoder for the character encoding
		Charset charset = Charset.forName("US-ASCII");
		CharsetDecoder decoder = charset.newDecoder();
		CharsetEncoder encoder = charset.newEncoder();
		// This line is the key to removing "unmappable" characters.
		encoder.onUnmappableCharacter(CodingErrorAction.IGNORE);
		String result = inString;

		try {
			// Convert a string to bytes in a ByteBuffer
			ByteBuffer bbuf = encoder.encode(CharBuffer.wrap(inString));

			// Convert bytes in a ByteBuffer to a character ByteBuffer and then to a string.
			CharBuffer cbuf = decoder.decode(bbuf);
			result = cbuf.toString();
		} catch (CharacterCodingException cce) {}

		return result;	
	}

}
