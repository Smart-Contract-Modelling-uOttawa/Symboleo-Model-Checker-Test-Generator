import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class LtlGenerator {
	private int obl_num, pow_num;
	private int prop_number;
	private String ltl_file = "ltl-priorities.ltl";
	private String tmp_file = "tmp_file.ltl";
	private String all_props = "";
	private String atoms = "";
	
	LtlGenerator(int onum, int pownum, int propnum) throws IOException, InterruptedException{
		obl_num = onum;
		pow_num = pownum;
		prop_number = propnum;
		if(propnum <= 0)
			return;
		atoms = "randltl -n" + prop_number + " -p --seed=4 --ltl-priorities 'W=0,M=0' -o " + tmp_file + " ";
		//generate obligation atoms 
		for(int o=0; o<obl_num; o++) {
			atoms += "obl"+o+".state=create ";
			atoms += "obl"+o+".state=inEffect ";
			atoms += "obl"+o+".state=discharge ";
			atoms += "obl"+o+".state=fulfillment ";
			atoms += "obl"+o+".state=suspension ";
			atoms += "obl"+o+".state=violation ";
			atoms += "obl"+o+".state=unsTermination ";
		}
		
		//generate power atoms
		for(int p=0; p<pow_num; p++) {
			atoms += "pow"+p+".state=create ";
			atoms += "pow"+p+".state=inEffect ";
			atoms += "pow"+p+".state=sTermination ";
			atoms += "pow"+p+".state=suspension ";
			atoms += "pow"+p+".state=unsTermination ";
		}
//		write_in_file(atoms, ltl_file);
		ProcessBuilder builder = new ProcessBuilder();
		builder.command("sh", "-c", atoms);
		builder.start();

		File infile = new File(tmp_file);
		while(!infile.exists()){
			Thread.sleep(1000);
		}
		Scanner reader = new Scanner(infile);
		int number = 0;
		while(reader.hasNextLine()) {
			String prop = reader.nextLine();
			prop = prop.replaceAll("\"", "").replaceAll(" R ", " S ").replaceAll("\\(0\\)","\\(FALSE\\)");
			prop = "\t\tLTLSPEC NAME LTL" + number + " := " + prop;
			all_props += prop +"\n\n";
			number ++;
		}
	}

	private void write_in_file(String content, String output_file) throws IOException {
		FileWriter smvFile = new FileWriter(output_file);
		smvFile.write(content);
		smvFile.close();
	}

	public String get() {
		return all_props;
	}
}
