package no.eyvind.otoliths.controller;

import no.eyvind.otoliths.entity.JSRequest;
import no.eyvind.otoliths.entity.LocalStorageService;
import no.eyvind.otoliths.entity.Statistics;
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
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/${app.url.homepage}")
public class HomepageController {
    @Value("${app.message.timedOut}") private String TIMED_OUT_MESSAGE;
    @Value("${app.url.login}") private String LOGIN_URL;
    @Value("${app.url.homepage}") private String HOMEPAGE_URL;

    private final LocalStorageService lss;
    private final Statistics stats;
    List<Integer> resultList;
    private static final String EASY_MODE = "easymode";
    private static final String HARD_MODE = "hardmode";

    @Autowired
    public HomepageController(LocalStorageService lss, Statistics stats) {
        this.lss = lss;
        this.stats = stats;
    }

    @GetMapping
    public String showHomepage(HttpSession session, RedirectAttributes ra, Model model) {

        // Checks if you have a valid session, if not you will be redirected to login
        if (!LoginUtil.isUserLoggedIn(session)) {
            ra.addFlashAttribute("redirectMessage", TIMED_OUT_MESSAGE);
            return "redirect:" + LOGIN_URL;
        }

        String difficulty = (String)session.getAttribute("difficulty");

        // Updates the histogram based on the difficulty you've chosen
        resultList = lss.calculateHistogram(difficulty);
        model.addAttribute("dynamicUrl", HOMEPAGE_URL); // Used in the JavaScript
        if (resultList != null)
            model.addAttribute("histogram", resultList);

        if (difficulty.equals(EASY_MODE)) {
            return "easymodeView";
        } else {
            return "hardmodeView";
        }
    }

    // Handles swapping of dificulty
    @PostMapping(params = "action=changeGamemode")
    public String changeGamemode(HttpSession session, RedirectAttributes ra) {

        String difficulty = (String)session.getAttribute("difficulty");

        // Checks your current difficulty and swaps it to the other
        if(difficulty.equals(EASY_MODE)) {
            session.setAttribute("difficulty", HARD_MODE);
            System.out.println("Changing gamemode to hardmode");
        } else {
            session.setAttribute("difficulty", EASY_MODE);
            System.out.println("Changing gamemode to easymode");
        }
        // Reloads the page
        return "redirect:" + HOMEPAGE_URL;
    }

    // Saves your result and updates the statistics
    @PostMapping
    public ResponseEntity<Void> showResults(@RequestBody JSRequest jsr, Model model,
                            HttpSession session, RedirectAttributes ra) {

        String username = (String)session.getAttribute("username");
        String difficulty = (String)session.getAttribute("difficulty");

        lss.addResult(username, jsr.getCorrectGuesses(), difficulty);
        stats.add(jsr.getShownPictures(), jsr.getChosenPictures());

        return ResponseEntity.ok().build();
    }
}
