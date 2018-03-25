package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

/**
 * Created by Aryeh on 1/8/18.
 */
@Autonomous(name="Abstract:AutoRR", group="Autonomus")
public class AutonomousRRFromAbs extends AutonomousAbstract {

    public double slotWidth = 8;

    @Override
    void moveToFrontOfGlyphHolder() {
        //Move off platform fowards
        //Auto controls the mecanum motors
        //move fowards 24 inches
        auto.move(24,0,0.4);
        //Lower front stick servo
        frontStickServo.setPosition(1.0);

        //rotate 180 degrees
        auto.move(180,movementSpeed);



        //Manual controls all the wheels without set distances, only set speeds
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

        //raise stick
        frontStickServo.setPosition(0.25);
        sleep(1000);
        auto.move(0,2.3,movementSpeed);
    }

    @Override
    void moveToCorrectSlot() {
        switch (vuMark) {
            case LEFT:
                auto.move(0.0,slotWidth * 2 + 1,movementSpeed);
                break;
            case CENTER:
                auto.move(0.0,slotWidth,movementSpeed);
                break;
            case RIGHT:
                //auto.move(0.0,1.3,movementSpeed);
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
