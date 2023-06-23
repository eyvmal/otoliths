package no.eyvind.otoliths.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class LoginUtil {
	
	private final static int MAX_INTERACTIVE_INTERVAL = 60;

	public static void logOutUser(HttpSession session) {
        session.invalidate();
	}

	public static void logInUser(HttpServletRequest request, String username, String difficulty) {
    	
        logOutUser(request.getSession());

        HttpSession session = request.getSession();
        session.setMaxInactiveInterval(MAX_INTERACTIVE_INTERVAL);

		if (username == null || username.equals("")){
			session.setAttribute("username", "Anonymous");
		} else {
			session.setAttribute("username", username);
		}

		session.setAttribute("difficulty", difficulty);
	}
	
	public static boolean isUserLoggedIn(HttpSession session) {
		return session != null 
				&& session.getAttribute("username") != null;
	}

}
