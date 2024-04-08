#include <stdio.h>

double med_salary(int sal[], int  n, int staj);
double formula(double s, int staj,int year, int n);
double defpens(int n);

int main()
{
    int n; // variable to store the level of disability
    int year;
    int staj = 0; // Initialize staj to 0
    int k; //variable to store number of employments

    printf("Enter the number of employments: ");
    scanf("%d", &k);

    int sal[k];
    double msal;
    double pension;
    double minpens;

    for (int i = 0; i < k; i++)
    {
        int j, l;
        printf("Enter the number of months you worked in company N%i: ", i+1);
        scanf("%d", &j);
        printf("Enter your salary in this company: ");
        scanf("%d", &l); // Use %lf for double
        sal[i] = j * l;
        staj += j;
    }

    printf("Enter your age: ");
    scanf("%d", &year);
    do
    {
        printf("Enter your level of disability: ");
        scanf("%d", &n);
    } while (n < 1 || n > 3);

    if ((year < 23 && staj/12 < 2) || (year > 23 && year < 29 && staj/12 < 4) || (year > 29 && year < 33 && staj/12 < 7) || (year > 33 && year < 37 && staj/12 < 10) || (year > 37 && year < 41 && staj/12 < 13) || (year > 41 && staj/12 < 15))
    {
        pension = defpens(n);
        printf("Your pension will be %f \n", pension);
        return 0;
    }

    msal = med_salary(sal, k, staj);
    pension = formula(msal, staj, year, n);
    minpens = defpens(n);
    if (pension < minpens) pension = minpens;
    printf("Your pension will be %f \n", pension);
}

double med_salary(int sal[], int  n, int staj)
{
    int sum = 0;
    for (int i = 0; i < n; i++) 
    {
        sum += sal[i];
    }
    return sum/staj;
}

double formula(double s, int staj,int year, int n)
{
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

double defpens(int n)
{
    switch (n)
    {
    case 1:
        return 1965.47;
        break;
    case 2:
        return 1834.43;
        break;
    case 3:
        return 1310.31;
    }
    return 0.0;
}
