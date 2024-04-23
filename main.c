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
    double Zp = sum / staj;
    float coni=0;
    for (int i=0;i<(int)staj;i++)
    {
        coni = coni + 28.66*0.01*Zp;
        Zp = Zp/1.16;
    }
    float Vav = 29 * 0.01 * coni;
    return Vav;
}

double calPension(int n, int years_n[], int months_n[], int Cpr_1[]) {
    float Pmin = 2777.86;

    // Array to store professional coefficients
    float Cpr[n];

    // Calculate professional coefficients based on input levels
    for (int i = 0; i < n; i++) {
        switch (Cpr_1[i]) {
            case 1:
                Cpr[i] = 1;
                break;
            case 2:
                Cpr[i] = 1.2;
                break;
            case 3:
                Cpr[i] = 1.5;
                break;
            case 4:
                Cpr[i] = 1.8;
                break;
            case 5:
                Cpr[i] = 2;
                break;
            case 6:
                Cpr[i] = 3;
                break;
        }
    }

    // Calculate the total years of working experience with professional coefficients
    float Tt_and_Cpr = 0;
    for (int i = 0; i < n; i++) {
        Tt_and_Cpr += (years_n[i] + (months_n[i] / 12.0)) * Cpr[i];
    }

    // Minimum total working years for pension calculation
    int Tmin = 15;

    // Calculate pension
    double P = Pmin / (2 * Tmin) * Tt_and_Cpr;

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

JNIEXPORT jdouble JNICALL Java_javagui_cp
  (JNIEnv *env, jobject obj, jint n, jintArray yearsN, jintArray monthsN, jintArray Cpr1) {
    jint *years_n = (*env)->GetIntArrayElements(env, yearsN, NULL);
    jint *months_n = (*env)->GetIntArrayElements(env, monthsN, NULL);
    jint *Cpr_1 = (*env)->GetIntArrayElements(env, Cpr1, NULL); 

    double pension = calPension(n, years_n, months_n, Cpr_1);

    (*env)->ReleaseIntArrayElements(env, yearsN, years_n, 0);
    (*env)->ReleaseIntArrayElements(env, monthsN, months_n, 0);
    (*env)->ReleaseIntArrayElements(env, Cpr1, Cpr_1, 0);
    
    return pension;
  }

  JNIEXPORT jdouble JNICALL Java_javagui_pension
  (JNIEnv *env, jobject obj, jint n, jint year, jint staj, jint k, jdoubleArray sal) {
    jsize len = (*env)->GetArrayLength(env, sal);
    jdouble *salArray = (*env)->GetDoubleArrayElements(env, sal, 0);
    double result = pension_calculation_function(n, year, staj, k, salArray);
    (*env)->ReleaseDoubleArrayElements(env, sal, salArray, 0);
    return result;
}