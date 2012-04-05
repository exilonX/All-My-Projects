#include <iostream>
#include <string>
using namespace std;

int main() 
{
  /* Sirul din care stergem si sirul pe care il stergem. */
    /* Citim cele doua siruri. */
  string str1;
  getline(cin, str1);
  string str2;
  getline(cin, str2);

  /* Stergem intr-o bucla. */
  int found = str1.find(str2);
  while (found >= 0) {
        str1.erase(found, str2.size());
        cout << str1;
        found = str1.find(str2);
        }
  system("pause");
  return 0;
}

