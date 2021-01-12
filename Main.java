import java.io.IOException;

public class Main {

	public static void main(String[] args) throws IOException, InterruptedException {
		// TODO Auto-generated method stub
		Test test = new Test();
		if(args.length != 2) {
			System.out.println("Parameters should be <nuXmv.exe path> <output folder path>");
			System.exit(0);
		}
		test.set_nuxmv_file(args[0]);
		test.set_output_folder(args[1]);
		test.generate();
		test.run();
	}

}
