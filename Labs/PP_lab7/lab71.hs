{-1. (1p)
   Sa se implementeze triunghiul lui Pascal: 
   [[1], [1,1], [1,2,1], [1,3,3,1], [1,4,6,4,1] ... ]
   In triunghiul lui Pascal, pe fiecare linie valorile se obtin 
   din adunarea valorilor adiacente de pe linia anterioara, iar
   la stanga si la dreapta fiecarei linii se adauga valoarea 1
-}

{-2. (1p)
   Sa se implementeze fluxul numerelor prime prin metoda
   ciurului lui Eratostene.
-}

{-3. (1p)
   Sa se construiasca multimea partilor unei liste.
   Exemplu: pt lista [1,2,3]:
   [[], [1], [2], [3], [1,2], [1,3], [2,3], [1,2,3]]
-}

{-4. (1p)
   Sa se construiasca multimea partitiilor unui numar.
   Partitie a unui numar n = o lista de numere, ordonate crescator, 
   a caror suma este n.
   Exemplu: pt numarul 4:
   [[1,1,1,1], [1,1,2], [1,3], [2,2], [4]]
-}

{-5. (2p)
   Sa se construiasca fluxul numerelor prime circulare.
   Exemplu: 197 este prim circular intrucat toate rotatiile sale 
   (197, 971, 719) sunt numere prime.
   take 10 circularPrimes:
   [2,3,5,7,11,13,17,31,37,71]
-}

{-6. (4p)
   Implementati TDA-ul List, reprezentand liste de numere intregi, 
   impreuna cu operatiile:
   - cautare element in lista (True daca exista, False altfel)
   - eliminare duplicate din lista
   - interclasare a doua liste sortate dupa un operator dat ca parametru
   - impartire a unei liste in 2 liste (jumatati)
   - sortare prin interclasare
   - filterList (filtreaza lista dupa un anumit predicat)
   - mapList (mapeaza o functie pe fiecare element din lista)
   - verificare daca 2 liste sunt una permutarea celeilalte
-}