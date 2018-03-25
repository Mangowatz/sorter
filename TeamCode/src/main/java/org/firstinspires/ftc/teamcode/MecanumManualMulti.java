package org.firstinspires.ftc.teamcode;

/**
 * Created by Aryeh on 10/1/17.
 */

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcontroller.external.samples.SensorDigitalTouch;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;

import java.util.Locale;


@TeleOp(name="Manual Multi Controller", group="TeleOp")
public class MecanumManualMulti extends LinearOpMode {

    protected Servo leftBlockGrabber;
    protected Servo rightBlockGrabber;
    protected Servo frontAngleServo;
    protected Servo relicGrabberServo;
    protected Servo frontStickServo;
    //protected Servo frontBlockRotater;
    protected Servo blockHolder;
    protected Servo blockPusher;

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


    ///If the claw is closed
    private boolean xPressed = false;
    private boolean clawClosed = false;
    private boolean relicClawClosed = true;
    private boolean switchFront = false;
    private boolean leftBumperPressed = false;
    private boolean rightStickPressed = false;
    private boolean angleMetalDown = false;
    private boolean rightBumperPressed = false;
    private boolean relicRaiserDownWasPressed = false;
    private boolean frontStickServoDown = false;
    private boolean oneLeftBumperPressed = false;
    private boolean twoRightBumperPressed = false;
    private boolean blockPusherOut = true;
    MotorSpeeds speed;



