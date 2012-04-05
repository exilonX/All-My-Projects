/* TODO: Includeti fisierele necesare pentru operatiile de IO, pentru vectori si
 * pentru functia de sortare. Puteti gasi respectivele headere in documentatia
 * de pe http://cplusplus.com */

/* Similar cu VectorIO.h, PairIO.h contine codul necesar pentru a putea
 * citi/scrie perechi de obiecte din/in stream-uri. */
#include "VectorIO.h"
#include <iostream>
#include <vector>
#include <algorithm>
#include "PairIO.h"
using namespace std;


typedef std::pair<float,float> Point;

int main()
{
    vector<Point> v;
    cin >> v;
    cout << v << "\n";
    sort(v.begin(), v.end());
    cout << v;
 
    system("pause");
    return 0;
}

