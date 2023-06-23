<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="css/style.css">
    <title>Annotation Guessing Game - Easy</title>
</head>
<body>
<div id="contentContainer">
    <h2 id="contentHeader">Which one is annotated by a human?</h2>
    <p id="contentDescription">Make your guess by clicking on the picture</p>
    <div id="pictureContainer">
        <img id="pictureOne" src="" alt="Picture One">
        <img id="pictureTwo" src="" alt="Picture Two">
    </div>
    <div id="histogramContainer">
        <div id="histogram">
            <div class="column" id="correct-1" style="height: ${histogram[0] + histogram[1] - 1}%;">
                <span class="percentage">10%</span>
            </div>
            <div class="column" id="correct-2" style="height: ${histogram[2]}%;">
                <span class="percentage">20%</span>
            </div>
            <div class="column" id="correct-3" style="height: ${histogram[3]}%;">
                <span class="percentage">30%</span>
            </div>
            <div class="column" id="correct-4" style="height: ${histogram[4]}%;">
                <span class="percentage">40%</span>
            </div>
            <div class="column" id="correct-5" style="height: ${histogram[5]}%;">
                <span class="percentage">50%</span>
            </div>
            <div class="column" id="correct-6" style="height: ${histogram[6]}%;">
                <span class="percentage">60%</span>
            </div>
            <div class="column" id="correct-7" style="height: ${histogram[7]}%;">
                <span class="percentage">70%</span>
            </div>
            <div class="column" id="correct-8" style="height: ${histogram[8]}%;">
                <span class="percentage">80%</span>
            </div>
            <div class="column" id="correct-9" style="height: ${histogram[9]}%;">
                <span class="percentage">90%</span>
            </div>
            <div class="column" id="correct-10" style="height: ${histogram[10]}%;">
                <span class="percentage">100%</span>
            </div>
        </div>
        <button id="refreshBtn" onclick="location.reload()">Try again</button>
        <br>
        <form method="post">
            <input type="hidden" name="action" value="changeGamemode"></input>
            <button type="submit" id="switchGamemodeBtn">Try hardmode</button>
        </form>
    </div>
</div>

<script src="js/easy.js"></script>
</body>
</html>