    @Override
    public void runOpMode() throws InterruptedException {


        hardwareSetup();

        //Setup accessories
        //myColorSensor = hardwareMap.get(ColorSensor.class, "sence");

        /*
        //Setup VuMark detection
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters(cameraMonitorViewId);
        parameters.vuforiaLicenseKey = "AcBbjLb/////AAAAGUE95xAvpEQzuMrgrlfyB3pATdcFCbND7+gUm8qqRk/0O4nOKmK9NdEKNpY+27LaHPFRVrULXyQqqaUr9Vm7gtyncPMBYZo3FAfYcXboDQXtEdBOHG3HLYmczuv3/k0MUQ7PDtuNj9KlQF84vB3ZpMQCfbqMVaXGdn1rLTCiOFtDdLhK9QGC315hgzV9VsbJ0wzwwlDabJBq5+aFNFhw4gF3YS97FG6fHiMGIJ4piGQJXwh+jKuV0A7ZHoqysxs2xsV9iUSOEbsawRXo/Lg1aXw1B8SZ6T6P6oqdvnwFYXSpGT0LKkNi9K6/7g/MiFkJLcYFpVNxz6i9gueh5c4ZKaYMHwVIbW6x/cmVFSQJSPZd";
        parameters.cameraDirection = VuforiaLocalizer.CameraDirection.BACK;
        this.vuforia = ClassFactory.createVuforiaLocalizer(parameters);
        relicTrackables = this.vuforia.loadTrackablesFromAsset("RelicVuMark");
        relicTemplate = relicTrackables.get(0);
        relicTemplate.setName("relicVuMarkTemplate");*/

        speed = new MotorSpeeds(motorFrontLeft,motorFrontRight,motorBackLeft,motorBackRight);






        waitForStart();
        //start
        //Move angle thing up initially
        frontAngleServo.setPosition(0.2);

        //Make sure relic grabber is closed
        relicGrabberServo.setPosition(0.0);

        //Extend color senser arm slightly to get out of way
        //MecanumAutonomus.moveDcMotorEncoded(sideStickMotor,0.2,-100);
        sideStickMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        MecanumAutonomus.moveDcMotorEncoded(relicExtender,0.6,-1000);




        //extend relic extender slightly
//        relicExtender.setPower(-0.5);
//        sleep(500);
//        relicExtender.setPower(0.0);




        while(opModeIsActive()) {
            //Loop

            if(Math.abs(gamepad1.right_stick_x) >= 0.01 || Math.abs(gamepad1.right_stick_y) >= 0.01) {
                rightJoystick();
            }


            else if (gamepad1.left_trigger > 0.01) {
                leftTrigger(gamepad1.left_trigger);
                //Rotate left

            }
            else if (gamepad1.right_trigger > 0.01) {
                //rotate right
                rightTrigger(gamepad1.right_trigger);

            }
            else {
                speed.myStop();
            }

            if(gamepad1.right_stick_button) {
                if(!rightStickPressed) {
                    rightStickPressed = true;
                    switchFront = !switchFront;

                }
                //Switch back to front
            }
            else {
                rightStickPressed = false;
            }


            if(gamepad2.a) {
                //move block riser down
                blockRaiser.setPower(0.7);
            }
            else if(gamepad2.y) {
                //Move block riser up
                blockRaiser.setPower(-0.7);
            }
            else {
                blockRaiser.setPower(0.0);
            }

            if(gamepad2.x) {
                //open claw

                if(!xPressed) {
                    xPressed = true;
                    if(clawClosed) {
                        rightBlockGrabber.setPosition(0.85);
                        leftBlockGrabber.setPosition(0.55);
                        blockHolder.setPosition(0.5);

                        clawClosed = false;
                    }
                    else {
                        rightBlockGrabber.setPosition(1.0);

                        leftBlockGrabber.setPosition(0.4);
                        if(blockPusherOut) {
                            blockHolder.setPosition(0.00);
                        }

                        clawClosed = true;
                    }
                }




            }
            else {
                xPressed = false;
            }

            if(gamepad2.dpad_up) {
                //Relic riser up
                //relicRaiserDownWasPressed = false;

                if(relicRaiserMax.getState() == true) {
                    relicRaiser.setPower(-1.0);
                }
                else {
                    relicRaiser.setPower(0.0);
                }
                telemetry.addData("Dpad up", gamepad1.dpad_up);

            }
            else if(gamepad2.dpad_down) {
                //Relic riser down
                //relicRaiserDownWasPressed = true;
                telemetry.addData("Dpad down", gamepad1.dpad_down);
                relicRaiser.setPower(1.0);
            }

            else {
                /*
                if(relicRaiserDownWasPressed) {
                    relicRaiser.setPower(-1.0);
                    sleep(100);
                    relicRaiser.setPower(0.0);
                    relicRaiserDownWasPressed = false;
                }
                else {
                    relicRaiser.setPower(0.0);
                    relicRaiserDownWasPressed = false;
                }*/
                relicRaiser.setPower(0.0);

            }

            if(gamepad2.dpad_right) {
                //Extend relic
                relicExtender.setPower(0.75);



            }
            else if(gamepad2.dpad_left) {
                //Retract relic
                //Down
                relicExtender.setPower(-1.0);
            }
            else {
                relicExtender.setPower(0.0);
            }



            if(gamepad2.left_bumper) {
                if(!leftBumperPressed) {
                    leftBumperPressed = true;
                    if(relicClawClosed) {
                        //open claw
                        relicGrabberServo.setPosition(0.6);
                        relicClawClosed = false;
                    }
                    else {
                        relicGrabberServo.setPosition(0.0);
                        relicClawClosed = true;
                    }

                }


            }

            else {
                leftBumperPressed = false;
            }
            if(gamepad2.left_trigger > 0.01) {
                sideStickMotor.setPower(-0.2 * gamepad2.left_trigger);
            }
            else if(gamepad2.right_trigger > 0.01) {
                sideStickMotor.setPower(0.3 * gamepad2.right_trigger);
            }
            else {
                sideStickMotor.setPower(0.0);
            }

            if(gamepad1.right_bumper) {
                if(!rightBumperPressed) {
                    rightBumperPressed = true;

                    if (angleMetalDown) {
                        frontAngleServo.setPosition(0.2);
                        angleMetalDown = !angleMetalDown;
                    } else {
                        frontAngleServo.setPosition(1.0);
                        angleMetalDown = !angleMetalDown;
                    }


                }
            }
            else {
                rightBumperPressed = false;
            }
            if(gamepad1.left_bumper) {
                if(!oneLeftBumperPressed) {
                    oneLeftBumperPressed = true;
                    frontStickServo.setPosition(frontStickServoDown ? 0.25 : 1.0);
                    frontStickServoDown = !frontStickServoDown;

                }

            }
            else {
                oneLeftBumperPressed = false;

            }


            if(gamepad2.right_bumper) {
                if(!twoRightBumperPressed) {


                    blockHolder.setPosition(0.5);
                    twoRightBumperPressed = true;
                    blockPusher.setPosition(blockPusherOut ? 1.0: 0.4);
                    blockPusherOut = !blockPusherOut;

                    //frontBlockStick = !frontBlockStick;
                }
            }
            else {
                twoRightBumperPressed = false;
            }




            telemetry.addData("FL: ", motorFrontLeft.getPower());
            telemetry.addData("FR: ", motorFrontRight.getPower());
            telemetry.addData("BL: ", motorBackLeft.getPower());
            telemetry.addData("BR: ", motorBackRight.getPower());
            telemetry.update();



        }







    }

