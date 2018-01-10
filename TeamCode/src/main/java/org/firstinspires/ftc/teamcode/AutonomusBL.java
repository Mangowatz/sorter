package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;

/**
 * Created by Aryeh on 11/21/17.
 */


@Autonomous(name="AutoBL", group="Autonomus")
@Disabled
public class AutonomusBL extends LinearOpMode {

    public final double slotWidth = 7.63;
    private final Color teamColor = Color.BLUE;
    private VuforiaLocalizer vuforia;
    private VuforiaTrackables relicTrackables;
    private VuforiaTrackable relicTemplate;
    private RelicRecoveryVuMark vuMark;

    private Servo leftBlockGrabber;
    private Servo rightBlockGrabber;
    private Servo frontStickServo;

    //private Servo sideStick;
    private DcMotor motorFrontLeft;
    private DcMotor motorFrontRight;
    private DcMotor motorBackLeft;
    private DcMotor motorBackRight;
    private DcMotor blockRaiser;
    private DcMotor relicExtender;
    private DcMotor sideStickMotor;

    private ColorSensor myColorSensor;
    private DigitalChannel frontStickButton;

    private Color ballColor;
    private MecanumAutonomus auto;
    private MotorSpeeds manual;


    @Override
    public void runOpMode() throws InterruptedException {
        //Init here
        vuMarkSetup();




        //Setup Hardware
        hardwareSetup();


        //init recovery mark
        vuMark = RelicRecoveryVuMark.UNKNOWN;


        waitForStart();

        //Run once start
        relicTrackables.activate();

        //Create moving objects
        auto = new MecanumAutonomus(motorFrontLeft,motorFrontRight,motorBackLeft,motorBackRight);
        manual = new MotorSpeeds(motorFrontLeft,motorFrontRight,motorBackLeft,motorBackRight);


        //To get out of the way for lowering
        openBlockClaw();



        //lower grabber slightly
        MecanumAutonomus.moveDcMotorEncoded(blockRaiser,0.8,400);


        //Close two servos around block

        closeBlockClaw();





        //2240
        //Only pickup 400 here rest after

        MecanumAutonomus.moveDcMotorEncoded(blockRaiser,0.8,-400);


        //Rotate sideways to look for vu
        auto.move(20,0.6);

        lookForVuMark();

        //move back after looking for vu
        auto.move(-20,0.6);




        //Lower side stick
        lowerSideStick();


        //moveDcMotorEncoded(relicExtender,0.3,-1800);
        //relicExtender.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        getClearanceFromRelicExtender();


        MecanumAutonomus.moveDcMotorEncoded(blockRaiser,0.8,-1840);


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
        MecanumAutonomus.moveDcMotorEncoded(blockRaiser,0.8,2200);

        //blockRaiser.setMode(DcMotor.RunMode.RUN_USING_ENCODER);



        //Move fowards slightly so block is in row
        auto.move(-7.0,0.0,0.7);

        //Put down block
        openBlockClaw();

        auto.move(7.0,0.0,1.0);

        /*
        closeBlockClaw();

        auto.move(-7.5,0.0,0.8);

        openBlockClaw();
        auto.move(2,0.0,1.0);


        while (opModeIsActive()) {
            //Run from time pressed play till end

        }*/
    }



    private void lowerSideStick() {
        MecanumAutonomus.moveDcMotorEncoded(sideStickMotor,0.2,-470);
    }
    private void raiseSideStick() {

        MecanumAutonomus.moveDcMotorEncoded(sideStickMotor,0.4,470);
    }

    private void moveToFrontOfGlyphHolder() {
        //Move until see the first thing going out

        auto.move(-25.5,0,0.9);

        frontStickServo.setPosition(1.0);
        manual.setSpeedsFromDirection(MotionDirections.E);
        manual.scaleSpeeds(0.8F);
        manual.updateMotors();
        //Wait until distance is less than 5 inches
        while(frontStickButton.getState() == true);
        manual.myStop();
        auto.move(0,-1.0,1.0);
        frontStickServo.setPosition(0.25);
        sleep(1000);

        auto.move(0,-(slotWidth/2 - 1),0.8);




        //Should end up with front sensor pointing directly at first spike dude



        //Move off platform fowards



        //
        //Rotate 180
        //auto.move(180,1.0);
    }

