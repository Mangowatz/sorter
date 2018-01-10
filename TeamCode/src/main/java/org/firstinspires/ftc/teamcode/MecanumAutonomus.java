package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import java.util.Calendar;
import java.util.Date;


/**
 * Created by Aryeh on 11/7/17.
 */


//Circumference of the wheels: 12.56 inch


public class MecanumAutonomus extends Object {
    final double ticksPerInchFoward = 2240.0/12.56;
    final double ticksPerInchSideways = 2240.0/10.5;
    final double ticksPerDegree = 3200.0/90.0;

    double moveX;
    double moveY;
    double rotateDegrees;
    double speed;
    DcMotor fl;
    DcMotor fr;
    DcMotor bl;
    DcMotor br;
    //double angle;


    //Distances in inches
    public void move(double moveY, double moveX, double speed) {
        this.moveX = moveX;
        this.moveY = moveY;
        this.speed = speed;

        moveLinear();
    }

    public void move(double rotateDegrees, double speed) {

        this.rotateDegrees = rotateDegrees;
        this.speed = speed;
        rotationMove();
    }

    public void rotationMove() {
        int yRotations = (int) (rotateDegrees * ticksPerDegree);

        fl.setMode(DcMotor.RunMode.RESET_ENCODERS);
        fr.setMode(DcMotor.RunMode.RESET_ENCODERS);
        bl.setMode(DcMotor.RunMode.RESET_ENCODERS);
        br.setMode(DcMotor.RunMode.RESET_ENCODERS);


        fl.setTargetPosition(yRotations);
        fr.setTargetPosition(yRotations);
        bl.setTargetPosition(yRotations);
        br.setTargetPosition(yRotations);


        fl.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        fr.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        bl.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        br.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        //Start Moving
        fl.setPower(speed);
        fr.setPower(speed);
        bl.setPower(speed);
        br.setPower(speed);

        while(fl.isBusy() && fr.isBusy() && bl.isBusy() && br.isBusy());

        //Stop moving
        fl.setPower(0.0);
        fr.setPower(0.0);
        bl.setPower(0.0);
        br.setPower(0.0);
    }




    //280 clicks per rev
    private void moveLinear() {

        //Move y first

        int yRotations = (int) (ticksPerInchFoward * moveY);

        this.fl.setMode(DcMotor.RunMode.RESET_ENCODERS);
        this.fr.setMode(DcMotor.RunMode.RESET_ENCODERS);
        this.bl.setMode(DcMotor.RunMode.RESET_ENCODERS);
        this.br.setMode(DcMotor.RunMode.RESET_ENCODERS);


        fl.setTargetPosition(-yRotations);
        fr.setTargetPosition(yRotations);
        bl.setTargetPosition(-yRotations);
        br.setTargetPosition(yRotations);


        fl.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        fr.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        bl.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        br.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        //Start Moving
        fl.setPower(speed);
        fr.setPower(speed);
        bl.setPower(speed);
        br.setPower(speed);

        while(fl.isBusy() && fr.isBusy() && bl.isBusy() && br.isBusy());

        //Stop moving
        fl.setPower(0.0);
        fr.setPower(0.0);
        bl.setPower(0.0);
        br.setPower(0.0);





        //move x now


        int xRotations = (int) (ticksPerInchSideways * moveX);
        //moves 10.5 inches per rotation


        this.fl.setMode(DcMotor.RunMode.RESET_ENCODERS);
        this.fr.setMode(DcMotor.RunMode.RESET_ENCODERS);
        this.bl.setMode(DcMotor.RunMode.RESET_ENCODERS);
        this.br.setMode(DcMotor.RunMode.RESET_ENCODERS);


        //Front left and back left motor must be reveresed
        fl.setTargetPosition(-xRotations);
        fr.setTargetPosition(-xRotations);
        bl.setTargetPosition(xRotations);
        br.setTargetPosition(xRotations);


        fl.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        fr.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        bl.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        br.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        //Start Moving
        fl.setPower(speed);
        fr.setPower(speed);
        bl.setPower(speed);
        br.setPower(speed);

        while(fl.isBusy() && fr.isBusy() && bl.isBusy() && br.isBusy());

        //Stop moving
        fl.setPower(0.0);
        fr.setPower(0.0);
        bl.setPower(0.0);
        br.setPower(0.0);




    }





    public MecanumAutonomus(DcMotor fl, DcMotor fr, DcMotor bl, DcMotor br) {
        this.fl = fl;
        this.fr = fr;
        this.bl = bl;
        this.br = br;

        this.fl.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        this.fr.setMode(DcMotor.RunMode.RUN_USING_ENCODERS);
        this.bl.setMode(DcMotor.RunMode.RUN_USING_ENCODERS);
        this.br.setMode(DcMotor.RunMode.RUN_USING_ENCODERS);
    }

    public static void moveDcMotorEncoded(DcMotor motor, double speed, int steps, LinearOpMode sender) {
        motor.setMode(DcMotor.RunMode.RESET_ENCODERS);
        motor.setTargetPosition(steps);
        motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        motor.setPower(speed);

        while (motor.isBusy() && !sender.isStopRequested());
        motor.setPower(0.0);
    }
    public static void moveDcMotorEncoded(DcMotor motor, double speed, int steps) {
        motor.setMode(DcMotor.RunMode.RESET_ENCODERS);
        motor.setTargetPosition(steps);
        motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        motor.setPower(speed);

        while (motor.isBusy());
        motor.setPower(0.0);
    }
    public static void moveDcMotorEncoded(DcMotor motor, double speed, int steps, int timeLimitMili) {
        Calendar end = Calendar.getInstance();
        end.add(Calendar.MILLISECOND,timeLimitMili);

        //Date finishedByTime = new Date()


        motor.setMode(DcMotor.RunMode.RESET_ENCODERS);
        motor.setTargetPosition(steps);
        motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        motor.setPower(speed);
        //time limit
        while (motor.isBusy() && end.after(Calendar.getInstance()));
        motor.setPower(0.0);
    }





}
