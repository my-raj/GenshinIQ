// Initial display when he website is opened
document.getElementById("home-screen").style.display = "flex";
document.getElementById("calculator-screen").style.display = "none";
document.getElementById("wish-screen").style.display = "none";
document.getElementById("hub-screen").style.display = "none";
document.getElementById("settings-screen").style.display = "none";
document.getElementById("chart-screen").style.display = "none";

// Switch display from hub screen to calculator screen
document.getElementById("card-pity").addEventListener("click", function() {
    document.getElementById("hub-screen").style.display = "none";
    document.getElementById("calculator-screen").style.display = "flex";
    if (!backgroundAnimationIds["calculator-screen"]) {
        drawBackground(calcCanvas, calcCtx, calcParticles, shootingStars, "calculator-screen");
    }
});

document.getElementById("banner").addEventListener("change", function() {
    var isWeapon = this.value === "weapon";
    var isStandard = this.value === "standard";
    document.querySelector(".guaranteed-checkbox").style.display = (isWeapon || isStandard) ? "none" : "flex";
    document.getElementById("weapon-inputs").style.display = isWeapon ? "flex" : "none";
});

// Switch display from hub screen to setting screen
document.getElementById("settings-btn").addEventListener("click", function() {
    document.getElementById("hub-screen").style.display = "none";
    document.getElementById("settings-screen").style.display = "flex";
    if (!backgroundAnimationIds["settings-screen"]) {
        drawBackground(settingsCanvas, settingsCtx, settingsParticles, settingsShootingStars, "settings-screen");
    }
    loadCharacters();
});

// Switch display from setting screen to hub screen
document.getElementById("settings-back-btn").addEventListener("click", function() {
    document.getElementById("settings-screen").style.display = "none";
    document.getElementById("hub-screen").style.display = "flex";
    if (!backgroundAnimationIds["hub-screen"]) {
        drawBackground(hubCanvas, hubCtx, hubParticles, hubShootingStars, "hub-screen");
    }
});

// Switch display from hub screen to boss screen
document.getElementById("card-boss").addEventListener("click", function() {
    document.getElementById("hub-screen").style.display = "none";
    document.getElementById("boss-screen").style.display = "flex";
    if (!backgroundAnimationIds["boss-screen"]) {
        drawBackground(bossCanvas, bossCtx, bossParticles, bossShootingStars, "boss-screen");
    }
});

document.getElementById("import-btn").addEventListener("click", function() {
    var uid = document.getElementById("uid-input").value;
    if (!uid) {
        document.getElementById("import-status").innerText = "Please enter your UID!";
        return;
    }
    document.getElementById("import-status").innerText = "Importing...";
    fetch("/import/" + uid)
        .then(function(response) {
            if (response.status === 429) {
                return response.text().then(function(text) {
                    document.getElementById("import-status").innerText = text;
                });
            }
            return response.json().then(function(data) {
                document.getElementById("import-status").innerText = "Successfully imported " + data.length + " characters!";
                setTimeout(function() {
                        loadCharacters();
                    }, 1000);
            });
        })
        .catch(function() {
            document.getElementById("import-status").innerText = "Import failed. Please try again.";
        });
});

// Code out character-build advisor
document.getElementById("card-build").addEventListener("click", function() {
    // coming soon
    alert("Character Build Advisor coming soon!");
});

var editCharacterId = null;
var dataDist = [];

// Manually building JSON for weekly bosses
var bossData = [
    {
        name: "Stormterror Dvalin",
        region: "Mondstadt",
        image: "bosses/dvalin.webp",
        immune: ["Frozen", "Stun", "Petrification"],
        weak: ["None"],
        tip: "Break his white shield bar with heavy attacks or high-frequency elemental skills to stun him."
    },
    {
        name: "Andrius",
        region: "Mondstadt",
        image: "bosses/andrius.webp",
        immune: ["Anemo", "Cryo"],
        weak: ["Pyro", "Electro"],
        tip: "Bringing Anemo or Cryo DPS makes this a zero-damage fight. Pyro/Electro work best."
    },
    {
        name: "Childe",
        region: "Liyue",
        image: "bosses/childe.webp",
        immune: ["Hydro (P1/P3)", "Electro (P2/P3)"],
        weak: ["Pyro", "Physical", "Dendro"],
        tip: "He swaps resistances each phase. Pyro Vaporize teams are most consistent across all phases."
    },
    {
        name: "Azhdaha",
        region: "Liyue",
        image: "bosses/azhdaza.webp",
        immune: ["Geo", "Physical", "Rotates 2 elements weekly"],
        weak: ["Check door crystals beforehand"],
        tip: "Avoid mono-element teams. Bring a shielder — his elemental attacks apply a mark that drains HP without one."
    },
    {
        name: "La Signora",
        region: "Inazuma",
        image: "bosses/la_signora.webp",
        immune: ["Cryo (P1)", "Pyro (P2)"],
        weak: ["Pyro (P1)", "Hydro/Electro (P2)"],
        tip: "Use Pyro to break her ice cocoon quickly in Phase 1. Never match her active phase element."
    },
    {
        name: "Magatsu Mitake Narukami",
        region: "Inazuma",
        image: "bosses/narukami.webp",
        immune: ["Electro (70% shield)"],
        weak: ["Cryo", "Pyro", "Electro (for flower)"],
        tip: "Keep an Electro character to charge the Magatsu Gohan flower and block her one-shot ultimate."
    },
    {
        name: "Everlasting Lord of Arcane Wisdom",
        region: "Sumeru",
        image: "bosses/arcane.webp",
        immune: ["Electro", "Hydro"],
        weak: ["Pyro", "Cryo", "Dendro"],
        tip: "Use elemental reactions to load the Neo-Akashic Terminal. It fires a beam that shatters his shield."
    },
    {
        name: "Guardian of Apep's Oasis",
        region: "Sumeru",
        image: "bosses/apep.webp",
        immune: ["Dendro (70%)"],
        weak: ["Pyro", "Electro", "Hydro"],
        tip: "High Dendro resistance throughout. Focus on high-damage reactions during the central defense phase."
    },
    {
        name: "All-Devouring Narwhal",
        region: "Fontaine",
        image: "bosses/narwhal.webp",
        immune: ["Hydro (70%)"],
        weak: ["Pneuma/Ousia (Arkhe)"],
        tip: "Attack the eye with opposing Arkhe energy to stagger it and break its shield state."
    },
    {
        name: "The Knave (Arlecchino)",
        region: "Fontaine",
        image: "bosses/knave.webp",
        immune: ["Pyro (70%)"],
        weak: ["Hydro", "Cryo"],
        tip: "Clear the Bond of Life she places on your characters to gain a massive damage buff against her."
    },
    {
        name: "Lord of Eroded Primal Fire",
        region: "Natlan",
        image: "bosses/primal.webp",
        immune: ["Pyro (high)"],
        weak: ["Hydro", "Electro"],
        tip: "Nightsoul attacks help break Void Wards faster. Uses Attrition debuff that bypasses shields."
    },
    {
        name: "The Game Before the Gate",
        region: "Nod-Krai",
        image: "bosses/gate.webp",
        immune: ["Physical"],
        weak: ["Electro", "Cryo", "Dendro"],
        tip: "Heavily armored machinery means high Physical RES. Focus on elemental chain reactions."
    },
    {
        name: "Heretic of the False Moon",
        region: "Nod-Krai",
        image: "bosses/moon.webp",
        immune: ["Abyssal/Variable"],
        weak: ["Anemo", "Geo", "Elemental"],
        tip: "Rotates defensive protocols. Strike floating matrix segments with reactive elements to force a stun window."
    },
    {
        name: "Exalted Master of the Heretical Path",
        region: "Sumeru",
        image: "bosses/heretic.webp",
        immune: ["Variable"],
        weak: ["Pyro", "Electro", "Cryo"],
        tip: "Use elemental reactions to break its stance and create damage windows."
    }
];

