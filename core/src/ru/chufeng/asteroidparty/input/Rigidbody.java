package ru.chufeng.asteroidparty.input;

/**
 * <p>Object can collide</p>
 *
 * @author Chufeng
 * @version 1.0
 */
public interface Rigidbody {
    float getVerticalSpeed();

    void setVerticalSpeed(float verticalSpeed);

    float getHorizontalSpeed();

    void setHorizontalSpeed(float horizontalSpeed);

    float getRotationSpeed();

    void setRotationSpeed(float rotationSpeed);

    float getMass();

    void setMass(float mass);
}
