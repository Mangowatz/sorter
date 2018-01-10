package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by Aryeh on 1/8/18.
 */
@TeleOp(name="JustDrive", group="TeleOp")
@Disabled
public class JustDrive extends LinearOpMode {

    protected Servo leftBlockGrabber;
    protected Servo rightBlockGrabber;
    protected Servo frontStickServo;
    protected Servo fontAngleServo;
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
    public void runOpMode() throws InterruptedException {
        setupHardware();
        speed  = new MotorSpeeds(motorFrontLeft,motorFrontRight,motorBackLeft,motorBackRight);
        waitForStart();
        while(opModeIsActive()) {
            if(Math.abs(gamepad1.right_stick_x) >= 0.01 || Math.abs(gamepad1.right_stick_y) >= 0.01) {
                rightJoystick();
                telemetry.addData("Should be driving: ", true);
            }


            else if (gamepad1.left_trigger > 0.01) {
                leftTrigger(gamepad1.left_trigger);
                //Rotate left
                telemetry.addData("Should be driving: ", true);

            }
            else if (gamepad1.right_trigger > 0.01) {
                //rotate right
                rightTrigger(gamepad1.right_trigger);
                telemetry.addData("Should be driving: ", true);

            }
            else {
                speed.myStop();
                telemetry.addData("Should be driving: ", false);
            }

            telemetry.addData("FL: ", speed.frontLeft);
            telemetry.addData("FR: ", speed.frontRight);
            telemetry.addData("BL: ", speed.backLeft);
            telemetry.addData("BR: ", speed.backRight);
            telemetry.addData("Stick value", gamepad1.right_stick_y);
            telemetry.update();

        }
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
