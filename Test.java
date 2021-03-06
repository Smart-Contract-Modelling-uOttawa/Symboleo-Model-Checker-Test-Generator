import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
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
	private int lprop_number;
	private int cprop_number;
	final int OBL_DEP = 0;
	final int POW_DEP = 0;

	Test(int max_obls, int max_pows, int lpnum, int cpnum) {
		lprop_number = lpnum;
		cprop_number = cpnum;
		//generate test scenarios (obligation#, power#, obligation dependency rate, power dependency rate) 
		for(int p=0; p<=max_obls; p++) {
			for(int o=0; o<=max_pows; o++) {
				scenarios.add(Arrays.asList((int)Math.pow(2,o), (int)Math.pow(2,p),OBL_DEP,POW_DEP));
			}
		}
	}

	public void generate() throws IOException, InterruptedException {
		System.out.println("Writting .ord files in " + output_folder);
		String generic_modules = read_generic_modules();
		for(int i=0; i<scenarios.size(); i++) {
			Contract cnt = new Contract(scenarios.get(i).get(0), scenarios.get(i).get(1), scenarios.get(i).get(2), scenarios.get(i).get(3));
			LtlGenerator ltl_gen = new LtlGenerator(scenarios.get(i).get(0), scenarios.get(i).get(1), lprop_number);
			CtlGenerator ctl_gen = new CtlGenerator(scenarios.get(i).get(0), scenarios.get(i).get(1), cprop_number);
			String content = generic_modules + cnt.get() + ltl_gen.get() + ctl_gen.get();
			scenario_files.add(output_folder+"/test"+scenarios.get(i).get(0)+"o"+scenarios.get(i).get(1)+"p"+scenarios.get(i).get(2)+"od"+scenarios.get(i).get(3)+"pd");
			write_in_file(content, scenario_files.get(scenario_files.size()-1)+".smv");
			Thread.sleep(200);
		}
		System.out.println("Wrote in " + output_folder);
	}

	public void run() throws IOException, InterruptedException {
		String command_file1 = output_folder+"/commands1.txt";
		String command_file2 = output_folder+"/commands2.txt";
		String pathToCCsv = output_folder+"/combinedresult.csv";
		String pathToOCsv = output_folder+"/orderresult.csv";
		String pathToRCsv = output_folder+"/reachresult.csv";	
		String command = "";
		FileWriter csvCWriter = new FileWriter(pathToCCsv);
		FileWriter csvOWriter = new FileWriter(pathToOCsv);
		FileWriter csvRWriter = new FileWriter(pathToRCsv);
		csvCWriter.append("obligation#, power#, obligation dependency%, power dependency%, elapse time, total time\n");
		csvOWriter.append("obligation#, power#, obligation dependency%, power dependency%, elapse time, total time\n");
		csvRWriter.append("obligation#, power#, obligation dependency%, power dependency%, elapse time, total time\n");
		
		for(int sc=0; sc<scenario_files.size(); sc++) {
			ProcessBuilder pb = new ProcessBuilder();
			//generate .ord file
			command = "dynamic_var_ordering -e sift; read_model -i " + scenario_files.get(sc)+".smv" + "; time; go; time; compute_reachable; time; write_order -o " + scenario_files.get(sc)+".ord" + "; quit;";
			write_in_file(command, command_file1);		
			pb.command("bash", "-c", "./"+nuXMV_file + " -source " + command_file1);		
			Process ps = pb.start();
			StringBuilder output = new StringBuilder();
			BufferedReader reader = new BufferedReader(
	                new InputStreamReader(ps.getInputStream()));
			
			String line;
	        while ((line = reader.readLine()) != null) {
	            output.append(line + "\n");
	        }
	        
	        int exitVal = ps.waitFor();
	        float eltime=0, ttime=0;
	        if (exitVal == 0) {
	            System.out.println("Success!");
	            System.out.println(output);
	            String[] lines = output.toString().split("\n");
	            String result = lines[lines.length-1].replace("elapse:", "").replace("seconds", "").replace("total:", "");
	            String[] times = result.split(",");
	            eltime = Float.parseFloat(times[0]);
	            ttime = Float.parseFloat(times[1]);
				
				//record ordering time
				csvOWriter.append(scenarios.get(sc).get(0).toString()+",").append(scenarios.get(sc).get(1).toString()+",")
	            .append(scenarios.get(sc).get(2).toString() +",").append(scenarios.get(sc).get(3).toString() +",")
	            .append(times[0]+",").append(times[1]).append("\n");
				
	        } else {
	        	System.out.println("Error!");
	        }
	        
	        //process smv file
	        command = "read_model -i " + scenario_files.get(sc)+".smv" + "; flatten_hierarchy; encode_variables -i " + scenario_files.get(sc)+".ord" + "; time; go; time; compute_reachable; time; quit;";
			write_in_file(command, command_file2);		
			pb.command("bash", "-c", "./"+nuXMV_file + " -source " + command_file2);		
			ps = pb.start();
			output = new StringBuilder();
			reader = new BufferedReader(
	                new InputStreamReader(ps.getInputStream()));
			
	        while ((line = reader.readLine()) != null) {
	            output.append(line + "\n");
	        }
	        
	        exitVal = ps.waitFor();
	        if (exitVal == 0) {
	            System.out.println("Success!");
	            System.out.println(output);
	            String[] lines = output.toString().split("\n");
	            String result = lines[lines.length-1].replace("elapse:", "").replace("seconds", "").replace("total:", "");
	            String[] stimes = result.split(",");
	            float[] times = {0,0};
				
				//record reach computing execution time
				csvRWriter.append(scenarios.get(sc).get(0).toString()+",").append(scenarios.get(sc).get(1).toString()+",")
	            .append(scenarios.get(sc).get(2).toString() +",").append(scenarios.get(sc).get(3).toString() +",")
	            .append(stimes[0]+",").append(stimes[1]).append("\n");
				
				//record total time(ordering and reach computing time)
	            times[0] = Float.parseFloat(stimes[0]) + eltime;
	            times[1] = Float.parseFloat(stimes[1]) + ttime;
	            csvCWriter.append(scenarios.get(sc).get(0).toString()+",").append(scenarios.get(sc).get(1).toString()+",")
	            .append(scenarios.get(sc).get(2).toString() +",").append(scenarios.get(sc).get(3).toString() +",")
	            .append(Float.toString(times[0])+",").append(Float.toString(times[1])).append("\n");
	        } else {
	        	System.out.println("Error!");
	        }
		}
		csvCWriter.flush();
		csvOWriter.flush();
		csvRWriter.flush();
		csvCWriter.close();
		csvOWriter.close();
		csvRWriter.close();
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
		//InputStream reader = Test.class.getResourceAsStream("/generic_smv_modules.txt");
		File gmodule = new File("generic_smv_modules.txt");
		InputStream reader = new FileInputStream("generic_smv_modules.txt");
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
