package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

/**
 * Created by Zack on 4/29/19.
 * Adapted from Aryeh's 2017-2018 FTC code
 */
@Autonomous(name = "rgb", group = "Autonomous")
public class AutoSorterRGB extends LinearOpMode {

    static final int block1R = 1;
    static final int block1G = 1;
    static final int block1B = 1;

    static final int block2R = 1;
    static final int block2G = 1;
    static final int block2B = 1;

    static final int block3R = 1;
    static final int block3G = 1;
    static final int block3B = 1;
    private ColorSensor jimmyTheSensor;

    @Override
    public void runOpMode() {

        //Setup Hardware
        hardwareSetup();
        waitForStart();
        while (opModeIsActive()) {
            colorFeedback();

            while (!(jimmyTheSensor.red() >= 20) && !(jimmyTheSensor.green() >= 20) && !(jimmyTheSensor.blue() >= 20)) {
                deposit();
                telemetry.update();
                //move conveyor
            }
        }
    }

    private void hardwareSetup(){
        jimmyTheSensor = hardwareMap.get(ColorSensor.class, "Jimmy");
    }

    public void colorFeedback(){

        telemetry.addData("red: ", jimmyTheSensor.red());
        telemetry.addData("green: ", jimmyTheSensor.green());
        telemetry.addData("blue: ", jimmyTheSensor.blue());
        if((jimmyTheSensor.red() >= 20) && (jimmyTheSensor.green() >= 20) && (jimmyTheSensor.blue() >= 20)){
            telemetry.addData("No object",0);
        }
        telemetry.update();
    }
    private void deposit(){
        if(/*color matches up with brick1*/
                        (jimmyTheSensor.red() >= block1R +10)&&(jimmyTheSensor.red() <= block1R-10)
                        &&(jimmyTheSensor.green() >= block1G +10)&&(jimmyTheSensor.green() <= block1G-10)
                        &&(jimmyTheSensor.blue() >= block1B +10)&&(jimmyTheSensor.blue() <= block1B-10))
            {
                telemetry.addData("Block Chosen: ",1);
                //pertz code sends to area 1
            }

        else if(/*color matches up with brick2*/
                        (jimmyTheSensor.red() >= block2R +10)&&(jimmyTheSensor.red() <= block2R-10)
                        &&(jimmyTheSensor.green() >= block2G +10)&&(jimmyTheSensor.green() <= block2G-10)
                        &&(jimmyTheSensor.blue() >= block2B +10)&&(jimmyTheSensor.blue() <= block2B-10))
            {
                telemetry.addData("Block Chosen: ",2);
                //pertz code sends to area 2
            }

        else if(/*color matches up with brick3*/
                        (jimmyTheSensor.red() >= block3R +10)&&(jimmyTheSensor.red() <= block3R-10)
                        &&(jimmyTheSensor.green() >= block3G +10)&&(jimmyTheSensor.green() <= block3G-10)
                        &&(jimmyTheSensor.blue() >= block3B +10)&&(jimmyTheSensor.blue() <= block3B-10))
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