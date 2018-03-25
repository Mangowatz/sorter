package org.firstinspires.ftc.teamcode;

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
 * Created by Aryeh on 1/8/18.
 */

public abstract class AutonomousAbstract extends LinearOpMode {
    //7.63
    public double slotWidth = 8;
    public final double movementSpeed = 0.8;
    protected VuforiaLocalizer vuforia;
    protected VuforiaTrackables relicTrackables;
    protected VuforiaTrackable relicTemplate;
    protected RelicRecoveryVuMark vuMark;

    protected Servo leftBlockGrabber;
    protected Servo rightBlockGrabber;
    protected Servo frontStickServo;
    protected Servo frontAngleServo;
    protected Servo relicGrabberServo;
    protected Servo blockPusher;
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
        blockPusher.setPosition(0.0);


        //init recovery mark
        vuMark = RelicRecoveryVuMark.UNKNOWN;


        waitForStart();

        //Run once start
        relicTrackables.activate();

        //Create moving class
        auto = new MecanumAutonomus(motorFrontLeft,motorFrontRight,motorBackLeft,motorBackRight);
        manual = new MotorSpeeds2(motorFrontLeft,motorFrontRight,motorBackLeft,motorBackRight);



        //To get out of the way for lowering

        MecanumAutonomus.moveDcMotorEncoded(blockRaiser,0.7,-100);

        openBlockClaw();
        sleep(200);



        //lower grabber slightly
        MecanumAutonomus.moveDcMotorEncoded(blockRaiser,0.7,1800);


        //Close two servos around block

        closeBlockClaw();





        //2240
        //Only pickup 400 here rest after

        MecanumAutonomus.moveDcMotorEncoded(blockRaiser,0.7,-800);

        lookForVuMark(30);

        /*
        if(vuMark == RelicRecoveryVuMark.UNKNOWN) {
            //Rotate sideways to look for vu
            auto.move(20,0.3);

            lookForVuMark(30);

            //move back after looking for vu
            auto.move(-20,0.3);

        }*/





        //Lower side stick
        lowerSideStick();


        //moveDcMotorEncoded(relicExtender,0.3,-1800);
        //relicExtender.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        //getClearanceFromRelicExtender();


        //MecanumAutonomus.moveDcMotorEncoded(blockRaiser,0.9,-400);


        //blockRaiser.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        //blockRaiser.setPower(-0.6);
        //sleep(1000);
        //blockRaiser.setPower(0.0);


        //sleep(1000);
















        //Sensor checks right ball
        ballColor = getColor();

        knockDownBall();




        //Raise side stick

        //sideStick.setPosition(0.5);



        moveToFrontOfGlyphHolder();

        //Move right diff distances based on vu mark


        moveToCorrectSlot();

        //Lower block raiser
        MecanumAutonomus.moveDcMotorEncoded(blockRaiser,0.8,1000,this);


        //blockRaiser.setMode(DcMotor.RunMode.RUN_USING_ENCODER);


        openBlockClawFully();

        //Move fowards slightly so block is in row
        auto.move(-6.0,0.0,movementSpeed);

        //Put down block


        auto.move(6.0,0.0,movementSpeed);

        //rollBackRelicExtender();
        stop();
        /*
        Shouldn't need since lines up perfectly
        closeBlockClaw();

        auto.move(-8.5,0.0,0.7);

        openBlockClaw();
        auto.move(2,0.0,1.0);
        */

        //while (opModeIsActive()) {
        //Run from time pressed play till end

