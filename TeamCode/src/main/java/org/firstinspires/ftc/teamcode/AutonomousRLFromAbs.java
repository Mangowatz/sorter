package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

/**
 * Created by Aryeh on 1/8/18.
 */
@Autonomous(name="Abstract:AutoRL", group="Autonomus")
public class AutonomousRLFromAbs extends AutonomousAbstract {


    @Override
    void moveToFrontOfGlyphHolder() {
        //Move off platform fowards
        //Move off platform fowards
        auto.move(25,0,0.4);
        //Rotate 180
        frontStickServo.setPosition(1.0);
        auto.move(90,movementSpeed);

        //wait for press



        manual.setSpeedsFromDirection(MotionDirections.S);
        manual.scaleSpeeds(0.2F);
        manual.updateMotors();
        //wait for button to be pressed
        while(frontFrontStickButton.getState() == true && !isStopRequested());
        manual.myStop();

        auto.move(1,0,movementSpeed);

        manual.setSpeedsFromDirection(MotionDirections.E);
        manual.scaleSpeeds(0.3F);
        manual.updateMotors();
        //wait for button to be pressed
        while(frontStickButton.getState() == true && !isStopRequested());
        manual.myStop();

        auto.move(0,-3.0,movementSpeed);


        frontStickServo.setPosition(0.25);
        sleep(1000);
        auto.move(0,2.3,movementSpeed);

    }

    @Override
    void moveToCorrectSlot() {
        switch (vuMark) {
            case LEFT:
                auto.move(0.0,slotWidth * 2 + 0.5,movementSpeed);
                break;
            case CENTER:
                auto.move(0.0,slotWidth,movementSpeed);
                break;
            case RIGHT:
                //auto.move(0.0,4.87,0.8);
                break;
            default:
                break;

        }
    }

    @Override
    Color getTeamColor() {
        return Color.RED;
    }
}
