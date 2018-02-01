package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by Aryeh on 1/31/18.
 */

@Autonomous(name="Test", group="Autonomus")
public class TestServo extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        Servo serv = hardwareMap.servo.get("serv");



        waitForStart();
        while (opModeIsActive()) {
            serv.setPosition(gamepad1.right_stick_y);
        }

    }
}
