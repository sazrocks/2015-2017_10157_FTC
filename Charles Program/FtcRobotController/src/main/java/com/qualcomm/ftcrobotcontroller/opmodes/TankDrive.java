package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
//import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.Range;

/**
 * Created by Charles II on 9/19/2015.
 */
public class TankDrive extends LinearOpMode {

    static float leftMotorMaxPower = (float) 0.2;
    static float rightMotorMaxPower = (float) 0.2;

    DcMotor motorRight;
    DcMotor motorLeft;

    @Override
    public void runOpMode() throws InterruptedException{

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

        motorRight = hardwareMap.dcMotor.get("motor_right");
        motorLeft = hardwareMap.dcMotor.get("motor_left");
        motorLeft.setDirection(DcMotor.Direction.REVERSE);
        gamepad1.setJoystickDeadzone(0.05F);

        int temp = 0;
        float power = 0.0F;
        while(true) {
            if (gamepad1.left_bumper) {

                while (!gamepad1.left_bumper) {
                    if (gamepad1.b) {

                        power = power + 0.05F;
                        temp = temp + 5;
                        sleep(500);
                        telemetry.addData("Joystick deadzone:", temp);
                        while (gamepad1.b) {
                            power = power + 0.05F;
                            temp = temp + 5;
                            telemetry.addData("Joystick deadzone:", temp);
                            sleep(500);
                        }
                    }
                    if (gamepad1.x) {

                        power = power - 0.05F;
                        temp = temp - 5;
                        sleep(500);
                        telemetry.addData("Joystick deadzone:", temp);
                        while (gamepad1.b) {
                            power = power - 0.05F;
                            temp = temp - 5;
                            telemetry.addData("Joystick deadzone:", temp);
                            sleep(500);
                        }
                    }
                    sleep(5);
                    telemetry.addData("Joystick deadzone:", temp);
                    gamepad1.setJoystickDeadzone(power);
                    sleep(5);
                }

            }


            if (gamepad1.right_bumper) {

                leftMotorMaxPower = 1;
                rightMotorMaxPower = 1;

            } else {
                leftMotorMaxPower = (float) 0.2;
                rightMotorMaxPower = (float) 0.2;
            }


            float left = -gamepad1.left_stick_y;
            float right = -gamepad1.right_stick_y;

            left = left * leftMotorMaxPower;
            right = right * rightMotorMaxPower;

            right = Range.clip(right, -rightMotorMaxPower, rightMotorMaxPower);
            left = Range.clip(left, -leftMotorMaxPower, leftMotorMaxPower);

            motorLeft.setPower(left);
            motorRight.setPower(right);

            telemetry.addData("1-motor left power", left);
            telemetry.addData("2-motor right power", right);
            telemetry.addData("Joystick deadzone", power);
            sleep(10);
        }




    }
}
