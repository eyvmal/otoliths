package no.eyvind.otoliths.controller;

import no.eyvind.otoliths.database.ResultService;
import no.eyvind.otoliths.util.LoginUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/${app.url.homepage}")
public class HomepageController {

    @Value("${app.message.timedOut}") private String TIMED_OUT_MESSAGE;
    @Value("${app.url.login}")   private String LOGIN_URL;
    @Value("${app.url.homepage}") private String HOMEPAGE_URL;

    @Autowired
    ResultService resultService;

    List<Integer> resultList;

    @GetMapping
    public String showHomepage(HttpSession session, RedirectAttributes ra, Model model) {

        // Sjekker om man har en aktiv sesjon. Hvis ikke sendes du til login
        if (!LoginUtil.isUserLoggedIn(session)) {
            ra.addFlashAttribute("redirectMessage", TIMED_OUT_MESSAGE);
            return "redirect:" + LOGIN_URL;
        }

        String difficulty = (String)session.getAttribute("difficulty");

        // Oppdatere histogrammet med valgt vanskelighetsgrad
        resultList = resultService.calculateHistogram(difficulty);
        model.addAttribute("histogram", resultList);

        if (difficulty.equals("easymode")) {
            // Videresender deg til easymodeView
            return "easymodeView";
        } else {
            // Videresender deg til hardmodeView
            return "hardmodeView";
        }
    }

    // Funksjon for å bytte gamemode
    @PostMapping(params = "action=changeGamemode")
    public String changeGamemode(HttpSession session, RedirectAttributes ra) {

        String difficulty = (String)session.getAttribute("difficulty");

        // Sjekker hvilken vanskelighetsgrad du har aktiv, og bytter den
        if(difficulty.equals("easymode")) {
            session.setAttribute("difficulty", "hardmode");
        } else {
            session.setAttribute("difficulty", "easymode");
        }
        // Laster inn siden på nytt
        return "redirect:" + HOMEPAGE_URL;
    }

    @PostMapping
    public void showResults(@RequestBody int correctGuesses, Model model,
            HttpSession session, RedirectAttributes ra) {

        // Henter litt info fra sesjonen
        String username = (String)session.getAttribute("username");
        String difficulty = (String)session.getAttribute("difficulty");

        // Lagrer resultatet til postgreSQL databasen
        resultService.addResult(username, correctGuesses, difficulty);
    }
}
