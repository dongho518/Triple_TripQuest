// IMissionAidlInterface.aidl
package com.example.kim.triple;

// Declare any non-default types here with import statements

interface IMissionAidlInterface {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    double get_speed();
    int getStep();
    float getBarometer();
}
