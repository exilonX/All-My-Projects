#include <iostream>
#include<string>
using namespace std;

int main() 
{
  /* TODO: Declarati doua string-uri, cititi-le de la tastatura si stergeti din
   * primul toate aparitiile celui de-al doilea.
   *
   * Pentru a declara un string va trebui sa includeti cu o directiva #include,
   * header-ul clasei (<string>).
   */
  string str1 ;
  getline(cin, str1);
  
  string str2 ;
  getline(cin, str2);
  string::iterator it;
  while(str1.find(str2)<=str1.size()) {
     str1.erase(str1.find(str2),str2.size());
  }
  
  cout<<str1;
  system("pause");
  return 0;
}

