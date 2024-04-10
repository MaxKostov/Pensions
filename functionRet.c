#include <stdio.h>
#include <jni_md.h>
#include <jni.h>
#include "javagui.h"

// Function declarations
double formula(double s, int staj, int year, int n);
double defpens(int n);
double med_salary(double sal[], int n, int staj);
double pension_calculation_function(int n, int year, int staj, int k, double sal[]);

// Definition of pension_calculation_function
double pension_calculation_function(int n, int year, int staj, int k, double sal[]) {
    double pension;
    double minpens;

    if ((year < 23 && staj/12 < 2) || (year > 23 && year < 29 && staj/12 < 4) || (year > 29 && year < 33 && staj/12 < 7) || (year > 33 && year < 37 && staj/12 < 10) || (year > 37 && year < 41 && staj/12 < 13) || (year > 41 && staj/12 < 15))
    {
        pension = defpens(n);
        return pension;
    }

    double msal = med_salary(sal, k, staj);
    pension = formula(msal, staj, year, n);
    minpens = defpens(n);
    if (pension < minpens) pension = minpens;
    return pension;
}

// Definition of formula function
double formula(double s, int staj, int year, int n) {
    double p;
    double staj_in_years = staj/12;
    int v = year - 18;
    if (v > 39) v = 39;
    switch (n)
    {
    case 1:
        p = 0.42 * s + (staj_in_years / v) * s * 0.1;
        break;
    case 2:
        p = 0.35 * s + (staj_in_years / v) * s * 0.1;
        break;
    case 3:
        p = 0.20 * s + (staj_in_years / v) * s * 0.1;
        break;
    }
    return p;
}

// Definition of defpens function
double defpens(int n) {
    switch (n)
    {
    case 1:
        return 1965.47;
    case 2:
        return 1834.43;
    case 3:
        return 1310.31;
    }
    return 0.0;
}

// Definition of med_salary function
double med_salary(double sal[], int n, int staj) {
    double sum = 0.0;
    for (int i = 0; i < n; i++) 
    {
        sum += sal[i];
    }
    return sum / staj;
}

// JNI function to call pension_calculation_function
JNIEXPORT jdouble JNICALL Java_javagui_pension
  (JNIEnv *env, jobject obj, jint n, jint year, jint staj, jint k, jdoubleArray sal) {
    jsize len = (*env)->GetArrayLength(env, sal);
    jdouble *salArray = (*env)->GetDoubleArrayElements(env, sal, 0);
    double result = pension_calculation_function(n, year, staj, k, salArray);
    (*env)->ReleaseDoubleArrayElements(env, sal, salArray, 0);
    return result;
}
