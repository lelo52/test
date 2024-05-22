let canvas;
let offsetX = 0;
let offsetY = 0;
let circleRadius = 50;

function setup() {
  // Create canvas and place it inside the container
  canvas = createCanvas(windowWidth, windowHeight);
  canvas.parent('canvas-container');
}

function draw() {
  // Set background color and draw pattern
  drawPattern();
  
  // Draw text and clock
  drawTextAndClock();

  // Draw ellipse based on mouse position
  if (mouseX >= circleRadius && mouseX <= width - circleRadius && mouseY >= circleRadius && mouseY <= height - circleRadius) {
    let distance = dist(mouseX, mouseY, width / 2, height / 2); // Calculate distance from center
    let ellipseColor = map(distance, 0, width / 2, 0, 255); // Map distance to color value
    fill(ellipseColor); // Set fill color based on distance
    noStroke();
    ellipse(mouseX, mouseY, circleRadius * 2, circleRadius * 2);
  }
}

function drawPattern() {
  // Set background color
  background(200);
  
  // Draw pattern with white and black rectangles
  for (let x = 0; x <= width; x += 50) {
    for (let y = 0; y <= height; y += 50) {
      let fillColor;
      if ((x + offsetX + y + offsetY) % 2 === 0) {
        fillColor = color(255); // White
      } else {
        fillColor = color(0); // Black
      }
      fill(fillColor);
      rect(x + offsetX, y + offsetY, 50, 50); // Apply offsetX and offsetY
    }
  }
}

function drawTextAndClock() {
  // Set text color
  fill(255);

  // Draw text
  textSize(32);
  textAlign(CENTER, CENTER);
  text("클릭하여 홈페이지에 접속, p5.js!", width/2, height/2);

  // Get current time
  let h = hour();
  let m = minute();
  let s = second();

  // Format the time to always show two digits
  let timeString = nf(h, 2) + ':' + nf(m, 2) + ':' + nf(s, 2);

  // Display the time on the page
  textSize(24);
  textAlign(LEFT, TOP);
  text(timeString, 10, 10);
}

function mouseMoved() {
  // Update pattern offset based on mouse movement
  offsetX = map(mouseX, 0, width, -50, 50);
  offsetY = map(mouseY, 0, height, -50, 50);
}

function mouseClicked() {
  // Check if the mouse click is within the canvas boundaries
  if (mouseX >= 0 && mouseX <= width && mouseY >= 0 && mouseY <= height) {
    window.location.href = "https://editor.p5js.org/";
  }
}

function windowResized() {
  // Resize canvas when window is resized
  resizeCanvas(windowWidth, windowHeight);
}
