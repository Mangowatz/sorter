package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;

/**
 * Created by Zack on 4/29/19.
 * Adapted from Aryeh's 2017-2018 FTC code
 */

public class AutoSorterDeltaS extends LinearOpMode {

    static final int block1 = 10;
    static final int block2 = 18;
    static final int block3 = 613;

    private ColorSensor jimmyTheSensor;

    @Override
    public void runOpMode(){


        //Setup Hardware
        hardwareSetup();
        waitForStart();
        colorFeedback();
        //move conveyor @ speed 1
        while(!(jimmyTheSensor.red() >= 5) && !(jimmyTheSensor.green() >= 5) && !(jimmyTheSensor.blue() >= 5)){
            getLegnth();
        }
        deposit();

    }

    private void hardwareSetup(){
        jimmyTheSensor = hardwareMap.get(ColorSensor.class, "Jimmy");

    }
    int distanceCount;

    public void colorFeedback(){

        telemetry.addData("red: ", jimmyTheSensor.red());
        telemetry.addData("green: ", jimmyTheSensor.green());
        telemetry.addData("blue: ", jimmyTheSensor.blue());
    }

    protected void getLegnth(){ //this code continually runs when there is a block. distanceCount represents object distance in (mm). in theory,  at least

        //move conveyor a shtickle (~1mm)
        distanceCount++;
        telemetry.addData("Measuring Distance: ",distanceCount);

    }
    private void deposit() { //thank you for distance; now it will go into box
        if ((distanceCount <= block1 + 5) && (distanceCount >= block1 - 5)) {//block 1
            distanceCount = 0;
            telemetry.addData("Block chosen: ",1);
            //peretz move this area uno
        } else if ((distanceCount <= block2 + 5) && (distanceCount >= block2 - 5)) {//block 2
            distanceCount = 0;
            telemetry.addData("Block chosen: ",2);
            //peretz move this area DOS
        } else if ((distanceCount <= block3 + 5) && (distanceCount >= block3 - 5)) {//block 3
            distanceCount = 0;
            telemetry.addData("Block chosen: ",3);
            //peretz move this area tres
        } else{
            telemetry.addData("ERROR",0);
        }
    }
}


