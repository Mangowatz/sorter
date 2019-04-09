package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;

/**
 * Created by Aryeh on 11/21/17.
 */



@Autonomous(name="AutoRR", group="Autonomus")
@Disabled
public class AutonomusRR extends LinearOpMode {

    public final double slotWidth = 7.63;
    private ColorSensor myColorSensor;
    private Color ballColor;
    private final Color teamColor = Color.RED;





    @Override
    public void runOpMode() throws InterruptedException {

        //Setup Hardware
        hardwareSetup();



        waitForStart();

        //Sensor checks right ball
        ballColor = getColor();

        knockDownBall();





    }




    private void hardwareSetup() {
        myColorSensor = hardwareMap.get(ColorSensor.class, "sence");
    }

    private void knockDownBall() {
        //Knock down other team ball
        if(ballColor == teamColor) {
            //Move away from this ball, ie left



            auto.move(-20,0.3);


            raiseSideStick();
            auto.move(20,0.3);

        }
        else {
            //Move towards this ball ie right
            auto.move(20,0.3);

            raiseSideStick();

            auto.move(-20,0.3);

        }
    }
    public Color getColor() {

        telemetry.addData("red: ", myColorSensor.red());
        telemetry.addData("blue: ", myColorSensor.blue());


        if (myColorSensor.red() > myColorSensor.blue()) return Color.RED;
        else return  Color.BLUE;


    }



}