// Group bosses by region
var regions = {};
bossData.forEach(function(boss) {
    if (!regions[boss.region]) {
        regions[boss.region] = [];
    }
    regions[boss.region].push(boss);
});

function noise(x, y) {
    var n = Math.sin(x * 12.9898 + y * 78.233) * 43758.5453;
    return n - Math.floor(n);
}

function smoothNoise(x, y) {
    var corners = (noise(x-1, y-1) + noise(x+1, y-1) + noise(x-1, y+1) + noise(x+1, y+1)) / 16;
    var sides = (noise(x-1, y) + noise(x+1, y) + noise(x, y-1) + noise(x, y+1)) / 8;
    var center = noise(x, y) / 4;
    return corners + sides + center;
}

var riverAnimationId = null;

function drawRiver() {

    if (riverAnimationId) {
        cancelAnimationFrame(riverAnimationId);
    }

    var riverCanvas = document.getElementById("river-canvas");
    var rctx = riverCanvas.getContext("2d");
    riverCanvas.width = window.innerWidth;
    riverCanvas.height = window.innerHeight;
    riverCanvas.style.position = "fixed";
    riverCanvas.style.top = "0";
    riverCanvas.style.left = "0";
    riverCanvas.style.zIndex = "0";

    var riverTime = 0;

    var cloudOffscreens = [];
    for (var c = 0; c < 5; c++) {
        var offCanvas = document.createElement("canvas");
        offCanvas.width = riverCanvas.width;
        offCanvas.height = riverCanvas.height;
        cloudOffscreens.push({ canvas: offCanvas, ctx: offCanvas.getContext("2d") });
    }

    function drawRippledCloudReflection(targetCtx, cloudX, cloudY, scale, canvasWidth, canvasHeight, time, offscreenIndex) {
        var offData = cloudOffscreens[offscreenIndex];
        var offCanvas = offData.canvas;
        var offCtx = offData.ctx;

        offCtx.clearRect(0, 0, canvasWidth, canvasHeight);
        drawCloud(offCtx, cloudX, cloudY, scale);

        var stripHeight = 4;
        var cloudRegionTop = cloudY - 100 * scale;
        var cloudRegionBottom = cloudY + 100 * scale;

        for (var y = cloudRegionTop; y < cloudRegionBottom; y += stripHeight) {
            if (y < 0 || y >= canvasHeight) continue;

            var noiseVal = smoothNoise(y * 0.05 + time * 0.0004, cloudX * 0.01);
            var xOffset = (noiseVal - 0.5) * 30;
            var reflectedY = canvasHeight - y;

            targetCtx.drawImage(
                offCanvas,
                0, y, canvasWidth, stripHeight,
                xOffset, reflectedY, canvasWidth, stripHeight
            );
        }
    }

    function animateRiver() {
        rctx.clearRect(0, 0, riverCanvas.width, riverCanvas.height);

        // Sky gradient (top half)
        var skyGrad = rctx.createLinearGradient(0, 0, 0, riverCanvas.height * 0.5);
        skyGrad.addColorStop(0, "#1a0a2e");
        skyGrad.addColorStop(0.4, "#6b2d6b");
        skyGrad.addColorStop(0.8, "#c4622d");
        skyGrad.addColorStop(1, "#e8a045");
        rctx.fillStyle = skyGrad;
        rctx.fillRect(0, 0, riverCanvas.width, riverCanvas.height * 0.5);

        // Moon
        rctx.beginPath();
        rctx.arc(riverCanvas.width * 0.75, riverCanvas.height * 0.15, 40, 0, Math.PI * 2);
        rctx.fillStyle = "rgba(255, 240, 200, 0.95)";
        rctx.fill();
        var moonGlow = rctx.createRadialGradient(
            riverCanvas.width * 0.75, riverCanvas.height * 0.15, 30,
            riverCanvas.width * 0.75, riverCanvas.height * 0.15, 100
        );
        moonGlow.addColorStop(0, "rgba(255, 240, 180, 0.3)");
        moonGlow.addColorStop(1, "rgba(255, 240, 180, 0)");
        rctx.fillStyle = moonGlow;
        rctx.beginPath();
        rctx.arc(riverCanvas.width * 0.75, riverCanvas.height * 0.15, 100, 0, Math.PI * 2);
        rctx.fill();

        // Clouds

        drawCloud(rctx, riverCanvas.width * 0.15, riverCanvas.height * 0.42, 1.7);
        drawCloud(rctx, riverCanvas.width * 0.45, riverCanvas.height * 0.46, 1.3);
        drawCloud(rctx, riverCanvas.width * 0.7, riverCanvas.height * 0.43, 1.6);
        drawCloud(rctx, riverCanvas.width * 0.9, riverCanvas.height * 0.42, 1.4);
        drawCloud(rctx, riverCanvas.width * 0.02, riverCanvas.height * 0.42, 1.29);


        // River (bottom half)
        var riverGrad = rctx.createLinearGradient(0, riverCanvas.height * 0.5, 0, riverCanvas.height);
        riverGrad.addColorStop(0, "#e8a045");
        riverGrad.addColorStop(0.3, "#c4622d");
        riverGrad.addColorStop(0.7, "#6b2d6b");
        riverGrad.addColorStop(1, "#1a0a2e");
        rctx.fillStyle = riverGrad;
        rctx.fillRect(0, riverCanvas.height * 0.5, riverCanvas.width, riverCanvas.height * 0.5);

        rctx.save();
        rctx.globalAlpha = 0.55;

        drawRippledCloudReflection(rctx, riverCanvas.width * 0.15, riverCanvas.height * 0.52, 1.7, riverCanvas.width, riverCanvas.height, riverTime, 0);
        drawRippledCloudReflection(rctx, riverCanvas.width * 0.45, riverCanvas.height * 0.48, 1.3, riverCanvas.width, riverCanvas.height, riverTime, 1);
        drawRippledCloudReflection(rctx, riverCanvas.width * 0.7, riverCanvas.height * 0.51, 1.6, riverCanvas.width, riverCanvas.height, riverTime, 2);
        drawRippledCloudReflection(rctx, riverCanvas.width * 0.9, riverCanvas.height * 0.52, 1.4, riverCanvas.width, riverCanvas.height, riverTime, 3);
        drawRippledCloudReflection(rctx, riverCanvas.width * 0.02, riverCanvas.height * 0.52, 1.29, riverCanvas.width, riverCanvas.height, riverTime, 4);

        rctx.restore();

        // Moon reflection - rippled
        var reflectCenterX = riverCanvas.width * 0.75;
        var reflectCenterY = riverCanvas.height * 0.85;
        var reflectWidth = 80;
        var reflectHeight = 30;
        var stripCount = 20;

        for (var s = 0; s < stripCount; s++) {
            var stripT = s / stripCount;
            var stripY = reflectCenterY - reflectHeight + (stripT * reflectHeight * 2);

            var noiseVal = smoothNoise(stripY * 0.15 + riverTime * 0.0004, 0);
            var xOffset = (noiseVal - 0.5) * 45;

            var stripHalfWidth = reflectWidth * Math.sqrt(Math.max(0, 1 - Math.pow((stripY - reflectCenterY) / reflectHeight, 2)));

            var stripGrad = rctx.createLinearGradient(
                reflectCenterX - stripHalfWidth + xOffset, stripY,
                reflectCenterX + stripHalfWidth + xOffset, stripY
            );
            stripGrad.addColorStop(0, "rgba(255, 240, 200, 0)");
            stripGrad.addColorStop(0.5, "rgba(255, 240, 200, 0.5)");
            stripGrad.addColorStop(1, "rgba(255, 240, 200, 0)");

            rctx.fillStyle = stripGrad;
            rctx.fillRect(reflectCenterX - stripHalfWidth + xOffset, stripY, stripHalfWidth * 2, reflectHeight / stripCount + 1);
        }

        // River ripples using noise
        for (var i = 0; i < 18; i++) {
            var ry = riverCanvas.height * 0.5 + (i / 18) * riverCanvas.height * 0.5;
            var opacity = 0.04 + (i / 18) * 0.14;

            var points = [];
            for (var x = 0; x <= riverCanvas.width; x += 40) {
                var noiseVal = smoothNoise(x * 0.003 + riverTime * 0.000001, ry * 0.01);
                var waveOffset = (noiseVal - 0.5) * 40;
                points.push({ x: x, y: ry + waveOffset });
            }

            var edgeNoise = smoothNoise(riverCanvas.width * 0.003 + riverTime * 0.0004, ry * 0.01);
            var edgeOffset = (edgeNoise - 0.5) * 40;
            points.push({ x: riverCanvas.width, y: ry + edgeOffset });

            rctx.beginPath();
            rctx.moveTo(points[0].x, points[0].y);

            for (var p = 0; p < points.length - 1; p++) {
                var midX = (points[p].x + points[p + 1].x) / 2;
                var midY = (points[p].y + points[p + 1].y) / 2;
                rctx.quadraticCurveTo(points[p].x, points[p].y, midX, midY);
            }

            var lastPoint = points[points.length - 1];
            rctx.lineTo(lastPoint.x, lastPoint.y);

            rctx.strokeStyle = "rgba(255, 210, 130, " + (opacity * 0.3) + ")";
            rctx.lineWidth = 4;
            rctx.stroke();

            rctx.strokeStyle = "rgba(255, 210, 130, " + opacity + ")";
            rctx.lineWidth = 1.5;
            rctx.stroke();

        }

        riverTime++;

            var chartScreenEl = document.getElementById("chart-screen");
            if (window.getComputedStyle(chartScreenEl).display === "none") {
                riverAnimationId = null;
                return;
            }

            riverAnimationId = requestAnimationFrame(animateRiver);
        }

        animateRiver();
    }