    /*
    @Override
    public void init_loop() {

    }

    @Override
    public void start() {
        //run when user presses start
        //relicTrackables.activate();

        //Extend relic slightly at the beginging

    }

    @Override
    public void loop() {
        if(Math.abs(gamepad1.right_stick_x) >= 0.01 || Math.abs(gamepad1.right_stick_y) >= 0.01) {
            rightJoystick();
        }


        else if (gamepad1.left_trigger > 0.01) {
            leftTrigger(gamepad1.left_trigger);
            //Rotate left

        }
        else if (gamepad1.right_trigger > 0.01) {
            //rotate right
            rightTrigger(gamepad1.right_trigger);

        }
        else if(gamepad1.a) {
            //move block riser down
            blockRaiser.setPower(0.2);
        }
        else if(gamepad1.y) {
            //Move block riser up
            blockRaiser.setPower(-0.3);
        }
        else if(gamepad1.x) {
            //open claw

            rightBlockGrabber.setPosition(0.3);
            leftBlockGrabber.setPosition(0.2);




        }

        else if(gamepad1.b) {

            //Close claw


            rightBlockGrabber.setPosition(0.5);
            leftBlockGrabber.setPosition(0.0);
        }

        else if(gamepad1.dpad_up) {
            //Relic riser up
            telemetry.addData("Dpad up", gamepad1.dpad_up);
            relicRaiser.setPower(-1.0);
        }
        else if(gamepad1.dpad_down) {
            //Relic riser down
            telemetry.addData("Dpad down", gamepad1.dpad_down);
            relicRaiser.setPower(1.0);
        }
        else if(gamepad1.dpad_right) {
            //Extend relic
            relicExtender.setPower(-0.5);

        }
        else if(gamepad1.dpad_left) {
            //Retract relic
            relicExtender.setPower(0.5);
        }

        else if(gamepad1.right_bumper) {
            //Switch back to front
            switchFront = true;

        }
        else if(gamepad1.left_bumper) {
            switchFront = false;
        }

        else {
            speed.stop();

            //Stock block raiser
            blockRaiser.setPower(0.0);
            relicRaiser.setPower(0.0);
            relicExtender.setPower(0.0);
        }







    }*/

    private void rightTrigger(double triggerValue) {
        speed.setSpeedsFromMotorSpeeds(MotorSpeeds.getSpeed(MotionDirections.ROTATER));

        speed.backRight *= triggerValue;
        speed.backLeft *= triggerValue;
        speed.frontLeft *= triggerValue;
        speed.frontRight *= triggerValue;
        speed.updateMotors();

    }
    private void leftTrigger(double triggerValue) {
        speed.setSpeedsFromMotorSpeeds(MotorSpeeds.getSpeed(MotionDirections.ROTATEL));

        speed.backRight *= triggerValue;
        speed.backLeft *= triggerValue;
        speed.frontLeft *= triggerValue;
        speed.frontRight *= triggerValue;
        speed.updateMotors();


    }



