package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

/**
 * Created by Aryeh on 1/8/18.
 */
@Autonomous(name="Abstract:AutoBR", group="Autonomus")
public class AutonomousBRFromAbs extends AutonomousAbstract {


    @Override
    void moveToFrontOfGlyphHolder() {
        //Move off platform fowards
        auto.move(-28,0,movementSpeed);

        auto.move(90,movementSpeed);
        //auto.move(0,-6,0.7);
        frontStickServo.setPosition(1.0);
        auto.move(-1.5,0,movementSpeed);


        sleep(300);

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
        while(frontStickButton.getState() == true);
        manual.myStop();
        auto.move(0,-1.0,movementSpeed);


        frontStickServo.setPosition(0.25);
        //sleep(200);
        //auto.move(0,1,movementSpeed);
    }


    @Override
    void moveToCorrectSlot() {
        switch (vuMark) {
            case LEFT:
                auto.move(0,0.6,movementSpeed);
                break;
            case CENTER:
                auto.move(0.0,-(slotWidth - 0.5),movementSpeed);
                break;
            case RIGHT:
                //auto.move(0.0,4.87,0.8);
                auto.move(0.0,-(slotWidth * 2),movementSpeed);
                break;
            default:
                auto.move(0,1,movementSpeed);
                break;

        }
    }

    @Override
    Color getTeamColor() {
        return Color.BLUE;
    }
}
