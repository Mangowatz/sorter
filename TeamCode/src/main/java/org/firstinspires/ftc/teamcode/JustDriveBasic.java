package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

/**
 * Created by Aryeh on 1/8/18.
 */

@TeleOp(name="JustDriveBasic", group="TeleOp")
@Disabled
public class JustDriveBasic extends OpMode {

    protected DcMotor motorFrontLeft;
    protected DcMotor motorFrontRight;
    protected DcMotor motorBackLeft;
    protected DcMotor motorBackRight;

    @Override
    public void init() {
        motorFrontLeft = hardwareMap.dcMotor.get("fl");
        motorFrontRight = hardwareMap.dcMotor.get("fr");
        motorBackLeft = hardwareMap.dcMotor.get("bl");
        motorBackRight = hardwareMap.dcMotor.get("br");
        motorFrontRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        motorFrontRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    @Override
    public void loop() {
        if(Math.abs(gamepad1.right_stick_y) > 0.01) {
            motorBackLeft.setPower(gamepad1.right_stick_y);
            motorBackRight.setPower(gamepad1.right_stick_y);
            motorFrontLeft.setPower(gamepad1.right_stick_y);
            motorFrontRight.setPower(gamepad1.right_stick_y);
        }
        else {
            motorBackLeft.setPower(0);
            motorBackRight.setPower(0);
            motorFrontLeft.setPower(0);
            motorFrontRight.setPower(0);
        }
    }

    @Override
    public void stop() {
        super.stop();
        motorBackLeft.setPower(0);
        motorBackRight.setPower(0);
        motorFrontLeft.setPower(0);
        motorFrontRight.setPower(0);
    }
}