function buildBossList() {
    // Build boss list
    var bossList = document.getElementById("boss-list");
    document.getElementById("boss-list").innerHTML = "";
    Object.keys(regions).forEach(function(region) {
        // Region header
        var regionHeader = document.createElement("div");
        regionHeader.className = "boss-region-header";
        regionHeader.innerText = region;
        bossList.appendChild(regionHeader);

        // Boss items
        regions[region].forEach(function(boss) {
            var bossItem = document.createElement("div");
            bossItem.className = "boss-item";
            bossItem.innerText = boss.name;
            bossItem.addEventListener("click", function() {
                // Remove active from all
                document.querySelectorAll(".boss-item").forEach(function(el) {
                    el.classList.remove("active");
                });
                bossItem.classList.add("active");
                showBossDetail(boss);
            });
            bossList.appendChild(bossItem);
        });
    });
}

document.getElementById("drop-bosses").addEventListener("change", function() {
    if (this.value === "weekly") {
        buildBossList();
    }
    else if (this.value === "normal") {
        alert("Normal bosses coming soon!")
    }
    else {
        document.getElementById("boss-list").innerHTML = "";
        document.getElementById("boss-detail-placeholder").style.display = "flex";
        document.getElementById("boss-detail-content").style.display = "none";
    }
});

// Show boss detail
function showBossDetail(boss) {
    document.getElementById("boss-detail-placeholder").style.display = "none";
    document.getElementById("boss-detail-content").style.display = "block";

    document.getElementById("detail-name").innerText = boss.name;
    document.getElementById("detail-region").innerText = "📍 " + boss.region;

    document.getElementById("boss-image").src = boss.image;
    document.getElementById("boss-image").alt = boss.name;

    document.getElementById("detail-immune").innerHTML =
        "<h3>⚠️ Avoid / Immune</h3>" +
        boss.immune.map(function(el) {
            return "<span class='element-tag immune'>" + el + "</span>";
        }).join("");

    document.getElementById("detail-weakness").innerHTML =
        "<h3>✅ Best Elements</h3>" +
        boss.weak.map(function(el) {
            return "<span class='element-tag weak'>" + el + "</span>";
        }).join("");

    document.getElementById("detail-tip").innerHTML =
        "<h3>💡 Key Mechanic</h3>" +
        "<p>" + boss.tip + "</p>";
}

// Back button
document.getElementById("boss-back-btn").addEventListener("click", function() {
    document.getElementById("boss-screen").style.display = "none";
    document.getElementById("hub-screen").style.display = "flex";
    if (!backgroundAnimationIds["hub-screen"]) {
        drawBackground(hubCanvas, hubCtx, hubParticles, hubShootingStars, "hub-screen");
    }
});

var waterCanvas = document.getElementById("water-canvas");
var wctx = waterCanvas.getContext("2d");
waterCanvas.width = window.innerWidth;
waterCanvas.height = 200;

var waterTime = 0;

// Calculator background particles
var calcCanvas = document.getElementById("calc-canvas");
var calcCtx = calcCanvas.getContext("2d");
calcCanvas.width = window.innerWidth;
calcCanvas.height = window.innerHeight;

var calcParticles = [];
for (var i = 0; i < 80; i++) {
    calcParticles.push({
        x: Math.random() * calcCanvas.width,
        y: Math.random() * calcCanvas.height,
        size: Math.random() * 2 + 0.5,
        opacity: Math.random() * 0.6 + 0.1,
        speed: Math.random() * 0.3 + 0.1,
        drift: (Math.random() - 0.5) * 0.3
    });
}

var shootingStars = [];

function spawnShootingStar() {
    shootingStars.push({
        x: Math.random() * calcCanvas.width,
        y: Math.random() * calcCanvas.height * 0.5,
        length: Math.random() * 150 + 80,
        speed: Math.random() * 8 + 5,
        opacity: 1,
        angle: Math.PI / 4
    });
}

// Spawn a shooting star every 3-6 seconds
setInterval(function() {
    spawnShootingStar();
}, Math.random() * 3000 + 3000);

var backgroundAnimationIds = {};

function drawBackground(bgCanvas, bgCtx, bgParticles, bgStars, screenId) {
    var screenEl = document.getElementById(screenId);
    if (window.getComputedStyle(screenEl).display === "none") {
        backgroundAnimationIds[screenId] = null;
        return;
    }

    bgCtx.clearRect(0, 0, bgCanvas.width, bgCanvas.height);

    bgParticles.forEach(function(p) {
        p.y -= p.speed;
        p.x += p.drift;
        p.opacity += Math.sin(Date.now() * 0.001 + p.x) * 0.005;
        if (p.y < 0) {
            p.y = bgCanvas.height;
            p.x = Math.random() * bgCanvas.width;
        }
        bgCtx.fillStyle = "rgba(201, 168, 76, " + p.opacity + ")";
        bgCtx.beginPath();
        bgCtx.arc(p.x, p.y, p.size, 0, Math.PI * 2);
        bgCtx.fill();
    });

    for (var i = bgStars.length - 1; i >= 0; i--) {
        var s = bgStars[i];
        s.x += Math.cos(s.angle) * s.speed;
        s.y += Math.sin(s.angle) * s.speed;
        s.opacity -= 0.02;
        if (s.opacity <= 0) { bgStars.splice(i, 1); continue; }
        var sg = bgCtx.createLinearGradient(
            s.x - Math.cos(s.angle) * s.length,
            s.y - Math.sin(s.angle) * s.length,
            s.x, s.y
        );
        sg.addColorStop(0, "rgba(255,255,255,0)");
        sg.addColorStop(1, "rgba(255,255,255," + s.opacity + ")");
        bgCtx.strokeStyle = sg;
        bgCtx.lineWidth = 2;
        bgCtx.beginPath();
        bgCtx.moveTo(s.x - Math.cos(s.angle) * s.length, s.y - Math.sin(s.angle) * s.length);
        bgCtx.lineTo(s.x, s.y);
        bgCtx.stroke();
    }

    backgroundAnimationIds[screenId] = requestAnimationFrame(function() {
        drawBackground(bgCanvas, bgCtx, bgParticles, bgStars, screenId);
    });
}

