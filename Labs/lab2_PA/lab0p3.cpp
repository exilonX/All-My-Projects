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
  vector<float> v;
  priority_queue<float> coada;
  float x;
  cout<<"numarul\n";
  int n ; cin>>n;
  cin>>x;
  for(int i =0 ;i<=n;i++) {
      v.push_back(x);
      cin>>x;
      }
  
  cout<<v<<"\n";
  for(int i=0;i<=n;i++) {
       coada.push(v[i]);
       }
  for(int i=0;i<=n;i++) {
       cout<<coada.top()<<"  ";
       coada.pop();
       }
 
   system("pause");
  return 0;
}

