#include <stdio.h>
#include <jni_md.h>
#include <jni.h>
#include "javagui.h"

// Function to calculate pension
double calculatePension(int Tl, int Tmin, float Pmin, int k, float years[], float months[], float salary[], int choice, float Ta_years, float Ta_months) {
    float Tt = 0;
    float n, Zp = 0;
    float Tn = 34;

    for (int i = 0; i < k; i++) {
        Tt += years[i] + months[i] / 12;
    }

    for (int i = 0; i < k; i++) {
        Zp += salary[i];
    }
    Zp = Zp/k;
    float coni=0;
    for (int i=0;i<(int)Tt;i++)
    {
        coni = coni + 28.66*0.01*Zp;
        Zp = Zp/1.16;
    }
    float Vav = 29 * 0.01 * coni;
    float P = 1.35 * 0.01 * Tt * Vav;

    if (Tt>Tmin && Tt<Tl)
        P = Pmin * Tt/Tl;
    else if (P<Pmin)
        P = Pmin;

    if (Tt<Tmin)
        P = 130.0;

    float Ta;
    if (choice == 1)
    {
        Ta = Ta_years + Ta_months/12;
        P = P + 2 * 0.01 * Ta * Vav;
    }
    
    // Returning the calculated pension and the total years worked
    return P;
}


JNIEXPORT jdouble JNICALL Java_javagui_calculatePension
  (JNIEnv *env, jobject obj, jint Tl, jint Tmin, jfloat Pmin, jint k, jfloatArray yearsArray, jfloatArray monthsArray, jfloatArray salaryArray, jint choice, jfloat Ta_years, jfloat Ta_months) {
    
    // Convert jfloatArray to C arrays
    jfloat *years = (*env)->GetFloatArrayElements(env, yearsArray, NULL);
    jfloat *months = (*env)->GetFloatArrayElements(env, monthsArray, NULL);
    jfloat *salary = (*env)->GetFloatArrayElements(env, salaryArray, NULL);
    
    // Call the C function to calculate the pension
    double pension = calculatePension(Tl, Tmin, Pmin, k, years, months, salary, choice, Ta_years, Ta_months);
    
    // Release the allocated memory
    (*env)->ReleaseFloatArrayElements(env, yearsArray, years, 0);
    (*env)->ReleaseFloatArrayElements(env, monthsArray, months, 0);
    (*env)->ReleaseFloatArrayElements(env, salaryArray, salary, 0);
    
    // Return the calculated pension
    return pension;
}