// Start calc background
drawBackground(calcCanvas, calcCtx, calcParticles, shootingStars, "calculator-screen");
// Hub canvas setup
var hubCanvas = document.getElementById("hub-canvas");
var hubCtx = hubCanvas.getContext("2d");
hubCanvas.width = window.innerWidth;
hubCanvas.height = window.innerHeight;

var hubParticles = [];
for (var i = 0; i < 80; i++) {
    hubParticles.push({
        x: Math.random() * hubCanvas.width,
        y: Math.random() * hubCanvas.height,
        size: Math.random() * 2 + 0.5,
        opacity: Math.random() * 0.6 + 0.1,
        speed: Math.random() * 0.3 + 0.1,
        drift: (Math.random() - 0.5) * 0.3
    });
}

var hubShootingStars = [];
setInterval(function() {
    hubShootingStars.push({
        x: Math.random() * hubCanvas.width,
        y: Math.random() * hubCanvas.height * 0.5,
        length: Math.random() * 150 + 80,
        speed: Math.random() * 8 + 5,
        opacity: 1,
        angle: Math.PI / 4
    });
}, Math.random() * 3000 + 3000);

// Start hub background
drawBackground(hubCanvas, hubCtx, hubParticles, hubShootingStars, "hub-screen");

function drawWater() {
    wctx.clearRect(0, 0, waterCanvas.width, waterCanvas.height);

    // Base water gradient
    var waterGrad = wctx.createLinearGradient(0, 0, 0, waterCanvas.height);
    waterGrad.addColorStop(0, "rgba(100, 160, 210, 0)");
    waterGrad.addColorStop(0.3, "rgba(120, 180, 230, 0.3)");
    waterGrad.addColorStop(0.7, "rgba(140, 200, 245, 0.6)");
    waterGrad.addColorStop(1, "rgba(160, 210, 250, 0.8)");
    wctx.fillStyle = waterGrad;
    wctx.fillRect(0, 0, waterCanvas.width, waterCanvas.height);

    // Ripple lines
    for (var i = 0; i < 12; i++) {
        var y = (i / 12) * waterCanvas.height;
        var wave = Math.sin(waterTime * 0.02 + i * 0.5) * 3;
        wctx.strokeStyle = "rgba(255, 255, 255, " + (0.05 + i * 0.01) + ")";
        wctx.lineWidth = 1;
        wctx.beginPath();
        wctx.moveTo(0, y + wave);
        wctx.lineTo(waterCanvas.width, y + wave);
        wctx.stroke();
    }

    // Golden glow reflection from door
    var glow = wctx.createRadialGradient(
        waterCanvas.width / 2, 0, 0,
        waterCanvas.width / 2, 0, 300
    );
    glow.addColorStop(0, "rgba(201, 168, 76, 0.3)");
    glow.addColorStop(1, "rgba(201, 168, 76, 0)");
    wctx.fillStyle = glow;
    wctx.fillRect(0, 0, waterCanvas.width, waterCanvas.height);

    waterTime++;
    requestAnimationFrame(drawWater);
}

drawWater();

var canvas = document.getElementById("cloud-canvas");
var ctx = canvas.getContext("2d");

// Make canvas fill the screen
canvas.width = window.innerWidth;
canvas.height = window.innerHeight;
console.log("Canvas size:", canvas.width, canvas.height);

function drawCloud(targetCtx, x, y, scale) {
    var s = scale || 1;

    targetCtx.filter = "blur(8px)";

    function puff(px, py, r, opacity) {
        var grad = targetCtx.createRadialGradient(px, py, 0, px, py, r);
        grad.addColorStop(0, "rgba(255, 255, 255, " + opacity + ")");
        grad.addColorStop(0.5, "rgba(240, 248, 255, " + (opacity * 0.5) + ")");
        grad.addColorStop(1, "rgba(200, 230, 255, 0)");
        targetCtx.fillStyle = grad;
        targetCtx.beginPath();
        targetCtx.arc(px, py, r, 0, Math.PI * 2);
        targetCtx.fill();
    }

    // Bottom - barely visible
    puff(x + 50*s,  y + 30*s,  60*s, 0.15);
    puff(x + 130*s, y + 30*s,  65*s, 0.15);
    puff(x + 200*s, y + 30*s,  55*s, 0.15);

    // Middle
    puff(x,         y,         55*s, 0.55);
    puff(x + 70*s,  y - 15*s,  65*s, 0.65);
    puff(x + 145*s, y - 25*s,  70*s, 0.7);
    puff(x + 215*s, y - 10*s,  60*s, 0.6);
    puff(x + 260*s, y + 5*s,   50*s, 0.55);

    // Top - solid but not too bright
    puff(x + 75*s,  y - 55*s,  42*s, 0.8);
    puff(x + 145*s, y - 65*s,  48*s, 0.8);
    puff(x + 210*s, y - 45*s,  38*s, 0.8);

    targetCtx.filter = "none";
}

// Upper sky - just 3 small ones
drawCloud(ctx, 280, 200, 0.3);
drawCloud(ctx, 750, 180, 0.7);
drawCloud(ctx, 1100, 210, 0.6);

// Middle - 2 medium ones on the sides
drawCloud(ctx, 50, 320, 0.9);


// Large horizon clouds
drawCloud(ctx, -100, 620, 1.8);
drawCloud(ctx, 400, 650, 2.0);
drawCloud(ctx, 400, 580, 2.0);
drawCloud(ctx, 900, 630, 1.6);
drawCloud(ctx, 1200, 610, 1.5);
drawCloud(ctx, 1000, 500, 1.5);
drawCloud(ctx, 200, 580, 1.4);
drawCloud(ctx, 1400, 600, 1.4);

document.getElementById("start-btn").addEventListener("click", function() {
    const leftDoor = document.getElementById("door-left-half");
    const rightDoor = document.getElementById("door-right-half");
    const overlay = document.getElementById("transition-overlay");

    // Slide doors apart
    leftDoor.style.transform = "translateX(-100%)";
    rightDoor.style.transform = "translateX(100%)";

    // Zoom in
    document.getElementById("door-scene").style.transition = "transform 1.5s ease-in";
    document.getElementById("door-scene").style.transform = "translateX(-50%) scale(3)";

    // Fade to white
    setTimeout(function() {
        overlay.style.opacity = "1";
    }, 800);

    // Switch to calculator
    setTimeout(function() {
        document.getElementById("home-screen").style.display = "none";
        document.getElementById("hub-screen").style.display = "flex";
        overlay.style.opacity = "0";
        if (!backgroundAnimationIds["hub-screen"]) {
            drawBackground(hubCanvas, hubCtx, hubParticles, hubShootingStars, "hub-screen");
        }
    }, 1800);
});


//grab the calculate button and listen for a click.
document.querySelector(".calculate").addEventListener("click", function() {

    //grab three input values
    const banner = document.getElementById("banner").value;
    const currentPity = document.getElementById("quantity").value;
    const isGuaranteed = document.getElementById("guaranteed").checked;
    const isWeaponGuaranteed = document.getElementById("featured-guaranteed").checked;
    const fatePoints = document.getElementById("fate-points").value;

    if (currentPity === "") {
        document.getElementById("result").innerText = "Please enter current pity!";
        return; // stops the function from continuing
    }

    if (Number(currentPity) < 0 || Number(currentPity) > 90) {
        document.getElementById("result").innerText = "Must enter a number between 0-90!";
        return; // stops the function from continuing
    }

    let url = ``;

    if (banner === "character" || banner === "chronicled") {
        url = `/pity/calculate?banner=${banner}&currentPity=${currentPity}&isGuaranteed=${isGuaranteed}`;
    } else if (banner === "weapon") {
        url = `/pity/calculate?banner=${banner}&currentPity=${currentPity}&fatePoints=${fatePoints}&isWeaponGuaranteed=${isWeaponGuaranteed}`;
    } else if (banner === "standard") {
          url = `/pity/calculate?banner=${banner}&currentPity=${currentPity}`;
    }

    // fetch from backend and display result
        fetch(url)
                    .then(response => response.json())
                    .then(data => {
                        console.log(data.distributions);
                        dataDist = data.distributions;
                        var isGold = data.distributions[1] >= 6.6;

                        document.getElementById("calculator-screen").style.display = "none";
                        document.getElementById("wish-screen").style.display = "flex";

                        startWishAnimation(isGold, data);
                    });
});

