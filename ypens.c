// Раздел 1:
// Исчисление размера пенсии по возрастудля застрахованных лиц,
// которые после 1 января 1999 года не приобрели страховой стаж или
// у которых страховой стаж, приобретенный после указанной даты,
// составляет менее 5 лет

/*
#include <stdio.h>

int main {
    
    return 0;
}

int ypens(int numJobs, int years_n[], int months_n[], int Cpr_1[])
{
    float Pmin = 2777.86;
    int years_of_working_total, months_of_working;
    int n = numJobs;
    printf("Professional levels: \n"
           "1. For agricultural workers, handymen (I, II qualification category) and unskilled support staff \n"
           "2. For workers of average qualification (III, IV qualification category) \n"
           "3. For highly qualified workers (V, VI, VII, VIII qualification category) and specialists with secondary specialized education \n"
           "4. For specialists with higher education \n"
           "5. For managers at the level of a structural unit \n"
           "6. For heads of enterprises and their deputies \n");

    int years_n[n], months_n[n];
    int Cpr_1[n];
    for (int i=0;i<n;i++){
        printf(" Input years and months of working on this job: ");
        scanf("%d%d", &years_n[i], &months_n[i]);
        printf(" Input your professional level: ");
        scanf("%d", &Cpr_1[i]);
    }
    float Cpr[n];
    for (int i=0;i<n;i++){
        switch (Cpr_1[i]){
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

    float Tt_and_Cpr;
    for (int i=0;i<n;i++){
    Tt_and_Cpr=Tt_and_Cpr+(years_n[i]+(months_n[i]/12))*Cpr[i];
    }

    int Tmin = 15;
    float P = Pmin/(2*Tmin) * Tt_and_Cpr;
    printf("Here is your pension: %.2f \n", P);
    return 0;
}*/