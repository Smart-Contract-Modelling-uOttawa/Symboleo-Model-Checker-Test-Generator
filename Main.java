import java.io.IOException;
import java.io.File;

public class Main {

	public static void main(String[] args) throws IOException, InterruptedException {
		// TODO Auto-generated method stub
		Test test = new Test();
		test.set_nuxmv_file("nuXmv");
		// make directory
		File output = new File("result");
		if(!output.exists()){
			output.mkdirs();
		}
		test.set_output_folder("result");
		test.generate();
		test.run();
	}

}