var wishAnimationId = null;

function startWishAnimation(isGold, data) {

    if (wishAnimationId) {
        cancelAnimationFrame(wishAnimationId);
    }

    var wishCanvas = document.getElementById("wish-canvas");
    var wishCtx = wishCanvas.getContext("2d");
    wishCanvas.width = window.innerWidth;
    wishCanvas.height = window.innerHeight;

    var time = 0;
    var phase = "comet";
    var cx = wishCanvas.width / 2;
    var cy = wishCanvas.height / 2;

    var primaryColor = isGold ? "255, 200, 50" : "150, 100, 255";
    var secondaryColor = isGold ? "255, 140, 0" : "100, 50, 200";
    var flashColor = isGold ? "255, 220, 100" : "180, 140, 255";

    var cometX = -100;
    var cometY = -50;
    var flashOpacity = 0;
    var flashRadius = 0;
    var shockwaves = [];
    var sparkles = [];
    var explosionParticles = [];

    function spawnSparkles() {
        for (var i = 0; i < 30; i++) {
            var angle = Math.random() * Math.PI * 2;
            var speed = Math.random() * 8 + 2;
            sparkles.push({
                x: cx, y: cy,
                vx: Math.cos(angle) * speed,
                vy: Math.sin(angle) * speed,
                size: Math.random() * 4 + 1,
                opacity: 1,
                color: Math.random() > 0.5 ? primaryColor : "255, 255, 255"
            });
        }
    }

    function spawnFirework(x, y, delay) {
        setTimeout(function() {
            for (var i = 0; i < 60; i++) {
                var angle = (i / 60) * Math.PI * 2;
                var speed = Math.random() * 6 + 2;
                explosionParticles.push({
                    x: x, y: y,
                    vx: Math.cos(angle) * speed,
                    vy: Math.sin(angle) * speed,
                    size: Math.random() * 4 + 1,
                    opacity: 1,
                    decay: Math.random() * 0.015 + 0.01,
                    color: Math.random() > 0.3 ? primaryColor : "255, 255, 255"
                });
            }
        }, delay);
    }

    function drawSky() {
        var skyGrad = wishCtx.createLinearGradient(0, 0, 0, wishCanvas.height);
        skyGrad.addColorStop(0, "#050d1a");
        skyGrad.addColorStop(0.4, "#0d2040");
        skyGrad.addColorStop(0.7, "#1a3a6b");
        skyGrad.addColorStop(1, "#4a90d9");
        wishCtx.fillStyle = skyGrad;
        wishCtx.fillRect(0, 0, wishCanvas.width, wishCanvas.height);

        for (var i = 0; i < 60; i++) {
            var sx = (i * 137.5) % wishCanvas.width;
            var sy = (i * 97.3) % (wishCanvas.height * 0.7);
            var brightness = 0.3 + Math.sin(time * 0.05 + i) * 0.3;
            wishCtx.fillStyle = "rgba(255, 255, 255, " + brightness + ")";
            wishCtx.beginPath();
            wishCtx.arc(sx, sy, 1.5, 0, Math.PI * 2);
            wishCtx.fill();
        }
    }

    function drawComet(x, y) {
        var startX = -100;
        var startY = -50;

        var trailGrad = wishCtx.createLinearGradient(startX, startY, x, y);
        trailGrad.addColorStop(0, "rgba(" + primaryColor + ", 0)");
        trailGrad.addColorStop(0.6, "rgba(" + primaryColor + ", 0.3)");
        trailGrad.addColorStop(1, "rgba(255, 255, 255, 1)");
        wishCtx.strokeStyle = trailGrad;
        wishCtx.lineWidth = 4;
        wishCtx.beginPath();
        wishCtx.moveTo(startX, startY);
        wishCtx.lineTo(x, y);
        wishCtx.stroke();

        wishCtx.lineWidth = 15;
        var glowGrad = wishCtx.createLinearGradient(startX, startY, x, y);
        glowGrad.addColorStop(0, "rgba(" + primaryColor + ", 0)");
        glowGrad.addColorStop(1, "rgba(" + primaryColor + ", 0.15)");
        wishCtx.strokeStyle = glowGrad;
        wishCtx.beginPath();
        wishCtx.moveTo(startX, startY);
        wishCtx.lineTo(x, y);
        wishCtx.stroke();

        for (var i = 0; i < 10; i++) {
            var t = Math.random();
            var px = startX + (x - startX) * t;
            var py = startY + (y - startY) * t;
            px += (Math.random() - 0.5) * 20;
            py += (Math.random() - 0.5) * 20;
            wishCtx.fillStyle = "rgba(255, 255, 255, " + Math.random() * 0.6 + ")";
            wishCtx.beginPath();
            wishCtx.arc(px, py, Math.random() * 2, 0, Math.PI * 2);
            wishCtx.fill();
        }

        var headGrad = wishCtx.createRadialGradient(x, y, 0, x, y, 25);
        headGrad.addColorStop(0, "rgba(255, 255, 255, 1)");
        headGrad.addColorStop(0.4, "rgba(" + flashColor + ", 0.8)");
        headGrad.addColorStop(1, "rgba(" + primaryColor + ", 0)");
        wishCtx.fillStyle = headGrad;
        wishCtx.beginPath();
        wishCtx.arc(x, y, 25, 0, Math.PI * 2);
        wishCtx.fill();
    }

    function drawFlash(opacity, radius) {
        var outerGrad = wishCtx.createRadialGradient(cx, cy, 0, cx, cy, radius * 2);
        outerGrad.addColorStop(0, "rgba(" + flashColor + ", " + opacity * 0.5 + ")");
        outerGrad.addColorStop(0.5, "rgba(" + primaryColor + ", " + opacity * 0.2 + ")");
        outerGrad.addColorStop(1, "rgba(0,0,0,0)");
        wishCtx.fillStyle = outerGrad;
        wishCtx.fillRect(0, 0, wishCanvas.width, wishCanvas.height);

        var innerGrad = wishCtx.createRadialGradient(cx, cy, 0, cx, cy, radius);
        innerGrad.addColorStop(0, "rgba(255, 255, 255, " + opacity + ")");
        innerGrad.addColorStop(0.3, "rgba(" + flashColor + ", " + opacity * 0.9 + ")");
        innerGrad.addColorStop(1, "rgba(" + primaryColor + ", 0)");
        wishCtx.fillStyle = innerGrad;
        wishCtx.beginPath();
        wishCtx.arc(cx, cy, radius, 0, Math.PI * 2);
        wishCtx.fill();
    }

    function drawShockwaves() {
        for (var i = shockwaves.length - 1; i >= 0; i--) {
            shockwaves[i].radius += 12;
            shockwaves[i].opacity -= 0.012;
            if (shockwaves[i].opacity <= 0) {
                shockwaves.splice(i, 1);
                continue;
            }
            wishCtx.strokeStyle = "rgba(" + flashColor + ", " + shockwaves[i].opacity + ")";
            wishCtx.lineWidth = 3;
            wishCtx.beginPath();
            wishCtx.arc(cx, cy, shockwaves[i].radius, 0, Math.PI * 2);
            wishCtx.stroke();
        }
    }

    function drawSparkles() {
        for (var i = sparkles.length - 1; i >= 0; i--) {
            sparkles[i].x += sparkles[i].vx;
            sparkles[i].y += sparkles[i].vy;
            sparkles[i].vy += 0.1;
            sparkles[i].opacity -= 0.02;
            if (sparkles[i].opacity <= 0) {
                sparkles.splice(i, 1);
                continue;
            }
            wishCtx.fillStyle = "rgba(" + sparkles[i].color + ", " + sparkles[i].opacity + ")";
            wishCtx.beginPath();
            wishCtx.arc(sparkles[i].x, sparkles[i].y, sparkles[i].size, 0, Math.PI * 2);
            wishCtx.fill();
        }
    }

    function drawExplosionParticles() {
        for (var i = explosionParticles.length - 1; i >= 0; i--) {
            var p = explosionParticles[i];
            p.x += p.vx;
            p.y += p.vy;
            p.vy += 0.08;
            p.vx *= 0.98;
            p.opacity -= p.decay;
            if (p.opacity <= 0) {
                explosionParticles.splice(i, 1);
                continue;
            }
            wishCtx.strokeStyle = "rgba(" + p.color + ", " + p.opacity * 0.4 + ")";
            wishCtx.lineWidth = p.size * 0.5;
            wishCtx.beginPath();
            wishCtx.moveTo(p.x - p.vx * 3, p.y - p.vy * 3);
            wishCtx.lineTo(p.x, p.y);
            wishCtx.stroke();
            wishCtx.fillStyle = "rgba(" + p.color + ", " + p.opacity + ")";
            wishCtx.beginPath();
            wishCtx.arc(p.x, p.y, p.size, 0, Math.PI * 2);
            wishCtx.fill();
        }
    }

    function animate() {
        wishCtx.clearRect(0, 0, wishCanvas.width, wishCanvas.height);
        drawSky();

        if (phase === "comet") {
            var progress = time / 60;
            cometX = -100 + (cx + 100) * progress;
            cometY = -50 + (cy + 50) * progress;
            drawComet(cometX, cometY);
            if (progress >= 1) {
                phase = "flash";
                spawnSparkles();
                shockwaves.push({ radius: 0, opacity: 1 });
                shockwaves.push({ radius: 30, opacity: 0.8 });
                shockwaves.push({ radius: 60, opacity: 0.6 });
            }
        }

        if (phase === "flash") {
            flashOpacity = Math.min(flashOpacity + 0.08, 1);
            flashRadius = Math.min(flashRadius + 20, 250);
            drawFlash(flashOpacity, flashRadius);
            drawShockwaves();
            drawSparkles();
            drawExplosionParticles();
            if (flashOpacity >= 1 && flashRadius >= 250) {
                phase = "fireworks";
                spawnFirework(cx, cy, 0);
                spawnFirework(cx - 200, cy - 100, 300);
                spawnFirework(cx + 250, cy - 150, 500);
                spawnFirework(cx - 150, cy + 100, 700);
                spawnFirework(cx + 100, cy + 50, 900);
            }
        }

        if (phase === "fireworks") {
            flashOpacity = Math.max(flashOpacity - 0.015, 0);
            if (flashOpacity > 0) drawFlash(flashOpacity, flashRadius);
            drawShockwaves();
            drawExplosionParticles();
            drawSparkles();
        }

        time++;

        var wishScreenEl = document.getElementById("wish-screen");
        if (window.getComputedStyle(wishScreenEl).display === "none") {
            wishAnimationId = null;
            return;
        }

        wishAnimationId = requestAnimationFrame(animate);
    }

    animate();

    setTimeout(function() {
        document.getElementById("wish-result-text").innerText =
            "\nGetting the featured 5-star in the next pull: " + data.distributions[1].toFixed(3) + "%" +
            "\nGetting the featured 5-star in the next 10 pulls: " + data.distributions[10].toFixed(3) + "%" +
            "\n" + data.recommendation;
        document.getElementById("wish-results").style.opacity = "1";
    }, 5000);
}

