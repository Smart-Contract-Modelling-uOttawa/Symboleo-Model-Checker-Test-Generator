import java.io.IOException;
import java.io.File;

public class Main {

    public static void main(String[] args) throws IOException, InterruptedException {
        if (args.length < 4) {
            System.out.println("Missing arguments: java Main <obligations number> <powers number> <properties number>");
        }

		// Create the output directory if it doesn't exist
        File output = new File("result");
        if (!output.exists()) {
            output.mkdirs();
        }
		
        Test test = new Test(Integer.parseInt(args[0]), Integer.parseInt(args[1]), Integer.parseInt(args[2]),
                Integer.parseInt(args[3]));
        test.set_nuxmv_file("nuXmv");
		test.set_output_folder("result");
        test.generate();
        test.run();
    }
}
