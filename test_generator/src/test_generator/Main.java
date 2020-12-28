package test_generator;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) throws IOException {
		int obl_number=0, pow_number=0, trigger_intdep_percent=0, ant_intdep_percent=0;
		System.out.println("Enter number of obligations, powers, and trigger and antecedent interdependency percentage (e.g., 3 5 100 100):");
		Scanner reader = new Scanner(System.in);
		if(reader.hasNextInt())
			obl_number = reader.nextInt();
		if(reader.hasNextInt())
			pow_number = reader.nextInt();
		if(reader.hasNextInt())
			trigger_intdep_percent = reader.nextInt();
		if(reader.hasNextInt())
			ant_intdep_percent = reader.nextInt();
		
		Contract cnt = new Contract(obl_number, pow_number, trigger_intdep_percent, ant_intdep_percent);		
		String content = read_generic_modules() + cnt.get();
		write_in_file(content);
		System.out.println("Done.");
	}
	
	public static String read_generic_modules() throws IOException {
		String content = "";
		InputStream reader = Main.class.getResourceAsStream("/generic_smv_modules.txt");
		try {
			content = stream_to_string(reader);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		return content;
	}
	
	private static String stream_to_string(InputStream aInInputStream)
    		throws IOException {
        ByteArrayOutputStream byteArrayOutputStream =
        		new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int length;
        while ((length = aInInputStream.read(buffer)) != -1) {
            byteArrayOutputStream.write(buffer, 0, length);
        }
        return byteArrayOutputStream.toString(StandardCharsets.UTF_8.name());
    }
	
	private static void write_in_file(String content) throws IOException {
		System.out.print("Where do you want to store SMV file?");
		Scanner reader = new Scanner(System.in);
		if(reader.hasNextLine()) {
			String file = reader.nextLine();
			FileWriter smvFile = new FileWriter(file);
			smvFile.write(content);
			smvFile.close();
		}		
	}
}