document.getElementById("wish-screen").addEventListener("click", function() {
    document.getElementById("wish-screen").style.display = "none";
    document.getElementById("chart-screen").style.display = "flex";
    drawRiver();
    drawChart();
    document.getElementById("wish-results").style.opacity = "0";
});

var probabilityChartInstance = null;

function drawChart() {
    var labels = [];
    for (var i = 1; i <= 180; i++) {
        labels.push(i);
    }

    var ctx = document.getElementById("probability-chart").getContext("2d");

    if (probabilityChartInstance) {
        probabilityChartInstance.destroy();
    }

    probabilityChartInstance = new Chart(ctx, {
        type: "line",
        data: {
            labels: labels,
            datasets: [{
                label: "Cumulative % chance of getting featured 5 star",
                data: dataDist.slice(1),
                borderColor: "rgba(201, 168, 76, 1)",
                backgroundColor: "rgba(201, 168, 76, 0.1)",
                fill: true,
                tension: 0.4,
                pointRadius: 0
            }]
        },
        options: {
            responsiveness: true,
            interaction: {
                mode: "index",
                intersect: false
            },
            plugins: {
                legend: {
                    labels: {
                        color: "#ffffff"
                    }
                }
            },
            scales: {
                x: {
                    title: {display: true, text: "Additional pulls spent", color: "#ffffff"},
                    ticks: { color: "#c9a84c" },
                    grid: { color: "rgba(201, 168, 76, 0.15)" }
                },
                y: {
                    title: {display: true, text: "Probability (%)", color: "#ffffff"},
                    min: 0,
                    max: 100,
                    ticks: { color: "#c9a84c" },
                    grid: { color: "rgba(201, 168, 76, 0.15)" }
                }
            }
        }
    });
}

document.getElementById("chart-back-btn").addEventListener("click", function() {
    document.getElementById("chart-screen").style.display = "none";
    document.getElementById("hub-screen").style.display = "flex";
    if (!backgroundAnimationIds["hub-screen"]) {
        drawBackground(hubCanvas, hubCtx, hubParticles, hubShootingStars, "hub-screen");
    }
});

var bossCanvas = document.getElementById("boss-canvas");
var bossCtx = bossCanvas.getContext("2d");
bossCanvas.width = window.innerWidth;
bossCanvas.height = window.innerHeight;

var bossParticles = [];
for (var i = 0; i < 80; i++) {
    bossParticles.push({
        x: Math.random() * bossCanvas.width,
        y: Math.random() * bossCanvas.height,
        size: Math.random() * 2 + 0.5,
        opacity: Math.random() * 0.6 + 0.1,
        speed: Math.random() * 0.3 + 0.1,
        drift: (Math.random() - 0.5) * 0.3
    });
}

var bossShootingStars = [];
setInterval(function() {
    bossShootingStars.push({
        x: Math.random() * bossCanvas.width,
        y: Math.random() * bossCanvas.height * 0.5,
        length: Math.random() * 150 + 80,
        speed: Math.random() * 8 + 5,
        opacity: 1,
        angle: Math.PI / 4
    });
}, Math.random() * 3000 + 3000);

drawBackground(bossCanvas, bossCtx, bossParticles, bossShootingStars, "boss-screen");

var settingsCanvas = document.getElementById("settings-canvas");
var settingsCtx = settingsCanvas.getContext("2d");
settingsCanvas.width = window.innerWidth;
settingsCanvas.height = window.innerHeight;

var settingsParticles = [];
for (var i = 0; i < 80; i++) {
    settingsParticles.push({
        x: Math.random() * settingsCanvas.width,
        y: Math.random() * settingsCanvas.height,
        size: Math.random() * 2 + 0.5,
        opacity: Math.random() * 0.6 + 0.1,
        speed: Math.random() * 0.3 + 0.1,
        drift: (Math.random() - 0.5) * 0.3
    });
}

var settingsShootingStars = [];
setInterval(function() {
    settingsShootingStars.push({
        x: Math.random() * settingsCanvas.width,
        y: Math.random() * settingsCanvas.height * 0.5,
        length: Math.random() * 150 + 80,
        speed: Math.random() * 8 + 5,
        opacity: 1,
        angle: Math.PI / 4
    });
}, Math.random() * 3000 + 3000);

drawBackground(settingsCanvas, settingsCtx, settingsParticles, settingsShootingStars, "settings-screen");

