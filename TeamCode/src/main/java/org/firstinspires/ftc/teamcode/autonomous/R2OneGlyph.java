package org.firstinspires.ftc.teamcode.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;
import org.firstinspires.ftc.teamcode.robotlibrary.pop.ActUponJewelKicker;
import org.firstinspires.ftc.teamcode.robotlibrary.pop.BasicGyroTurn;
import org.firstinspires.ftc.teamcode.robotlibrary.pop.EncoderDrive;
import org.firstinspires.ftc.teamcode.robotlibrary.pop.Lift;
import org.firstinspires.ftc.teamcode.robotlibrary.pop.LiftToPosition;

/**
 * Created by Dynamic Signals on 10/10/2017.
 */

@Autonomous(name = "R2OneGlyph", group = "A-Team")
public class R2OneGlyph extends org.firstinspires.ftc.teamcode.robotlibrary.pop.Autonomous {

    @Override
    public void start() {
        setAlliance("Red");
        gyroUtils.calibrateGyro();
    }

    @Override
    public void loop() {

        // Use Jewel Kicker with color and alliance and read vumark
        if (stage == 0) {
            ActUponJewelKicker.doAction(this, kicker, alliance);
            relicRecoveryVuMark = vuforiaSystem.getVuMark();
            if (relicRecoveryVuMark.equals(RelicRecoveryVuMark.UNKNOWN)) {
                relicRecoveryVuMark = RelicRecoveryVuMark.LEFT;
            }
        }

        // Move lift up to second position
        if (stage == 1) {
            lift.setRampPosition(Lift.RampServoPosition.FLAT);
            next();
        }

        // Drive off ramp
        if (stage == 2) {
            EncoderDrive.createDrive(this, 1100, 0.35);
        }

        // Turn to -90 degrees to be parallel to the cryptobox
        if (stage == 3) {
            BasicGyroTurn.createTurn(this, -90);
        }

        /*
         * Right - 400
         * Center - 100
         * Left - 400
         */
        // Drive to appropriate positions based on read vumark
        if (stage == 4) {
            if (relicRecoveryVuMark.equals(RelicRecoveryVuMark.LEFT)) {
                EncoderDrive.createDrive(this, 400, 0.35);
            }
            if (relicRecoveryVuMark.equals(RelicRecoveryVuMark.CENTER)) {
                EncoderDrive.createDrive(this, 100, 0.35);
            }
            if (relicRecoveryVuMark.equals(RelicRecoveryVuMark.RIGHT)) {
                EncoderDrive.createDrive(this, 400, 0.35);
            }
        }

        // Turn based on vumark
        if (stage == 5) {
            if (relicRecoveryVuMark.equals(RelicRecoveryVuMark.LEFT)) {
                BasicGyroTurn.createTurn(this, -28);
            }
            if (relicRecoveryVuMark.equals(RelicRecoveryVuMark.CENTER)) {
                BasicGyroTurn.createTurn(this, -27);
            }
            if (relicRecoveryVuMark.equals(RelicRecoveryVuMark.RIGHT)) {
                BasicGyroTurn.createTurn(this, 25);
            }
        }

        if (stage == 6) {
            EncoderDrive.createDrive(this, 120);
        }

        if (stage == 7) {
            lift.setRampPosition(Lift.RampServoPosition.SCORE);
            if (time.time() > 1) {
                next();
            }
        }

        if (stage == 8) {
            EncoderDrive.createDrive(this, -175);
        }

        if (stage == 9) {
            EncoderDrive.createDrive(this, 220);
        }

        if (stage == 10) {
            EncoderDrive.createDrive(this, -200);
        }

        if (stage == 11) {
            lift.setRampPosition(Lift.RampServoPosition.HOME);
            next();
        }

        if (telemetryEnabled) {
            telemetry.addData("Stage", stage);
            telemetry.addData("Heading", gyroUtils.getHeading());
            telemetry.addData("Pitch", gyroUtils.getPitch());
            telemetry.addData("Roll", gyroUtils.getRoll());
            telemetry.addData("VuMark", (relicRecoveryVuMark != null ? relicRecoveryVuMark.toString().toLowerCase() : "Unknown") );

        }
    }

}
