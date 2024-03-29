import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class Obligation {
	String name = "";	
	String surviving;
	String cnt_in_effect;
	String cnt_term;
	String triggered;
	String fulfilled;
	String violated;
	String activated;
	String expired;
	String power_suspended;
	String cnt_suspended;
	String terminated;
	String power_resumed;
	String cnt_resumed;
	String discharged;
	String antecedent;
	String obl;
	
	Map<String, String> events = new HashMap<>();
	Map<String, String> defines = new HashMap<>();
	
	Obligation(String name){
		
		this.name = name;	
		surviving = "";
		cnt_in_effect = "";
		cnt_term = "";
		triggered = "";
		fulfilled = "";
		violated = "";
		activated = "";
		expired = "";
		power_suspended = "";
		cnt_suspended = "";
		terminated = "";
		power_resumed = "";
		cnt_resumed = "";
		discharged = "";
		antecedent = "";
	}
	
	public void generate() {
		
		if(surviving.trim().length()==0) {
			//events.put(this.name+"_surviving", "Event("+this.name+".state=inEffect);");
			//surviving = this.name+"_surviving._happened";
			surviving = "FALSE";
		}
		if(cnt_in_effect.trim().length()==0) {
			cnt_in_effect = "TRUE";
		}
		if(cnt_term.trim().length()==0) {
			cnt_term = "FALSE";
		}
		if(fulfilled.trim().length()==0) {
			events.put(this.name+"_fulfilled","Event("+this.name+".state=inEffect);");
			fulfilled = this.name+"_fulfilled._happened";
		}
		if(triggered.trim().length()==0) {
			events.put(this.name+"_triggered", "Event("+cnt_in_effect+");");
			triggered = this.name+"_triggered._happened";
		}
		if(violated.trim().length()==0) {
			//events.put(this.name+"_violated", "Event("+this.name+".state=inEffect);");
			//violated = this.name+"_violated._happened";
			violated = this.name+"_fulfilled._expired";
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
		if(discharged.trim().length()==0) {
			//events.put(this.name+"_discharged", "Event("+this.name+".state=inEffect);");
			//discharged = this.name+"_discharged._happened";
			discharged = "FALSE";
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
	
	public void set_surviving(String srv) {
		surviving = srv;
	}
	
	public void set_trigger(String trg) {
		triggered = trg;
	}
	
	public String get() {
		obl = "Obligation("+surviving+","+ cnt_in_effect +","+ cnt_term+",\r\n\t\t\t"+ 
				fulfilled+"," + triggered +","+ violated +","+ activated +",\r\n\t\t\t" + 
				expired +","+ power_suspended +","+ cnt_suspended +","+ terminated +","+ power_resumed +",\r\n\t\t\t" + 
				cnt_resumed +","+ discharged +","+ antecedent+");\n";
		return "\t\t" + name + "\t:\t" + obl;
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
