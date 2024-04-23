// Раздел 2:
// Исчисление размера пенсии по возрасту из застрахованного
// дохода, полученного после 1 января 1999 года

// P = 1,35% * Tt * Vav,
// P = Pmin * Tt/Tl,

#include <stdio.h>

int main() {
    int Tl = 34, Tmin = 15;
    float Pmin = 2777.86;
    int k;
    printf("Input number of your jobs: ");
    scanf("%d", &k);

    float years[k], months[k], salary[k];
    float Tt = 0;
    float n, Zp = 0;
    float Tn = 34;

    for (int i = 0; i < k; i++) {
        printf("Input how many years and months you were working on job number %d:", i + 1);
        scanf("%f %f", &years[i], &months[i]);
        Tt += years[i] + months[i] / 12;
    }

    for (int i = 0; i < k; i++) {
        printf("Input your salary on job %d:", i + 1);
        scanf("%f", &salary[i]);
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
        printf("You will get stipend as 130 \n");
    else
        printf("Your Pension will be: %.2f", P);


    char choice;
    float Ta_years, Ta_months, Ta;
    printf("\nHave you worked after retirement age? (Y/N)");
    scanf(" %c", &choice);
    printf("\n%c", choice);
    if (choice == 'Y')
    {
        printf("Input how many years and months have you worked: ");
        scanf("%f%f", &Ta_years, &Ta_months);
        Ta = Ta_years + Ta_months/12;
        P = P + 2 * 0.01 * Ta * Vav;
        printf("Your Pension will be: %.2f", P);
    }
    else if (choice != 'Y' || choice != 'N')
        printf("\nERROR 404");
    else
        printf("Your Pension will be: %.2f \n", P);
    return 0;
}

// 3676,7