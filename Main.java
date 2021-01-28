import java.io.IOException;
import java.io.File;
import java.util.*;

public class Main {

	public static void main(String[] args) throws IOException, InterruptedException {
		// TODO Auto-generated method stub
		if(args.length < 2)
			System.out.println("Missing arguments: java Main <obligations number> <powers number> <properties number>");
		Test test = new Test(Integer.parseInt(args[0]), Integer.parseInt(args[1]), Integer.parseInt(args[2]));
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
