package test_generator;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Test {
	private String output_folder = "";
	private String nuXMV_file = "";
	private List<List<Integer>> scenarios = new ArrayList<List<Integer>>();
	private List<String> scenario_files = new ArrayList<String>();
	final int MAX_OBLS = 2;
	
	Test() {
		//generate test scenarios (obligation#, power#, obligation dependency rate, power dependency rate) 
		for(int od=0; od<=100; od+=101) {
			for(int pd=0; pd<=100; pd+=101) {
				for(int p=0; p<MAX_OBLS; p++) {
					for(int o=0; o<MAX_OBLS; o++) {
						scenarios.add(Arrays.asList((int)Math.pow(2,o),0,od,pd));
					}
				}
			}
		}
	}
	
	public void generate() throws IOException {
		for(int i=0; i<scenarios.size(); i++) {
			Contract cnt = new Contract(scenarios.get(i).get(0), scenarios.get(i).get(1), scenarios.get(i).get(2), scenarios.get(i).get(3));		
			String content = read_generic_modules() + cnt.get();
			scenario_files.add(output_folder+"/test"+scenarios.get(i).get(0)+"o"+scenarios.get(i).get(1)+"p"+scenarios.get(i).get(2)+"od"+scenarios.get(i).get(3)+"pd.smv");
			write_in_file(content, scenario_files.get(scenario_files.size()-1));			
		}
		System.out.println("Wrote in " + output_folder);
	}
	
	public void run() throws IOException, InterruptedException {		
		String source_file = output_folder+"/commands.txt";
		String pathToCsv = output_folder+"/result.csv";	
		String command = "";
		FileWriter csvWriter = new FileWriter(pathToCsv);
		csvWriter.append("obligation#, power#, obligation dependency%, power dependency%, elapse time, total time\n");
		
		for(int sc=0; sc<scenario_files.size(); sc++) {
			ProcessBuilder pb = new ProcessBuilder();
			command = "read_model -i " + scenario_files.get(sc) + "; go; compute_reachable; time; quit";
			write_in_file(command, source_file);		
			pb.command("cmd.exe","/c", nuXMV_file + " -source " + source_file);		
			Process ps = pb.start();
			StringBuilder output = new StringBuilder();
			BufferedReader reader = new BufferedReader(
	                new InputStreamReader(ps.getInputStream()));
			
			String line;
	        while ((line = reader.readLine()) != null) {
	            output.append(line + "\n");
	        }
	        
	        int exitVal = ps.waitFor();
	        if (exitVal == 0) {
	            System.out.println("Success!");
	            System.out.println(output);
	            String[] lines = output.toString().split("\n");
	            String result = lines[lines.length-1].replace("elapse:", "").replace("seconds", "").replace("total:", "");
	            csvWriter.append(scenarios.get(sc).get(0).toString()+",").append(scenarios.get(sc).get(1).toString()+",")
	            .append(scenarios.get(sc).get(2).toString()+",").append(scenarios.get(sc).get(3).toString()+",").append(result).append("\n");
	        } else {
	        	System.out.println("Error!");
	        }
		}
		csvWriter.flush();
		csvWriter.close();
		System.exit(0);
	}
	
	public void set_output_folder(String path) {
		output_folder = path;
	}
	
	public void set_nuxmv_file(String path) {
		nuXMV_file = path;
	}
	
	private String read_generic_modules() throws IOException {
		String content = "";
		InputStream reader = Test.class.getResourceAsStream("/generic_smv_modules.txt");
		try {
			content = stream_to_string(reader);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		return content;
	}
	
	private String stream_to_string(InputStream aInInputStream)
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
	
	private void write_in_file(String content, String output_file) throws IOException {
		FileWriter smvFile = new FileWriter(output_file);
		smvFile.write(content);
		smvFile.close();		
	}
}
