package es.bewom.chat;

public class Censure {
	
	public static String[] wordsCensured = {
			"meriland", 
			"http://",
			"https://",
			"dynamic",
			"www.",
			"meryland",
			".es",
			".com",
			".net",
			".tc",
			".cat"
	};
	
	public static String[] wordsNotCensured = {
			"bewom"
	};
	
	public static String censureText(String formatedMSG) {
		
		String toReturn = "";
		String[] message = formatedMSG.split(" ");
		for (int i = 0; i < message.length; i++) {
			boolean censuredWord = false;
			censuredWord = blockIps(message[i]);
			for (int j = 0; j < wordsCensured.length; j++) {
				if(message[i].toLowerCase().contains(wordsCensured[j])){
					censuredWord = true;
				}
			}
			for (int j = 0; j < wordsNotCensured.length; j++) {
				if(message[i].toLowerCase().contains(wordsNotCensured[j])){
					censuredWord = false;
				}
			}
			
			if(i != 0){
				toReturn += " ";
			}
			if(censuredWord){
				toReturn += "!@#$!%&";
			} else {
				toReturn += message[i];
			}
		}
		
		return toReturn;
	}
	
	public static boolean blockIps(String addr){
	    if (addr.matches("^\\d+\\.\\d+\\.\\d+\\.\\d+$") || addr.indexOf(":") >= 0 || addr.subSequence(0, addr.length() - 1).toString().matches("^\\d+\\.\\d+\\.\\d+\\.\\d+$")){
	        return true;
	    }
	    return false;
	}
	
}
