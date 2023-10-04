<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="css/style.css">
    <title>Annotation Guessing Game - Hard</title>
</head>
<body dynamic-url="${dynamicUrl}">
<div id="contentContainer">
    <div id="textContainer">
        <h2 id="contentHeader">Is the picture annotated by a human or AI?</h2>
        <p id="contentDescription">Make your guess by clicking the buttons below the picture</p>
        <h3 id="pictureNumber"></h3>
    </div>
    <div id="pictureContainer">
        <div class="pictureWrapperHard">
            <img id="pictureHard" src="" alt="Picture">
        </div>
    </div>
    <div id="btnContainer">
        <button id="humanBtn">Human</button>
        <button id="aiBtn">AI</button>
    </div>
    <div id="histogramContainer">
        <div id="histogram">
            <div class="column" id="column-0" style="height: ${histogram[0]}%;">
                <span class="percentage">0%</span>
            </div>
            <div class="column" id="column-1" style="height: ${histogram[1]}%;">
                <span class="percentage">10%</span>
            </div>
            <div class="column" id="column-2" style="height: ${histogram[2]}%;">
                <span class="percentage">20%</span>
            </div>
            <div class="column" id="column-3" style="height: ${histogram[3]}%;">
                <span class="percentage">30%</span>
            </div>
            <div class="column" id="column-4" style="height: ${histogram[4]}%;">
                <span class="percentage">40%</span>
            </div>
            <div class="column" id="column-5" style="height: ${histogram[5]}%;">
                <span class="percentage">50%</span>
            </div>
            <div class="column" id="column-6" style="height: ${histogram[6]}%;">
                <span class="percentage">60%</span>
            </div>
            <div class="column" id="column-7" style="height: ${histogram[7]}%;">
                <span class="percentage">70%</span>
            </div>
            <div class="column" id="column-8" style="height: ${histogram[8]}%;">
                <span class="percentage">80%</span>
            </div>
            <div class="column" id="column-9" style="height: ${histogram[9]}%;">
                <span class="percentage">90%</span>
            </div>
            <div class="column" id="column-10" style="height: ${histogram[10]}%;">
                <span class="percentage">100%</span>
            </div>
        </div>
        <button id="refreshBtn" onclick="location.reload()">Try again</button>
        <br>
        <form method="post">
            <input type="hidden" name="action" value="changeGamemode"></input>
            <button type="submit" id="switchGamemodeBtn">Try easymode</button>
        </form>
    </div>
</div>
<script src="js/common.js"></script>
<script src="js/hard.js"></script>
</body>
</html>