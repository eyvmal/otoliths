package no.eyvind.otoliths.controller;

import no.eyvind.otoliths.entity.JSRequest;
import no.eyvind.otoliths.entity.LocalStorageService;
import no.eyvind.otoliths.entity.Statistics;
import no.eyvind.otoliths.util.LoginUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/${app.url.homepage}")
public class HomepageController {

    @Value("${app.message.timedOut}") private String TIMED_OUT_MESSAGE;
    @Value("${app.url.login}")   private String LOGIN_URL;
    @Value("${app.url.homepage}") private String HOMEPAGE_URL;

    LocalStorageService lss = new LocalStorageService();
    Statistics stats = new Statistics();
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
        resultList = lss.calculateHistogram(difficulty);
        model.addAttribute("dynamicUrl", HOMEPAGE_URL); // Used in the JavaScript
        if (resultList != null)
            model.addAttribute("histogram", resultList);

        if (difficulty.equals("easymode")) {
            return "easymodeView";
        } else {
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
            System.out.println("Changing gamemode to hardmode");
        } else {
            session.setAttribute("difficulty", "easymode");
            System.out.println("Changing gamemode to easymode");
        }
        // Laster inn siden på nytt
        return "redirect:" + HOMEPAGE_URL;
    }

    @PostMapping
    public ResponseEntity<Void> showResults(@RequestBody JSRequest jsr, Model model,
                            HttpSession session, RedirectAttributes ra) {

        String username = (String)session.getAttribute("username");
        String difficulty = (String)session.getAttribute("difficulty");

        // Lagrer resultatet til json og oppdaterer statistikk
        lss.addResult(username, jsr.getCorrectGuesses(), difficulty);
        stats.add(jsr.getShownPictures(), jsr.getChosenPictures());

        return ResponseEntity.ok().build();
    }
}
