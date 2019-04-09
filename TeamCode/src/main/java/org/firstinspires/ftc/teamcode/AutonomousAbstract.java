package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;

import com.qualcomm.robotcore.hardware.DigitalChannel;


import org.firstinspires.ftc.robotcore.external.ClassFactory;


/**
 * Created by Aryeh on 1/8/18.
 */

public abstract class AutonomousAbstract extends LinearOpMode {
    //7.63
    public double slotWidth = 8;
    public final double movementSpeed = 0.8;

    protected ColorSensor myColorSensor;
    protected DigitalChannel frontStickButton;
    protected DigitalChannel relicRaiserMax;
    protected DigitalChannel frontFrontStickButton;


    protected Color ballColor;
    protected MecanumAutonomus auto;
    protected MotorSpeeds2 manual;


    @Override
    public void runOpMode() throws InterruptedException {
        //Init here
        vuMarkSetup();


        //Setup Hardware
        hardwareSetup();


        waitForStart();

        //Run once start


        //Sensor checks right ball
        ballColor = getColor();

        knockDownBall();

        protected void stopEverything () {
            manual.myStop();

        }

        abstract Color getTeamColor ();


        protected void hardwareSetup () {

            //Others
            myColorSensor = hardwareMap.get(ColorSensor.class, "sence");
            frontStickButton = hardwareMap.get(DigitalChannel.class, "FSB");
            frontStickButton.setMode(DigitalChannel.Mode.INPUT);
            frontFrontStickButton = hardwareMap.get(DigitalChannel.class, "FFSB");
            frontFrontStickButton.setMode(DigitalChannel.Mode.INPUT);
            relicRaiserMax = hardwareMap.get(DigitalChannel.class, "RRM");
            relicRaiserMax.setMode(DigitalChannel.Mode.INPUT);


        }


        protected void knockDownBall () {
            //Knock down other team ball
            if (ballColor == getTeamColor()) {
                //Move away from this ball, ie left


                auto.move(-20, 0.2);


                raiseSideStick();
                auto.move(20, 0.2);

            }
        }

        public Color getColor () {

            telemetry.addData("red: ", myColorSensor.red());
            telemetry.addData("blue: ", myColorSensor.blue());


            if (myColorSensor.red() > myColorSensor.blue()) return Color.RED;
            else return Color.BLUE;


        }


    }
}

