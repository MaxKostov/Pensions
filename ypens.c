#include <stdio.h>
#include <jni_md.h>
#include <jni.h>
#include "javagui.h"

double calculatePension(int n, int years_n[], int months_n[], int Cpr_1[]) {
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

JNIEXPORT jdouble JNICALL Java_javagui_cp
  (JNIEnv *env, jobject obj, jint n, jintArray yearsN, jintArray monthsN, jintArray Cpr1) {
    jint *years_n = (*env)->GetIntArrayElements(env, yearsN, NULL);
    jint *months_n = (*env)->GetIntArrayElements(env, monthsN, NULL);
    jint *Cpr_1 = (*env)->GetIntArrayElements(env, Cpr1, NULL); 

    double pension = calculatePension(n, years_n, months_n, Cpr_1);

    (*env)->ReleaseIntArrayElements(env, yearsN, years_n, 0);
    (*env)->ReleaseIntArrayElements(env, monthsN, months_n, 0);
    (*env)->ReleaseIntArrayElements(env, Cpr1, Cpr_1, 0);
    
    return pension;
  }


/*
int main() {
    int n;
    printf("Enter the number of jobs: ");
    scanf("%d", &n);

    int years_n[n], months_n[n], Cpr_1[n];
    for (int i = 0; i < n; i++) {
        printf("Input years and months of working on job %d: ", i + 1);
        scanf("%d %d", &years_n[i], &months_n[i]);
        printf("Input professional level for job %d: ", i + 1);
        scanf("%d", &Cpr_1[i]);
    }

    // Call the function to calculate the pension
    double pension = calculatePension(n, years_n, months_n, Cpr_1);
    printf("Here is your pension: %.2f\n", pension);

    return 0;
}
*/