    private RelicRecoveryVuMark checkForVUMark() {
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

    private void vuMarkSetup() {
        //Relic setup
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters(cameraMonitorViewId);
        parameters.vuforiaLicenseKey = "AcBbjLb/////AAAAGUE95xAvpEQzuMrgrlfyB3pATdcFCbND7+gUm8qqRk/0O4nOKmK9NdEKNpY+27LaHPFRVrULXyQqqaUr9Vm7gtyncPMBYZo3FAfYcXboDQXtEdBOHG3HLYmczuv3/k0MUQ7PDtuNj9KlQF84vB3ZpMQCfbqMVaXGdn1rLTCiOFtDdLhK9QGC315hgzV9VsbJ0wzwwlDabJBq5+aFNFhw4gF3YS97FG6fHiMGIJ4piGQJXwh+jKuV0A7ZHoqysxs2xsV9iUSOEbsawRXo/Lg1aXw1B8SZ6T6P6oqdvnwFYXSpGT0LKkNi9K6/7g/MiFkJLcYFpVNxz6i9gueh5c4ZKaYMHwVIbW6x/cmVFSQJSPZd";
        parameters.cameraDirection = VuforiaLocalizer.CameraDirection.BACK;
        this.vuforia = ClassFactory.createVuforiaLocalizer(parameters);
        relicTrackables = this.vuforia.loadTrackablesFromAsset("RelicVuMark");
        relicTemplate = relicTrackables.get(0);
        relicTemplate.setName("relicVuMarkTemplate");
    }
    private void lookForVuMark() {
        //Look for vu mark
        int counter = 0;
        while(vuMark == RelicRecoveryVuMark.UNKNOWN) {
            vuMark = checkForVUMark();
            counter++;
            sleep(50);
            telemetry.addData("Checking for VU Mark", true);
            telemetry.update();
            if(counter > 40) {
                //None found, set to center
                vuMark = RelicRecoveryVuMark.CENTER;
                //break;
            }
        }
        telemetry.addData("vu mark valxue", vuMark);
        telemetry.update();
    }
    private void getClearanceFromRelicExtender() {
        relicExtender.setPower(-0.5);
        sleep(800);
        relicExtender.setPower(0.0);
    }

    private void hardwareSetup() {
        //Servos
        leftBlockGrabber = hardwareMap.servo.get("leftBlockServo");
        rightBlockGrabber = hardwareMap.servo.get("rightBlockServo");
        frontStickServo = hardwareMap.servo.get("frontStick");
        //sideStick = hardwareMap.servo.get("sideStick");

        //Motors
        motorFrontLeft = hardwareMap.dcMotor.get("fl");
        motorFrontRight = hardwareMap.dcMotor.get("fr");
        motorBackLeft = hardwareMap.dcMotor.get("bl");
        motorBackRight = hardwareMap.dcMotor.get("br");
        blockRaiser = hardwareMap.dcMotor.get("blockRaiser");
        sideStickMotor = hardwareMap.dcMotor.get("sideStickMotor");
        relicExtender = hardwareMap.dcMotor.get("relicExtender");

        //Others
        myColorSensor = hardwareMap.get(ColorSensor.class, "sence");
        frontStickButton = hardwareMap.get(DigitalChannel.class, "frontStickButton");
        frontStickButton.setMode(DigitalChannel.Mode.INPUT);

    }
    private void openBlockClaw() {
        //Put servos in closed position
        rightBlockGrabber.setPosition(0.3);
        leftBlockGrabber.setPosition(0.2);
    }
    private void closeBlockClaw() {
        rightBlockGrabber.setPosition(0.6);
        leftBlockGrabber.setPosition(0.0);

    }

    private void moveToCorrectSlot() {
        //Each slot is 7.63 inches wide so increment each distance by that
        switch (vuMark) {
            case LEFT:
                //auto.move(0.0,-1.5,0.8);
                break;
            case CENTER:
                auto.move(0.0,slotWidth,0.8);
                break;
            case RIGHT:
                auto.move(0.0,slotWidth * 2,0.8);
                break;
            default:
                //auto.move(0.0,-8.63,0.8);
                break;
        }

    }

    private void knockDownBall() {
        //Knock down other team ball
        if(ballColor == teamColor) {
            //Move away from this ball, ie left



            auto.move(-20,0.6);


            raiseSideStick();
            auto.move(20,0.6);






        }
        else {
            //Move towards this ball ie right
            auto.move(20,0.6);

            raiseSideStick();

            auto.move(-20,0.6);

        }
    }
    /*
    public static void moveDcMotorEncoded(DcMotor motor, double speed, int steps) {
        motor.setMode(DcMotor.RunMode.RESET_ENCODERS);
        motor.setTargetPosition(steps);
        motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        motor.setPower(speed);

        while (motor.isBusy());
        motor.setPower(0.0);
    }*/

    public Color getColor() {

        telemetry.addData("red: ", myColorSensor.red());
        telemetry.addData("blue: ", myColorSensor.blue());


        if (myColorSensor.red() > myColorSensor.blue()) return Color.RED;
        else return  Color.BLUE;


    }


}
