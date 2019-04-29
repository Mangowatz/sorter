package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;

/**
 * Created by Aryeh on 1/8/18.
 *Adapted by Zack
 */

public abstract class AutonomousAbstract extends LinearOpMode {

    protected ColorSensor myColorSensor;

    protected Color ballColor;


    @Override
    public void runOpMode() throws InterruptedException {

        //Setup Hardware
        hardwareSetup();
        waitForStart();

        //Sensor checks right ball
        ballColor = getColor();


        abstract Color getTeamColor();

        protected void hardwareSetup(){
            //Others
            myColorSensor = hardwareMap.get(ColorSensor.class, "sence");
        }


        protected void knockDownBall () {
            //Knock down other team ball
            if (ballColor == getTeamColor()) {

        }

        public Color getColor(){

            telemetry.addData("red: ", myColorSensor.red());
            telemetry.addData("blue: ", myColorSensor.blue());

            if (myColorSensor.red() > myColorSensor.blue()) return Color.RED;
            else return Color.BLUE;


        }


    }
}