var allCharacters = [];
var iconCache = {};
var selectedWeaponType = "";

document.getElementById("char-name").addEventListener("focus", function() {
    fetch("https://genshin-db-api.vercel.app/api/v5/characters?query=names&matchCategories=true")
    .then(response => response.json())
    .then(data => {
        allCharacters = data;
    });
});

document.getElementById("char-name").addEventListener("input", function() {
    var query = this.value.toLowerCase();
    var matches = allCharacters.filter(function(name) {
        return name.toLowerCase().includes(query);
    });
    var suggestions = document.getElementById("char-suggestions");
    suggestions.innerHTML = "";

    if (query.length === 0) {
        suggestions.style.display = "none";
        return;
    }

    suggestions.style.display = "block";

    matches.slice(0, 8).forEach(function(name) {
        var item = document.createElement("div");
        item.className = "suggestion-item";
        item.innerHTML = "<img id='icon-" + name.replace(/ /g, "-") + "' src=''> " + name;
        item.addEventListener("click", function() {
            document.getElementById("char-name").value = name;
            suggestions.style.display = "none";
            if (iconCache[name]) {
                selectedWeaponType = iconCache[name].weaponType;
            }
        });
        suggestions.appendChild(item);

        // Fetch icon if not cached
        if (iconCache[name]) {
            document.getElementById("icon-" + name.replace(/ /g, "-")).src = iconCache[name].icon;
        } else {
            fetch("https://genshin-db-api.vercel.app/api/v5/characters?query=" + encodeURIComponent(name))
            .then(response => response.json())
            .then(data => {
                iconCache[name] = {
                    icon: data.images.hoyowiki_icon,
                    weaponType: data.weaponText
                };
                var img = document.getElementById("icon-" + name.replace(/ /g, "-"));
                if (img) img.src = iconCache[name].icon;
            });
        }
    });
});


var allWeapons = [];
var weaponCache = {};


document.getElementById("weapon-name").addEventListener("focus", function() {
    if (allWeapons.length > 0) return; // already fetched
    fetch("https://genshin-db-api.vercel.app/api/v5/weapons?query=names&matchCategories=true")
    .then(response => response.json())
    .then(function(names) {
        allWeapons = names;
        // Prefetch all weapon details
        names.forEach(function(name) {
            fetch("https://genshin-db-api.vercel.app/api/v5/weapons?query=" + encodeURIComponent(name))
            .then(response => response.json())
            .then(function(data) {
                weaponCache[name] = {
                    icon: data.images.icon || data.images.mihoyo_icon,
                    weaponType: data.weaponText
                };
            });
        });
    });
});

document.getElementById("weapon-name").addEventListener("input", function() {
    var query = this.value.toLowerCase();
    var matches = allWeapons.filter(function(name) {
        return name.toLowerCase().includes(query);
    });
    var suggestions = document.getElementById("weapon-suggestions");
    suggestions.innerHTML = "";

    if (query.length === 0) {
        suggestions.style.display = "none";
        return;
    }

    suggestions.style.display = "block";

    matches.slice(0, 8).forEach(function(name) {
        var item = document.createElement("div");
        var safeId = "wicon-" + name.replace(/[^a-zA-Z0-9]/g, "-");
        item.className = "suggestion-item";
        item.innerHTML = "<img id='" + safeId + "' src=''> " + name;
        item.addEventListener("click", function() {
            document.getElementById("weapon-name").value = name;
            suggestions.style.display = "none";

        });
        suggestions.appendChild(item);

        // Fetch icon if not cached
        if (weaponCache[name]) {
            document.getElementById(safeId).src = weaponCache[name].icon;
            if (weaponCache[name].weaponType !== selectedWeaponType) {
                item.style.display = "none";
            }
        } else {
            fetch("https://genshin-db-api.vercel.app/api/v5/weapons?query=" + encodeURIComponent(name))
            .then(response => response.json())
            .then(data => {
                weaponCache[name] = {
                    icon: data.images.icon || data.images.mihoyo_icon,
                    weaponType: data.weaponText
                };
                if (data.weaponText !== selectedWeaponType) {
                        item.style.display = "none";
                    }
                var img = document.getElementById(safeId);
                if (img) img.src = weaponCache[name].icon;
            });
        }
    });
});

document.getElementById("add-char-btn").addEventListener("click", function() {
    var charName = document.getElementById("char-name").value;
    if (charName === "") {
        return alert("Please enter character name!!");
    }
    var charLevel = document.getElementById("char-level").value;
    var charConstellation = document.getElementById("char-constellation").value;
    var weaponName = document.getElementById("weapon-name").value;
    var weaponLevel = document.getElementById("char-weapon-level").value;

    if(editCharacterId) {
            fetch('/characters/' + editCharacterId , {
                method: 'PUT',
                headers: {'Content-Type': 'application/json'},
                body: JSON.stringify({
                    id: editCharacterId,
                    name: charName,
                    level: charLevel || 1,
                    constellation: charConstellation,
                    weapon: {
                        name: weaponName,
                        level: weaponLevel || 0,
                    },
                    artifacts: [
                        { id: 0, characterId: editCharacterId, slot: "Flower", name: document.getElementById("artifact-flower").value, level: document.getElementById("artifact-flower-level").value || 0 },
                        { id: 0, characterId: editCharacterId, slot: "Feather", name: document.getElementById("artifact-feather").value, level: document.getElementById("artifact-feather-level").value || 0 },
                        { id: 0, characterId: editCharacterId, slot: "Sands", name: document.getElementById("artifact-sands").value, level: document.getElementById("artifact-sands-level").value || 0 },
                        { id: 0, characterId: editCharacterId, slot: "Goblet", name: document.getElementById("artifact-goblet").value, level: document.getElementById("artifact-goblet-level").value || 0 },
                        { id: 0, characterId: editCharacterId, slot: "Circlet", name: document.getElementById("artifact-circlet").value, level: document.getElementById("artifact-circlet-level").value || 0 }
                    ]
                })
            })
            .then(response => response.json())
            .then(function() {
                editCharacterId = null;
                document.getElementById("add-char-btn").innerText = "Add Character";
                        // clear form
                document.getElementById("char-name").value = "";
                document.getElementById("char-level").value = "";
                document.getElementById("char-constellation").value = "";
                document.getElementById("weapon-name").value = "";
                document.getElementById("char-weapon-level").value = "";
                document.getElementById("artifact-flower").value = "";
                document.getElementById("artifact-flower-level").value = "";
                document.getElementById("artifact-feather").value = "";
                document.getElementById("artifact-feather-level").value = "";
                document.getElementById("artifact-sands").value = "";
                document.getElementById("artifact-sands-level").value = "";
                document.getElementById("artifact-goblet").value = "";
                document.getElementById("artifact-goblet-level").value = "";
                document.getElementById("artifact-circlet").value = "";
                document.getElementById("artifact-circlet-level").value = "";
                loadCharacters();
            })

    } else {
        fetch('/characters/add',  {
                method: 'POST',
                headers: {'Content-Type' : 'application/json'},
                body: JSON.stringify({
                    name: charName,
                    level: charLevel,
                    constellation: charConstellation,
                    weapon: {
                        name: weaponName,
                        level: weaponLevel || 0,
                    },
                    artifacts: [
                        { id: 0, slot: "Flower", name: document.getElementById("artifact-flower").value, level: document.getElementById("artifact-flower-level").value || 0 },
                        { id: 0, slot: "Feather", name: document.getElementById("artifact-feather").value, level: document.getElementById("artifact-feather-level").value || 0 },
                        { id: 0, slot: "Sands", name: document.getElementById("artifact-sands").value, level: document.getElementById("artifact-sands-level").value || 0 },
                        { id: 0, slot: "Goblet", name: document.getElementById("artifact-goblet").value, level: document.getElementById("artifact-goblet-level").value || 0 },
                        { id: 0, slot: "Circlet", name: document.getElementById("artifact-circlet").value, level: document.getElementById("artifact-circlet-level").value || 0 }
                    ]
                })
            })
            .then(response => response.json())
            .then(data => {
                alert("Character saved!");
                document.getElementById("char-name").value = "";
                document.getElementById("char-level").value = "" || 1;
                document.getElementById("char-constellation").value = "";
                document.getElementById("weapon-name").value = "";
                document.getElementById("char-weapon-level").value = "" || 0;
                document.getElementById("artifact-flower").value = "";
                document.getElementById("artifact-flower-level").value = "";
                document.getElementById("artifact-feather").value = "";
                document.getElementById("artifact-feather-level").value = "";
                document.getElementById("artifact-sands").value = "";
                document.getElementById("artifact-sands-level").value = "";
                document.getElementById("artifact-goblet").value = "";
                document.getElementById("artifact-goblet-level").value = "";
                document.getElementById("artifact-circlet").value = "";
                document.getElementById("artifact-circlet-level").value = "";
                loadCharacters();
            });
    }
});

