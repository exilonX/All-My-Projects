#include <iostream>
#include <queue>

/* VectorIO.h descrie doi operatori cu ajutorul carora puteti citi/scrie
 * vectori de la tastatura sau din fisiere ca si cum ati lucra cu date scalare
 * (int, float, etc.). */
#include "VectorIO.h"
using namespace std;

int main()
{
  /* TODO: Declarati un vector de numere float. */
 int n;
 float x;
 vector<float> v;
 cout << "citeste n ";
 cin >> n;
  cout << "citeste X ";
 cin >> x;
 for(int i = 0; i < n; i++) {
      v.push_back(x);
       cout << "citeste X ";
      cin >> x;
      }
 
 priority_queue<float> coada;
 
 for (int i = 0; i < n; i++) {
    coada.push(v[i]);
 }
 v.clear();
 for (int i = 0; i < n; i++) {
     float element = coada.top();
     v.push_back(element);
    // cout << coada.top() << ", ";
     coada.pop();
 }
 cout << v << "\n";
   system("pause");
  return 0;
}

