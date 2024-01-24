import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class Power {
	String name = "";	
	String cnt_in_effect;
	String triggered;
	String activated;
	String expired;
	String power_suspended;
	String cnt_suspended;
	String terminated;
	String exerted;
	String power_resumed;
	String cnt_resumed;
	String antecedent;	
	String pow;
	
	Map<String, String> events = new HashMap<>();
	Map<String, String> defines = new HashMap<>();
	
	Power(String name){
		
		this.name = name;	
		cnt_in_effect = "";
		triggered = "";
		activated = "";
		expired = "";
		power_suspended = "";
		cnt_suspended = "";
		terminated = "";
		exerted = "";
		power_resumed = "";
		cnt_resumed = "";
		antecedent = "";
	}
	
	public void generate() {

		if(cnt_in_effect.trim().length()==0) {
			cnt_in_effect = "TRUE";
		}

		if(triggered.trim().length()==0) {
			events.put(this.name+"_triggered", "Event("+cnt_in_effect+");");
			triggered = this.name+"_triggered._happened";
		}

		if(activated.trim().length()==0) {
			events.put(this.name+"_activated", "Event("+this.name+".state=create);");
			activated = this.name+"_activated._happened";
		}
		if(expired.trim().length()==0) {
			events.put(this.name+"_expired", "Event("+this.name+".state=create);");
			expired = this.name+"_expired._happened";
		}
		if(power_suspended.trim().length()==0) {
			//events.put(this.name+"_power_suspended", "Event("+this.name+".state=inEffect);");
			//power_suspended = this.name+"_power_suspended._happened";
			power_suspended = "FALSE";
		}
		if(cnt_suspended.trim().length()==0) {
			//events.put(this.name+"_cnt_suspended", "Event("+this.name+".state=inEffect);");
			//cnt_suspended = this.name+"_cnt_suspended._happened";
			cnt_suspended = "FALSE";
		}
		if(terminated.trim().length()==0) {
			//events.put(this.name+"_terminated", "Event("+this.name+".state=inEffect);");
			//terminated = this.name+"_terminated._happened";
			terminated = "FALSE";
		}
		if(exerted.trim().length()==0) {
			events.put(this.name+"_exerted", "Event("+this.name+".state=inEffect);");
			exerted = this.name+"_exerted._happened";
		}
		if(power_resumed.trim().length()==0) {
			//events.put(this.name+"_power_resumed", "Event("+this.name+".state=suspension);");
			//power_resumed = this.name+"_power_resumed._happened";
			power_resumed = "FALSE";
		}
		if (cnt_resumed.trim().length()==0) {
			//events.put(this.name+"_cnt_resumed", "Event("+this.name+".state=suspension);");
			//cnt_resumed = this.name+"_cnt_resumed._happened";
			cnt_resumed = "FALSE";
		}

		if(antecedent.trim().length()==0) {
			antecedent = "TRUE";
		}
	}
	
	public void set_antecedent(String ant) {
		antecedent = ant;
	}
	
	public void set_cnt_ineffect(String inef) {
		cnt_in_effect = inef;
	}
	
	public void set_trigger(String trg) {
		triggered = trg;
	}
	
	public String get() {
		pow = "Power("+ cnt_in_effect + "," + triggered +","+ activated +",\r\n\t\t\t" + 
				expired +","+ power_suspended +","+ cnt_suspended +","+ terminated +",\r\n\t\t\t"+ 
				exerted +","+ power_resumed + "," + cnt_resumed +","+ antecedent+");\n";
		
		return "\t\t" + name + "\t:\t" + pow;
	}
	
	public String export_events() {
		String list = "";
		Iterator<Entry<String, String>> itr = events.entrySet().iterator();
		while(itr.hasNext()) {
			Map.Entry<String, String> event = itr.next();
			list += "\t\t"+event.getKey() +"\t:\t"+event.getValue()+"\n";
		};
		return list;
	}
}
