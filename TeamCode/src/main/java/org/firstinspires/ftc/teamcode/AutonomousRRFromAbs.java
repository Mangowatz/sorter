package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

/**
 * Created by Aryeh on 1/8/18.
 */
@Autonomous(name="Abstract:AutoRR", group="Autonomus")
public class AutonomousRRFromAbs extends AutonomousAbstract {


    @Override
    void moveToFrontOfGlyphHolder() {
        //Move off platform fowards
        auto.move(25,0,movementSpeed);
        //Rotate 180
        auto.move(180,movementSpeed);
        auto.move(-0.5,0,movementSpeed);

        frontStickServo.setPosition(1.0);
        manual.setSpeedsFromDirection(MotionDirections.E);
        manual.scaleSpeeds(0.4F);
        manual.updateMotors();
        //wait for button to be pressed
        while(frontStickButton.getState() == true && !isStopRequested());
        manual.myStop();
        auto.move(0,-3.0,movementSpeed);

        frontStickServo.setPosition(0.25);
        sleep(1000);
        auto.move(0,2.5,movementSpeed);

    }

    @Override
    void moveToCorrectSlot() {
        switch (vuMark) {
            case LEFT:
                auto.move(0.0,slotWidth + 1.0,movementSpeed);
                break;
            case CENTER:
                auto.move(0.0,slotWidth + 1.0,movementSpeed);
                break;
            case RIGHT:
                auto.move(0.0,1.0,movementSpeed);
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
