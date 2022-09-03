package com.z3db0y.davidlib;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorImplEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.Configurable;

@Config
public class Motor {
    boolean holdPosition = false;
    DcMotorEx motor;
    DcMotorEx.RunMode runMode;
    DcMotorEx.RunMode lastRunMode;

    public Motor(HardwareMap hardwareMap, String motorName) {
        this.motor = hardwareMap.get(DcMotorEx.class, motorName);
    }

    public void runToPosition(int targetPosition, double power) {
        this.lastRunMode = this.runMode;
        while(Math.abs(targetPosition - this.getCurrentPosition()) > 0) {
            this.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
            this.setPower(power);
        }
        this.setMode(this.lastRunMode);
        this.setPower(0);
    }

    public void runToPositionAsync(int targetPosition, double power) {
        this.setTargetPosition(targetPosition);
        this.motor.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);
        this.setPower(power);
    }

    public void setHoldPosition(boolean willHold) {
        this.holdPosition = willHold;
        holdPosition();
    }

    public boolean isHoldingPosition() {
        return this.holdPosition;
    }

    public void setPower(double power) {
        this.setPowerInternal(power);
        this.holdPosition();
    }

    private void setPowerInternal(double power) {
        this.motor.setPower(power);
    }

    public double getPower() {
        return this.motor.getPower();
    }

    public void setTargetPosition(int ticks) {
        this.motor.setTargetPosition(ticks);
    }

    public int getCurrentPosition() {
        return this.motor.getCurrentPosition();
    }

    public void setMode(DcMotorEx.RunMode runMode) {
        this.motor.setMode(runMode);
    }

    private void holdPosition() {
        if(this.getPower() == 0) {
            this.lastRunMode = this.runMode;
            this.setTargetPosition(this.getCurrentPosition());
            this.setPowerInternal(1);
        }
    }

    public void setDirection(DcMotorEx.Direction direction) {
        this.motor.setDirection(direction);
    }
}