    private void rightJoystick() {
        double fixedYValue = -gamepad1.right_stick_y;
        double xValue = gamepad1.right_stick_x;

        if(switchFront) {
            xValue *= -1;
            fixedYValue *= -1;
        }
        double rightRotation = gamepad1.right_trigger;
        double leftRotation = gamepad1.left_trigger;
        double robotSpeed = Math.sqrt(Math.pow(fixedYValue,2) + Math.pow(xValue,2));
        double changeDirectionSpeed = 0;

        if(rightRotation > leftRotation) {
            changeDirectionSpeed = rightRotation;
        }
        else {
            changeDirectionSpeed = -leftRotation;
        }

        double frontLeftPower = robotSpeed * Math.sin(Math.atan2(xValue,fixedYValue) + Math.PI/4) + changeDirectionSpeed;
        double frontRightPower = robotSpeed * Math.cos(Math.atan2(xValue,fixedYValue) + Math.PI/4) - changeDirectionSpeed;
        double backLeftPower = robotSpeed * Math.cos(Math.atan2(xValue,fixedYValue) + Math.PI/4) + changeDirectionSpeed;
        double backRightPower = robotSpeed * Math.sin(Math.atan2(xValue,fixedYValue) + Math.PI/4) - changeDirectionSpeed;


        if(gamepad1.dpad_left) {
            double[] array = {frontLeftPower,frontRightPower,backLeftPower,backRightPower};
            //if none of them are equal to power, get scaler from max to power and scale all by that
            double max = array[0];
            for(int i = 1; i < array.length; i++) {
                if(array[i] > max) {
                    max = array[i];
                }
            }
            System.out.println("Max is " + max);
            double scaler = Math.abs(robotSpeed/max);

            frontLeftPower *= scaler;
            frontRightPower *= scaler;
            backLeftPower *= scaler;
            backRightPower *= scaler;

        }

        speed.setSpeeds(frontLeftPower,frontRightPower,backLeftPower,backRightPower);
        speed.updateMotors();



        /*
        //Speed is a double from 0 - 1.0 and is a scale factor to be applied to motor powers
        double speedCoef = Math.sqrt(Math.pow(gamepad1.left_stick_x,2) + Math.pow(gamepad1.left_stick_y,2));

        if(gamepad1.left_stick_x >= 0 && fixedYValue >= 0) {
            //first quad

            //If diff of values is less than .3 go diagonal
            if(Math.abs(gamepad1.left_stick_x - fixedYValue) > 0.3) {
                //Go horizontal or vertical

                //X > Y GO EAST
                if(gamepad1.left_stick_x > fixedYValue) speed.setSpeedsFromMotorSpeeds(MotorSpeeds.getSpeed(MotionDirections.E));

                    //Y > X GO NORTH
                else speed.setSpeedsFromMotorSpeeds(MotorSpeeds.getSpeed(MotionDirections.N));
            }

            else speed.setSpeedsFromMotorSpeeds(MotorSpeeds.getSpeed(MotionDirections.NE));
        }

        else if(gamepad1.left_stick_x < 0 && fixedYValue >= 0) {
            //second quad
            if(Math.abs(-gamepad1.left_stick_x - fixedYValue) > 0.3) {
                if(-gamepad1.left_stick_x > fixedYValue) {
                    //GO W
                    speed.setSpeedsFromMotorSpeeds(MotorSpeeds.getSpeed(MotionDirections.W));
                }

                else speed.setSpeedsFromMotorSpeeds(MotorSpeeds.getSpeed(MotionDirections.N));
            }
            else speed.setSpeedsFromMotorSpeeds(MotorSpeeds.getSpeed(MotionDirections.NW));

        }
        else if(gamepad1.left_stick_x < 0 && fixedYValue < 0) {
            //third quad

            if(Math.abs(gamepad1.left_stick_x - fixedYValue) > 0.3) {
                if(gamepad1.left_stick_x < fixedYValue) {
                    //GO W
                    speed.setSpeedsFromMotorSpeeds(MotorSpeeds.getSpeed(MotionDirections.W));
                }

                else speed.setSpeedsFromMotorSpeeds(MotorSpeeds.getSpeed(MotionDirections.S));
            }
            else speed.setSpeedsFromMotorSpeeds(MotorSpeeds.getSpeed(MotionDirections.SW));

        }
        else {
            //fourth quad

            if(Math.abs(gamepad1.left_stick_x + fixedYValue) > 0.3) {
                if(gamepad1.left_stick_x > -fixedYValue) {
                    //GO W
                    speed.setSpeedsFromMotorSpeeds(MotorSpeeds.getSpeed(MotionDirections.E));
                }

                else speed.setSpeedsFromMotorSpeeds(MotorSpeeds.getSpeed(MotionDirections.S));
            }
            else speed.setSpeedsFromMotorSpeeds(MotorSpeeds.getSpeed(MotionDirections.SE));

        }

        //apply speed coefficient
        speed.backRight *= speedCoef;
        speed.backLeft *= speedCoef;
        speed.frontRight *= speedCoef;
        speed.frontLeft *= speedCoef;

        speed.updateMotors();
*/

    }

    protected void hardwareSetup() {
        //Servos
        leftBlockGrabber = hardwareMap.servo.get("RBS");
        rightBlockGrabber = hardwareMap.servo.get("LBS");
        frontAngleServo = hardwareMap.servo.get("FAS");
        relicGrabberServo = hardwareMap.servo.get("ReGS");
        frontStickServo = hardwareMap.servo.get("FSS");
        //frontBlockRotater = hardwareMap.servo.get("FBR");
        blockHolder = hardwareMap.servo.get("BH");
        blockPusher = hardwareMap.servo.get("BLOCKPUSH");


        //Motors
        motorFrontLeft = hardwareMap.dcMotor.get("br");
        motorFrontRight = hardwareMap.dcMotor.get("bl");
        motorBackLeft = hardwareMap.dcMotor.get("fr");
        motorBackRight = hardwareMap.dcMotor.get("fl");
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

        sideStickMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        relicRaiser.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        blockRaiser.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);


    }


}


