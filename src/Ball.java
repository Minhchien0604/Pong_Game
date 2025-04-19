public class Ball {
    public Rect rect;
    public Rect LeftPaddle, RightPaddle;
    public Text leftScoreText, rightScoreText;

    private double vy = 100;
    private double vx = -200;


    public Ball (Rect rect, Rect LeftPaddle, Rect RightPaddle, Text leftScoreText, Text rightScoreText){
        this.rect = rect;
        this.LeftPaddle = LeftPaddle;
        this.RightPaddle = RightPaddle;
        this.leftScoreText = leftScoreText;
        this.rightScoreText = rightScoreText;
    }


    public double calculateNewVelocityAngle (Rect paddle){
        double relativeIntersectY = (paddle.y + (paddle.height / 2.0) - (this.rect.y + (this.rect.height / 2.0)));
        double normalIntersectY = relativeIntersectY / (paddle.height / 2.0);
        double theta = normalIntersectY * Constants.MAX_ANGLE;

        return Math.toRadians(theta);
    }
    public void update (double dt){
        if (vy >= 0.0){
            if (this.rect.y + (vy  * dt) + this.rect.height > Constants.SCREEN_HEIGHT - Constants.INSETS_BOTTOM){
                vy *= -1.0;
            }
        }else if (vy < 0.0){
            if (this.rect.y + (vy  * dt) < Constants.TOOLBAR_HEIGHT){
                vy *= -1.0;
            }
        }

        if (vx < 0.0){
            if (this.rect.x + (vx * dt) < LeftPaddle.x + LeftPaddle.width){
                if (this.rect.y + (vy  * dt) > LeftPaddle.y &&
                this.rect.y + (vy  * dt) + this.rect.height < LeftPaddle.y + LeftPaddle.height){
                    double theta = calculateNewVelocityAngle(LeftPaddle);
                    double newVx = Math.abs(Math.cos (theta)) * Constants.BALL_SPEED;
                    double newVy = (-Math.sin (theta)) * Constants.BALL_SPEED;

                    double oldSign = Math.signum(vx);
                    this.vx = newVx * (-1.0 * oldSign);
                    this.vy = newVy;
                }
            }
        }else if (vx >= 0.0){
            if (this.rect.x + (vx * dt) + rect.width > RightPaddle.x){
                if (this.rect.y + (vy * dt) > RightPaddle.y &&
                    this.rect.y + (vy * dt) + this.rect.height < RightPaddle.y + RightPaddle.height){
                    double theta = calculateNewVelocityAngle(RightPaddle);
                    double newVx = Math.abs(Math.cos (theta)) * Constants.BALL_SPEED;
                    double newVy = (-Math.sin (theta)) * Constants.BALL_SPEED;

                    double oldSign = Math.signum(vx);
                    this.vx = newVx * (-1.0 * oldSign);
                    this.vy = newVy;
                }
            }
        }

        this.rect.y += vy  * dt;
        this.rect.x += vx  * dt;

        if (this.rect.x + this.rect.width < LeftPaddle.x){
            int rightScore = Integer.parseInt(leftScoreText.text);
            rightScore++;
            rightScoreText.text = "" + rightScore;

            this.rect.x = Constants.SCREEN_WIDTH / 2.0;
            this.rect.y = Constants.SCREEN_HEIGHT / 2.0;
            this.vx = -150;
            this.vy = 10;

            if (rightScore >= Constants.WIN_SCORE){
                rightScoreText.text = "0";
                Main.changeState(2);
            }
        }
        else if (this.rect.x > RightPaddle.x + RightPaddle.width){
            int leftScore = Integer.parseInt(leftScoreText.text);
            leftScore++;
            leftScoreText.text = "" + leftScore;

            this.rect.x = Constants.SCREEN_WIDTH / 2.0;
            this.rect.y = Constants.SCREEN_HEIGHT / 2.0;
            this.vx = 200;
            this.vy = 100;
            if (leftScore > Constants.WIN_SCORE){
                Main.changeState(2);
            }
        }
    }
}
