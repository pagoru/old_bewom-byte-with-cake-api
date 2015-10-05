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
			".cat",
			"skype",
			"facebook",
			"telefono",
			"movil",
			"whatsapp"
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
	
	public static boolean blockIps(String ip){
		if(ip.length() > 0){
			ip = ip.substring(0, ip.length()-1);
			try {
		        if ( ip == null || ip.isEmpty() ) {
		            return false;
		        }

		        String[] parts = ip.split( "\\." );
		        if ( parts.length != 4 ) {
		            return false;
		        }

		        for ( String s : parts ) {
		            int i = Integer.parseInt( s );
		            if ( (i < 0) || (i > 255) ) {
		                return false;
		            }
		        }
		        if ( ip.endsWith(".") ) {
		            return false;
		        }

		        return true;
		    } catch (NumberFormatException nfe) {
		        return false;
		    }
		}
		return false;
	}
	
}