        //}
    }
    protected void stopEverything() {
        manual.myStop();
        blockRaiser.setPower(0.0);
        relicExtender.setPower(0.0);
        sideStickMotor.setPower(0.0);
    }

    protected void lowerSideStick() {
        //MecanumAutonomus.moveDcMotorEncoded(sideStickMotor,0.2,-440);
        MecanumAutonomus.moveDcMotorEncoded(sideStickMotor,0.2,-500,1000);
    }
    protected void raiseSideStick() {

        MecanumAutonomus.moveDcMotorEncoded(sideStickMotor,0.2,390,this);
        sideStickMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    //Move to be in front of right slot
    abstract void moveToFrontOfGlyphHolder();
    abstract void moveToCorrectSlot();
    abstract Color getTeamColor();

    protected RelicRecoveryVuMark checkForVUMark() {
        RelicRecoveryVuMark vuMark = RelicRecoveryVuMark.from(relicTemplate);

        switch (vuMark) {
            case UNKNOWN:
                telemetry.addData("No VU Mark Visible","");
                return RelicRecoveryVuMark.UNKNOWN;

            case LEFT:
                telemetry.addData("Left VU Mark Visible","");
                return RelicRecoveryVuMark.LEFT;

            case CENTER:
                telemetry.addData("Center VU Mark Visible","");
                return RelicRecoveryVuMark.CENTER;

            case RIGHT:
                telemetry.addData("Right VU Mark Visible","");
                return RelicRecoveryVuMark.RIGHT;

        }
        return RelicRecoveryVuMark.UNKNOWN;
    }

    protected void vuMarkSetup() {
        //Relic setup
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters(cameraMonitorViewId);
        parameters.vuforiaLicenseKey = "AcBbjLb/////AAAAGUE95xAvpEQzuMrgrlfyB3pATdcFCbND7+gUm8qqRk/0O4nOKmK9NdEKNpY+27LaHPFRVrULXyQqqaUr9Vm7gtyncPMBYZo3FAfYcXboDQXtEdBOHG3HLYmczuv3/k0MUQ7PDtuNj9KlQF84vB3ZpMQCfbqMVaXGdn1rLTCiOFtDdLhK9QGC315hgzV9VsbJ0wzwwlDabJBq5+aFNFhw4gF3YS97FG6fHiMGIJ4piGQJXwh+jKuV0A7ZHoqysxs2xsV9iUSOEbsawRXo/Lg1aXw1B8SZ6T6P6oqdvnwFYXSpGT0LKkNi9K6/7g/MiFkJLcYFpVNxz6i9gueh5c4ZKaYMHwVIbW6x/cmVFSQJSPZd";
        parameters.cameraDirection = VuforiaLocalizer.CameraDirection.FRONT;
        this.vuforia = ClassFactory.createVuforiaLocalizer(parameters);
        relicTrackables = this.vuforia.loadTrackablesFromAsset("RelicVuMark");
        relicTemplate = relicTrackables.get(0);
        relicTemplate.setName("relicVuMarkTemplate");
    }
    protected void lookForVuMark(int times) {
        //Look for vu mark
        int counter = 0;
        while(vuMark == RelicRecoveryVuMark.UNKNOWN && !isStopRequested()) {
            vuMark = checkForVUMark();
            counter++;
            sleep(50);
            telemetry.addData("Checking for VU Mark", true);
            telemetry.update();
            if(counter > times) {
                //None found, set to center
                vuMark = RelicRecoveryVuMark.UNKNOWN;
                break;
            }
        }
        telemetry.addData("vu mark value", vuMark);
        telemetry.update();
    }
    protected void getClearanceFromRelicExtender() {
        /*
        relicExtender.setPower(-0.5);
        sleep(800);
        relicExtender.setPower(0.0);*/

        MecanumAutonomus.moveDcMotorEncoded(relicExtender,0.6,-800);
    }
    private void rollBackRelicExtender() {
        MecanumAutonomus.moveDcMotorEncoded(relicExtender,0.6,800);
    }
    protected void hardwareSetup() {
        //Servos
        leftBlockGrabber = hardwareMap.servo.get("RBS");
        rightBlockGrabber = hardwareMap.servo.get("LBS");
        frontAngleServo = hardwareMap.servo.get("FAS");
        relicGrabberServo = hardwareMap.servo.get("ReGS");
        frontStickServo = hardwareMap.servo.get("FSS");
        blockPusher = hardwareMap.servo.get("BLOCKPUSH");


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
        frontFrontStickButton = hardwareMap.get(DigitalChannel.class, "FFSB");
        frontFrontStickButton.setMode(DigitalChannel.Mode.INPUT);
        relicRaiserMax = hardwareMap.get(DigitalChannel.class, "RRM");
        relicRaiserMax.setMode(DigitalChannel.Mode.INPUT);


        //Set braking
        sideStickMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        relicRaiser.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);


    }
    protected void openBlockClaw() {
        //Put servos in closed position
        rightBlockGrabber.setPosition(0.7);
        leftBlockGrabber.setPosition(0.6);
    }
    protected void openBlockClawFully() {
        rightBlockGrabber.setPosition(0.5);
        leftBlockGrabber.setPosition(0.8);
    }
    protected void closeBlockClaw() {
        rightBlockGrabber.setPosition(1.0);
        leftBlockGrabber.setPosition(0.4);
    }



    protected void knockDownBall() {
        //Knock down other team ball
        if(ballColor == getTeamColor()) {
            //Move away from this ball, ie left



            auto.move(-20,0.2);


            raiseSideStick();
            auto.move(20,0.2);

        }
        else {
            //Move towards this ball ie right
            auto.move(20,0.2);

            raiseSideStick();

            auto.move(-20,0.2);

        }
    }

    public Color getColor() {

        telemetry.addData("red: ", myColorSensor.red());
        telemetry.addData("blue: ", myColorSensor.blue());


        if (myColorSensor.red() > myColorSensor.blue()) return Color.RED;
        else return  Color.BLUE;


    }



}
