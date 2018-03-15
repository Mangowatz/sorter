package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
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
 * Created by Aryeh on 1/31/18.
 */

@Autonomous(name="90 Degrees Movement", group="Autonomus")
public class TestServo extends LinearOpMode {


    public final double slotWidth = 7.63;
    public final double movementSpeed = 0.2;
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
    protected MotorSpeeds2 manual;

    @Override
    public void runOpMode() throws InterruptedException {
        hardwareSetup();



        waitForStart();

        leftBlockGrabber.setPosition(0.4);
        rightBlockGrabber.setPosition(0.03);

        //auto = new MecanumAutonomus(motorFrontLeft,motorFrontRight,motorBackLeft,motorBackRight);
        //auto.move(25,0,0.3);
        while(opModeIsActive())
        {
            telemetry.addData("Left block servo", leftBlockGrabber.getPosition());
            telemetry.addData("Right block servo", rightBlockGrabber.getPosition());
            telemetry.update();
        }

    }

    protected void hardwareSetup() {
        //Servos
        leftBlockGrabber = hardwareMap.servo.get("RBS");
        rightBlockGrabber = hardwareMap.servo.get("LBS");
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

        sideStickMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        relicRaiser.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        blockRaiser.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        motorFrontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        motorFrontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        motorBackLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        motorBackRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);


        //Others
        myColorSensor = hardwareMap.get(ColorSensor.class, "sence");
        frontStickButton = hardwareMap.get(DigitalChannel.class, "FSB");
        frontStickButton.setMode(DigitalChannel.Mode.INPUT);
        relicRaiserMax = hardwareMap.get(DigitalChannel.class, "RRM");
        relicRaiserMax.setMode(DigitalChannel.Mode.INPUT);


        //Set braking
        sideStickMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        relicRaiser.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);


    }
}
