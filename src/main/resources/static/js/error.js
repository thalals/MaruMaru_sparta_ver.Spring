$(document).keydown(function (event) {
    if (event.keyCode == 32) {
        event.preventDefault();
    }
});


var canvas = document.getElementById('canvas');
var ctx = canvas.getContext('2d');

canvas.width = window.innerWidth - 100;
canvas.height = window.innerHeight - 100;


var img1 = new Image();
img1.src = '/static/ozizo/dog.png'


var maru = {
    x: 90,
    y: 200,
    width: 40,
    height: 40,
    draw() {
        // ctx.fillStyle = 'green';
        // ctx.fillRect(this.x, this.y, this.width, this.height);
        ctx.drawImage(img1, this.x, this.y, this.width, this.height);

    }
}

maru.draw()

// 1초에 60번 +1 해주기

var img2 = new Image();
var randomNum = Math.floor(Math.random() * 4);

if (randomNum == 0) {
    img2.src = '/static/ozizo/hm.png';
} else if (randomNum == 1) {
    img2.src = '/static/ozizo/so.png';
} else if (randomNum == 2) {
    img2.src = '/static/ozizo/sy.png';
} else {
    img2.src = '/static/ozizo/ju.png';
}


class Obstacle {
    constructor() {
        this.x = 600;
        this.y = 200;
        this.width = 40;
        this.height = 40;
    }

    draw() {
        // ctx.fillStyle = 'red';
        // ctx.fillRect(this.x, this.y, this.width, this.height);
        ctx.drawImage(img2, this.x, this.y, this.width, this.height);
    }
}

var timer = 0;
var obstacleMany = [];
var jumpTimer = 0;
var animation;

function moveToLeft() {
    animation = requestAnimationFrame(moveToLeft);
    timer++;

    ctx.clearRect(0, 0, canvas.width, canvas.height);

    var showObstacle = Math.floor(Math.random() * (230 - 150)) + 150;
    if (timer % showObstacle === 0) {
        var obstacle = new Obstacle();
        obstacleMany.push(obstacle);
    }
    obstacleMany.forEach((e, i, o) => {
        // x 좌표 0 미만이면 제거
        if (e.x < 0) {
            o.splice(i, 1)
        }
        e.x -= 3;

        gameOver(maru, e);
        e.draw();
    });

    if (jump == true) {
        maru.y -= 4;
        jumpTimer++;
    }

    if (jump == false) {
        if (maru.y < 200) {
            maru.y += 4;
        }
    }

    if (jumpTimer > 50) {
        jump = false;
        jumpTimer = 0;
    }

    maru.draw();
}

moveToLeft();


// 충돌 설정
function gameOver(maru, obstacle) {
    var meetX = obstacle.x - (maru.x + maru.width);
    var meetY = obstacle.y - (maru.y + maru.height);
    if (meetX < 0 && meetY < 0) {
        ctx.clearRect(0, 0, canvas.width, canvas.height);
        cancelAnimationFrame(animation);
    }
};

var jump = false;
document.addEventListener('keydown', function (e) {
    if (e.code === 'Space') {
        jump = true;
    }
});