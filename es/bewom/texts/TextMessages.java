package es.bewom.texts;

import org.cakepowered.api.util.text.TextFormating;

public class TextMessages {
	
	public static final String TP_SUCCESS = TextFormating.RED + "Teletransporte exitoso.";
	
	public static final String NO_PERMISSIONS = TextFormating.RED + "No tienes permisos.";
	public static final String ERROR = TextFormating.RED + "No me hexperava heste heror.";
	public static final String NOT_CONSOLE_COMPATIBLE = "Este comando no funciona en consola.";

	public static final String TP_EXPIRED = TextFormating.RED + "La solicitud ha expirado.";
	public static final String TP_NOT_FOUND = TextFormating.RED + "No hay ninguna solicitud.";
	public static final String TP_DENIED = TextFormating.RED + "Solicitud de teletransporte denegada.";
	public static final String TP_REQUEST_SENT = TextFormating.RED + "Se ha enviado una solicitud.";
	
	public static final String LOGIN_SUCCESS = TextFormating.RED + "Bienvenido a " + TextFormating.GOLD + "BEWOM" + TextFormating.RED + ".";
	
	public static final String WORLD_NOT_FOUND = TextFormating.RED + "No se ha encontrado el mundo especificado.";
	
	public static final String BROADCAST = 
			TextFormating.DARK_RED +  "" + TextFormating.BOLD + "bewom" +  
			TextFormating.RESET + TextFormating.WHITE + ": ";
	
	public static final String CENTER_ESTABLISHED = TextFormating.RED + "Centro establecido correctamente.";
	public static final String CENTER_REMOVED = TextFormating.RED + "Centro quitado correctamente.";
	
	public static final String[] MP_TALKING_WITH = {
				TextFormating.GREEN + "Ahora estas hablando con ",
				" por mensaje privado."
		};
	public static final String MP_TALKING_FINISH_SUGESTION = TextFormating.GREEN + "Escribe /mp para dejar de hablar por mensaje privado.";
	public static final String MP_TALKING_FINISH = TextFormating.RED + "Has dejado de hablar por mensaje privado.";
	public static final String MP_TALKING_WITH_YOU = TextFormating.RED + "No puedes enviarte un mensaje a ti mismo. ¿Te pegan?";
	
	public static final String FRIENDS_WITH_YOU = TextFormating.RED + "No puedes enviarte una solicitud a ti mismo. ¿Eres tonto?";
	public static final String FRIENDS_LIMIT = TextFormating.RED + "No puedes enviar mas solicitudes de amistad a ";
	public static final String FRIENDS_SEND = TextFormating.GREEN + "Has enviado una solicitud a ";
	public static final String[] FRIENDS_SEND_TO = {
		TextFormating.GREEN + "",
		" quiere ser tu amigo."
	};
	public static final String[] FRIENDS_SEND_TO_ADD_SUGESTION = {
			TextFormating.GREEN + "Usa /amigos aceptar ",
			" para aceptar la solicitud."
	};
	public static final String[] FRIENDS_SEND_TO_DELETE_SUGESTION = {
			TextFormating.GREEN + "Usa /amigos denegar ",
			" para denegar la solicitud."
	};
	public static final String FRIENDS_NOW_FRIENDS = TextFormating.GREEN + "Ahora eres amigo de ";
	public static final String FRIENDS_JUST_FRIENDS = TextFormating.RED + "Ya eres amigo de ";
	public static final String FRIENDS_NOT_FRIENDS = TextFormating.RED + "Ya no eres amigo de ";
	
}
