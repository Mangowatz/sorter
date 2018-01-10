package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

/**
 * Created by Aryeh on 1/8/18.
 */

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
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



@Autonomous(name="AutoRR2", group="Autonomus")
@Disabled
public class AutoRR extends LinearOpMode {



    //private Servo sideStick;
    private DcMotor motorFrontLeft;
    private DcMotor motorFrontRight;
    private DcMotor motorBackLeft;


    @Override
    public void runOpMode() throws InterruptedException {
        //Init here

        motorFrontLeft = hardwareMap.dcMotor.get("fl");
        motorFrontRight = hardwareMap.dcMotor.get("fr");
        motorBackLeft = hardwareMap.dcMotor.get("bl");

        waitForStart();
        while(opModeIsActive()) {
            motorFrontRight.setPower(1.0);
            motorBackLeft.setPower(1.0);
            motorFrontLeft.setPower(1.0);
        }



    }





}
