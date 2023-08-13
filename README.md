# Otoliths Minigame
This is a browser-minigame where the user is going to guess if the picture is annotated by a human or an AI.

# Setup
- clone the repo
- install Java 17 and maven
- compile the project using maven
- run the .jar
- To reset the results, delete the .ser files in src/main/webapp/WEB-INF/ and create new ones with the exact same names

# About
The project is built using Java Spring MVC as the backend and html/css/js as the frontend.
When the user connects to the page they will be redirected to a login where they can choose to add a username (leave empty for anonymous) and choose a difficulty.
There are two difficulties: easy and hard.
- Easymode will show you the same picture annotated by both a human and an AI and youre going to click the picture that is annotated by a human.
- Hardmode will only show either the picture annotated by a human or the one annotated by the AI and you have to guess which one is shown.

# Todo:
- Add admin page
- Add top results
- Move pictures to local storage
- Add thread safety on read/write methods