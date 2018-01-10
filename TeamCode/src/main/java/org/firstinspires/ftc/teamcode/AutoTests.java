package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;

/**
 * Created by Aryeh on 1/9/18.
 */

@Autonomous(name="AutoTests", group="Autonomus")
@Disabled
public class AutoTests extends LinearOpMode {
    public final double slotWidth = 7.63;
    protected Color teamColor;
    protected VuforiaLocalizer vuforia;
    protected VuforiaTrackables relicTrackables;
    protected VuforiaTrackable relicTemplate;
    protected RelicRecoveryVuMark vuMark;

    protected Servo leftBlockGrabber;
    protected Servo rightBlockGrabber;
    protected Servo frontStickServo;
    protected Servo frontAngleServo;
    protected Servo relicGrabberServo;
    //protected Servo frontStickServo;

    //protected Servo sideStick;
    protected DcMotor motorFrontLeft;
    protected DcMotor motorFrontRight;
    protected DcMotor motorBackLeft;
    protected DcMotor motorBackRight;
    protected DcMotor blockRaiser;
    protected DcMotor relicExtender;
    protected DcMotor sideStickMotor;
    protected DcMotor relicRaiser;

    protected ColorSensor myColorSensor;
    protected DigitalChannel frontStickButton;
    protected DigitalChannel relicRaiserMax;



    protected Color ballColor;
    protected MecanumAutonomus auto;
    protected MotorSpeeds manual;

    @Override
    public void runOpMode() throws InterruptedException {
        hardwareSetup();
        auto = new MecanumAutonomus(motorFrontLeft,motorFrontRight,motorBackLeft,motorBackRight);
        waitForStart();
        auto.move(360,0.5);
        auto.move(20,0,0.5);
        auto.move(0,20,0.5);

    }

    protected void hardwareSetup() {
        //Servos
        leftBlockGrabber = hardwareMap.servo.get("LBS");
        rightBlockGrabber = hardwareMap.servo.get("RBS");
        frontAngleServo = hardwareMap.servo.get("FAS");
        relicGrabberServo = hardwareMap.servo.get("ReGS");
        frontStickServo = hardwareMap.servo.get("FSS");


        //Motors
        motorFrontLeft = hardwareMap.dcMotor.get("fl");
        motorFrontRight = hardwareMap.dcMotor.get("fr");
        motorBackLeft = hardwareMap.dcMotor.get("bl");
        motorBackRight = hardwareMap.dcMotor.get("br");
        blockRaiser = hardwareMap.dcMotor.get("BlR");
        sideStickMotor = hardwareMap.dcMotor.get("SSM");
        relicExtender = hardwareMap.dcMotor.get("RE");
        relicRaiser = hardwareMap.dcMotor.get("RR");

        //Others
        myColorSensor = hardwareMap.get(ColorSensor.class, "sence");
        frontStickButton = hardwareMap.get(DigitalChannel.class, "FSB");
        frontStickButton.setMode(DigitalChannel.Mode.INPUT);
        relicRaiserMax = hardwareMap.get(DigitalChannel.class, "RRM");
        relicRaiserMax.setMode(DigitalChannel.Mode.INPUT);

    }
}
