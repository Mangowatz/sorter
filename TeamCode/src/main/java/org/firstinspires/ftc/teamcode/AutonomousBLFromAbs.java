package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

/**
 * Created by Aryeh on 1/8/18.
 */
@Autonomous(name="Abstract:AutoBL", group="Autonomus")
public class AutonomousBLFromAbs extends AutonomousAbstract {

    @Override
    void moveToFrontOfGlyphHolder() {
        //Move off platform fowards
        auto.move(-24,0,0.4);
        //Rotate 180
        //auto.move(180,movementSpeed);
        //auto.move(-2.5,0,movementSpeed);

        auto.move(0,-4.5,movementSpeed);

        frontStickServo.setPosition(1.0);
        sleep(2000);




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
        auto.move(0,-1.0,movementSpeed);

        frontStickServo.setPosition(0.25);

    }


    @Override
    void moveToCorrectSlot() {
        switch (vuMark) {
            case LEFT:
                auto.move(0,0,movementSpeed);
                break;
            case CENTER:
                auto.move(0.0,-(slotWidth),movementSpeed);
                break;
            case RIGHT:
                //auto.move(0.0,4.87,0.8);
                auto.move(0.0,-(slotWidth * 2),movementSpeed);
                break;
            default:
                auto.move(0,0,movementSpeed);
                break;

        }
    }

    @Override
    Color getTeamColor() {
        return Color.BLUE;
    }
}
