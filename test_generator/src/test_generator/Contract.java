package test_generator;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;

public class Contract {
	ArrayList<Obligation> obls = new ArrayList<>();
	ArrayList<Power> pows = new ArrayList<>();
	
	public Contract(int obl_number, int pow_number, int trigger_intdep_percent, int ant_intdep_percent) {
		int tr_interdependent = (obl_number * trigger_intdep_percent) /100;
		int ant_interdependent = (pow_number * ant_intdep_percent) /100;
		for(int number = 0; number<obl_number; number++) {
			Obligation obl;
			obl = new Obligation("obl"+number);
			obl.set_cnt_ineffect("TRUE");
			obls.add(obl);
		}
		for(int number = 0; number<pow_number; number++) {
			Power pow;
			pow = new Power("pow"+number);
			pow.set_cnt_ineffect("TRUE");
			pows.add(pow);
		}
		
		// exection of a power triggers an obligation(create obligation by a power)
		HashSet<Integer> used = new HashSet<>();
		while(tr_interdependent>0) {
			Random rnd = new Random();
			int depender = rnd.nextInt(obl_number);
			if(used.contains(depender))
				continue;			
			int dependee = rnd.nextInt(pow_number);
			used.add(depender);
			obls.get(depender).set_trigger("pow"+ dependee +".state=sTermination");
			tr_interdependent --;
		}
		
		//violation of an obligation activates a power (antecedent becomes true)
		used.clear();
		while(ant_interdependent>0) {
			//generate with uniform distribution
			Random rnd = new Random(System.currentTimeMillis());
			int depender = rnd.nextInt(pow_number);
			if(used.contains(depender))
				continue;			
			int dependee = rnd.nextInt(obl_number);
			used.add(depender);
			pows.get(depender).set_antecedent("obl"+ dependee +".state=violation");
			ant_interdependent --;
		}
		
	}
	
	public String get() {
		String events = "";
		String obligations = "";
		String powers = "";
		String content = "";
		Iterator<Obligation> itr = obls.iterator();		
		while(itr.hasNext()) {
			Obligation obl = itr.next();
			obl.generate();
			events += obl.export_events();
			obligations += obl.get();
		}
		
		Iterator<Power> itrp = pows.iterator();		
		while(itrp.hasNext()) {
			Power pow = itrp.next();
			pow.generate();
			events += pow.export_events();
			powers += pow.get();
		}
		
		content += "--------------------------------------------------------------------------------------\r\n" + 
				"-- Sample contract\r\n" + 
				"--------------------------------------------------------------------------------------\r\n" + 
				"MODULE main()\r\n" + 				
				"\r\n" + 
				"\t VAR \n" +
				events + obligations + powers;
		
		return content;
	}
}