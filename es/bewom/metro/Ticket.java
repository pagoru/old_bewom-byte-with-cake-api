package es.bewom.metro;

import java.util.Calendar;
import java.util.UUID;

import com.google.gson.annotations.Expose;

public class Ticket {
	
	@Expose protected long 	msb;
	@Expose protected long		lsb;
	
	@Expose protected int 		duration;
	@Expose protected int 		travels;
	
	@Expose protected int	 	year;
	@Expose protected int		month;
	@Expose protected int		day;
	@Expose protected int 		hour;
	@Expose protected int		minute;
	@Expose protected int 		second;
	
	@Expose protected boolean valid;
	
	public Ticket(UUID uuid, int duration, Calendar creationTime, int travels){
		this.valid		 	= true;
		
		this.msb 			= uuid.getMostSignificantBits();
		this.lsb			= uuid.getLeastSignificantBits();
		
		this.duration 		= duration;
		this.travels 		= travels;
		
		this.year 			= creationTime.get(Calendar.YEAR);
		this.month 			= creationTime.get(Calendar.MONTH);
		this.day 			= creationTime.get(Calendar.DAY_OF_MONTH);
		this.hour 			= creationTime.get(Calendar.HOUR);
		this.minute 		= creationTime.get(Calendar.MINUTE);
		this.second			= creationTime.get(Calendar.SECOND);
	}
	
	public boolean isSame(Ticket tick){
		if(tick.getUUID().equals(getUUID())){
			return true;
		}
		return false;
	}
	
	public UUID getUUID() {
		return new UUID(msb, lsb);
	}

	public int getDuration() {
		return duration;
	}

	public Calendar getCreationTime() {
		Calendar cal = Calendar.getInstance();
		cal.set(year, month, day, hour, minute, second);
		return cal;
	}

	public int getTravels() {
		return travels;
	}
	
	public boolean isValid() {
		
		if(this.valid){
			
			valid = false;
			
			for(Ticket t : Metro.tickets){
				
				if(t.isSame(t)){
					valid = true;
				}
				
			}
			
			if(valid){
				
				valid = false;
				
				if(this.travels != 0){
					
					Calendar cal = Calendar.getInstance();
					
					Calendar creationTime = getCreationTime();
					creationTime.add(Calendar.DAY_OF_MONTH, this.duration);
					
					if(creationTime.compareTo(cal) > 0){
						valid = true;
					}
					
				}
				
			} else {
				valid = false;
			}
			
		}
		
		return valid;
		
	}
	
	public void removeOneTravel(){
		
		if(this.travels != 0){
			this.travels--;
		} else {
			isValid();
		}
		
	}
	
}
