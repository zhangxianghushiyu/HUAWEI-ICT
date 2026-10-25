const fs = require("fs");
const path = "D:/xyapp/hyt-app/components/tsp-video/tsp-video-list/video-v-encipher.js";
const lines = fs.readFileSync(path, "utf8").split("\n");

// Print current created() lines
console.log("=== Current created() ===");
for (let i = 207; i < 250 && i < lines.length; i++) {
  console.log(i+1 + ":" + JSON.stringify(lines[i]));
}
