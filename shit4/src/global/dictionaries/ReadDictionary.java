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

/**
 * Class uesd to read dictionaries from files
 * 
 * All dictionary files should be written in UTF-8 format.
 * the file layout should be:
 * entry1<tab>translation1
 * entry2<tab>translation2
 * entry3<tab>translation3
 * entry4<tab>translation4
 * 
 * There is no constraint on entry order but wrong entry names
 * will result in a reading error.
 * 
 * @author Ophir De Jager
 *
 */
/*
 * Suggestion: If we have time, it makes more sense to save the dictionaries
 * as XML files (I think it's safer and more portable). I do this now
 * because it's quicker and easier (I have NO IDEA how the XML magic works
 * and I don't have time to learn that now).
 */
public class ReadDictionary{
	
	//dictionaries standard root directory
	public static final String directory = "languages/dictionaries/";
	
	//<tab> to separate entries and translations
	public static String delimiter = "\u0009";	
	
	
	/**
	 * Read the given file and create a dictionary map from it
	 * @param filename: the file name
	 * @return a map from entries to translations
	 * @throws IOException if reading error occurred or file format was bad
	 */
	public static Map<Messages, String> readDictionary(String filename) throws IOException{
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
		
		in.close();
		return dict;
	}
	
	
	/**
	 * Filter non-ASCII characters
	 * (needed to match entries with Messages enum types)
	 * @param inString: String to be filtered
	 * @return an ASCII String corresponding to the given non-ASCII given String
	 */
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
