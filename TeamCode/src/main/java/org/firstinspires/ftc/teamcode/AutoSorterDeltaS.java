package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;

/**
 * Created by Zack on 4/29/19.
 * Adapted from Aryeh's 2017-2018 FTC code
 */

public class AutoSorterDeltaS extends LinearOpMode {

    static final int block1 = 1;
    static final int block2 = 1;
    static final int block3 = 1;

    private ColorSensor jimmyTheSensor;

    @Override
    public void runOpMode(){

        //Setup Hardware
        hardwareSetup();
        waitForStart();
        colorFeedback();
        while(!(jimmyTheSensor.red() >= 5) && !(jimmyTheSensor.green() >= 5) && !(jimmyTheSensor.blue() >= 5)){
            deposit();
            //move conveyor
        }
    }

    private void hardwareSetup(){
        jimmyTheSensor = hardwareMap.get(ColorSensor.class, "Jimmy");
    }

    public void colorFeedback(){

        telemetry.addData("red: ", jimmyTheSensor.red());
        telemetry.addData("blue: ", jimmyTheSensor.green());
        telemetry.addData("blue: ", jimmyTheSensor.blue());
    }
    private void deposit(){
        if(/*distance matches up with brick1*/)

        {
            telemetry.addData("Block Chosen: ",1);
            //pertz code sends to area 1
        }

        else if(/*distance matches up with brick2*/)
        {
            telemetry.addData("Block Chosen: ",2);
            //pertz code sends to area 2
        }

        else if(/*distance matches up with brick3*/)
        {
            telemetry.addData("Block Chosen: ",3);
            //pertz code sends to area 3
        }

        else{
            telemetry.addData("ERROR: ",1);
            telemetry.addData("UNKNOWN OBJECT",0);
        }

    }
}


