package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by Aryeh on 1/8/18.
 */

@TeleOp(name="JustDrive2", group="TeleOp")
@Disabled
public class JustDriveOp extends OpMode {
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
    protected MotorSpeeds speed;

    @Override
    public void init() {
        speed  = new MotorSpeeds(motorFrontLeft,motorFrontRight,motorBackLeft,motorBackRight);
        setupHardware();
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
        else {
            speed.myStop();
        }

        telemetry.addData("FL: ", speed.frontLeft);
        telemetry.addData("FR: ", speed.frontRight);
        telemetry.addData("BL: ", speed.backLeft);
        telemetry.addData("BR: ", speed.backRight);
        telemetry.update();

    }

    @Override
    public void stop() {
        super.stop();
        speed.myStop();
    }

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



        //public MotorSpeeds(double frontL, double frontR, double backL, double backR)

        speed.setSpeeds(frontLeftPower,frontRightPower,backLeftPower,backRightPower);

        speed.updateMotors();



    }


    private void setupHardware(){
        motorFrontLeft = hardwareMap.dcMotor.get("fl");
        motorFrontRight = hardwareMap.dcMotor.get("fr");
        motorBackLeft = hardwareMap.dcMotor.get("bl");
        motorBackRight = hardwareMap.dcMotor.get("br");
    }
}