function loadCharacters() {
    fetch('/characters/all')
    .then(response => response.json())
    .then(function(data) {
        var display = document.getElementById("characters-display");
        display.innerHTML = "";

        data.forEach(function(character) {
            var card = document.createElement("div");
            card.className = "character-card";
            card.innerHTML = "<img src='' class='char-card-icon'>" +
                             "<h3>" + character.name + "</h3>" +
                             "<p>Level: " + character.level + "</p>" +
                             "<div class='char-card-overlay'>" +
                             "<button class='edit-char-btn'>Edit</button>" +
                             "<button class='delete-char-btn' data-id='" + character.id + "'>Delete</button>" +
                             "</div>";
            display.appendChild(card);
            card.querySelector(".delete-char-btn").addEventListener("click", function() {
                var id = this.getAttribute("data-id");
                fetch("/characters/" + id, { method: "DELETE" })
                .then(response => {
                    loadCharacters();
                });
            });

            card.querySelector(".edit-char-btn").addEventListener("click", function() {
                    editCharacterId = character.id;
                    document.getElementById("char-name").value = character.name;
                    document.getElementById("char-level").value = character.level;
                    document.getElementById("char-constellation").value = character.constellation || "";
                    document.getElementById("weapon-name").value = character.weapon ? character.weapon.name : "";
                    document.getElementById("char-weapon-level").value = character.weapon ? character.weapon.level : 0;
                    document.getElementById("add-char-btn").innerText = "Save changes";

                    var flowerArtifact = character.artifacts.find(function(a) { return a.slot === "Flower"; });
                    document.getElementById("artifact-flower").value = flowerArtifact ? flowerArtifact.name : "";
                    document.getElementById("artifact-flower-level").value = flowerArtifact ? flowerArtifact.level : "";

                    var featherArtifact = character.artifacts.find(function(a) { return a.slot === "Feather"; });
                    document.getElementById("artifact-feather").value = featherArtifact ? featherArtifact.name : "";
                    document.getElementById("artifact-feather-level").value = featherArtifact ? featherArtifact.level : "";

                    var gobletArtifact = character.artifacts.find(function(a) { return a.slot === "Goblet"; });
                    document.getElementById("artifact-goblet").value = gobletArtifact ? gobletArtifact.name : "";
                    document.getElementById("artifact-goblet-level").value = gobletArtifact ? gobletArtifact.level : "";

                    var sandsArtifact = character.artifacts.find(function(a) { return a.slot === "Sands"; });
                    document.getElementById("artifact-sands").value = sandsArtifact ? sandsArtifact.name : "";
                    document.getElementById("artifact-sands-level").value = sandsArtifact ? sandsArtifact.level : "";

                    var circletArtifact = character.artifacts.find(function(a) { return a.slot === "Circlet"; });
                    document.getElementById("artifact-circlet").value = circletArtifact ? circletArtifact.name : "";
                    document.getElementById("artifact-circlet-level").value = circletArtifact ? circletArtifact.level : "";
            });

            fetch("https://genshin-db-api.vercel.app/api/v5/characters?query=" + encodeURIComponent(character.name))
            .then(response => response.json())
            .then(function(charData) {
                var img = card.querySelector(".char-card-icon");
                if (img && charData.images) img.src = charData.images.hoyowiki_icon;
            });

        });
    });

}

var slideNo = 0;

function moveSlider() {
    document.getElementById("slider-track").style.transform = "translateX(-" + (slideNo * 33.333) + "%)";
}

var autoScroll = setInterval(function() {
    moveSlider();
    if (slideNo < 2) {
        slideNo++;
    } else {
        slideNo = 0;
    }
}, 5000);

document.getElementById("right-btn").addEventListener("click", function() {
    clearInterval(autoScroll)
    if (slideNo < 2) {
            slideNo++;
        } else {
            slideNo = 0;
        }
    moveSlider();
});

document.getElementById("left-btn").addEventListener("click", function() {
    clearInterval(autoScroll)
    if (slideNo > 0) {
            slideNo--;
        } else {
            slideNo = 2;
        }
    moveSlider();
});

var allArtifactSets = [];
var artifactSetCache = {};

function fetchArtifactSetNames(callback) {
    if (allArtifactSets.length > 0) {
        callback();
        return;
    }
    fetch("https://genshin-db-api.vercel.app/api/v5/artifacts?query=names&matchCategories=true")
    .then(response => response.json())
    .then(function(names) {
        allArtifactSets = names;
        callback();
    });
}

function setupArtifactSearch(inputId, suggestionsId, slotKey) {
    var inputEl = document.getElementById(inputId);
    var suggestionsEl = document.getElementById(suggestionsId);

    inputEl.addEventListener("focus", function() {
        fetchArtifactSetNames(function() {});
    });

    inputEl.addEventListener("input", function() {
        var query = this.value.toLowerCase();
        var matches = allArtifactSets.filter(function(setName) {
            return setName.toLowerCase().includes(query);
        });

        suggestionsEl.innerHTML = "";

        if (query.length === 0) {
            suggestionsEl.style.display = "none";
            return;
        }

        suggestionsEl.style.display = "block";

        matches.slice(0, 8).forEach(function(setName) {
            var safeId = "aicon-" + slotKey + "-" + setName.replace(/[^a-zA-Z0-9]/g, "-");
            var item = document.createElement("div");
            item.className = "suggestion-item";
            item.innerHTML = "<img id='" + safeId + "' src=''> " + setName;

            item.addEventListener("click", function() {
                if (artifactSetCache[setName]) {
                    inputEl.value = artifactSetCache[setName][slotKey + "Name"];
                }
                suggestionsEl.style.display = "none";
            });

            suggestionsEl.appendChild(item);

            if (artifactSetCache[setName]) {
                var img = document.getElementById(safeId);
                if (img) img.src = artifactSetCache[setName][slotKey + "Icon"];
            } else {
                fetch("https://genshin-db-api.vercel.app/api/v5/artifacts?query=" + encodeURIComponent(setName))
                .then(response => response.json())
                .then(function(data) {
                    artifactSetCache[setName] = {
                        flowerName: data.flower.name,
                        plumeName: data.plume.name,
                        sandsName: data.sands.name,
                        gobletName: data.goblet.name,
                        circletName: data.circlet.name,
                        flowerIcon: data.images.flower,
                        plumeIcon: data.images.plume,
                        sandsIcon: data.images.sands,
                        gobletIcon: data.images.goblet,
                        circletIcon: data.images.circlet
                    };
                    var img = document.getElementById(safeId);
                    if (img) img.src = artifactSetCache[setName][slotKey + "Icon"];
                });
            }
        });
    });
}

setupArtifactSearch("artifact-flower", "flower-suggestions", "flower");
setupArtifactSearch("artifact-feather", "feather-suggestions", "plume");
setupArtifactSearch("artifact-sands", "sands-suggestions", "sands");
setupArtifactSearch("artifact-goblet", "goblet-suggestions", "goblet");
setupArtifactSearch("artifact-circlet", "circlet-suggestions", "circlet");