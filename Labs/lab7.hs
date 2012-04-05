{-1. (1p)
   Sa se implementeze triunghiul lui Pascal: 
   [[1], [1,1], [1,2,1], [1,3,3,1], [1,4,6,4,1] ... ]
   In triunghiul lui Pascal, pe fiecare linie valorile se obtin 
   din adunarea valorilor adiacente de pe linia anterioara, iar
   la stanga si la dreapta fiecarei linii se adauga valoarea 1
-}

pascal = iterate (\row -> zipWith (+) ([0] ++ row) (row ++ [0])) [1]

{-2. (1p)
   Sa se implementeze fluxul numerelor prime prin metoda
   ciurului lui Eratostene.
-}

primes = eratostene [2..]
  where
    eratostene (p:xs) = p : eratostene [x|x <- xs, (mod x p) /= 0]

{-3. (1p)
   Sa se construiasca multimea partilor unei liste.
   Exemplu: pt lista [1,2,3]:
   [[], [1], [2], [3], [1,2], [1,3], [2,3], [1,2,3]]
-}

subsets [] = [[]]
subsets (x:xs) = subsets xs ++ map (x :)(subsets xs)

{-4. (1p)
   Sa se construiasca multimea partitiilor unui numar.
   Partitie a unui numar n = o lista de numere, ordonate crescator, 
   a caror suma este n.
   Exemplu: pt numarul 4:
   [[1,1,1,1], [1,1,2], [1,3], [2,2], [4]]
-}

parts n = partsHelper n n 
		where partsHelper c n
				| n == 0 = [[]]
				| otherwise = [p++[i]| i<-[1..(min c n)], p <- partsHelper i (n-i)]

{-5. (2p)
   Sa se construiasca fluxul numerelor prime circulare.
   Exemplu: 197 este prim circular intrucat toate rotatiile sale 
   (197, 971, 719) sunt numere prime.
   take 10 circularPrimes:
   [2,3,5,7,11,13,17,31,37,71]
-}

prime 1 = False
prime 2 = True
prime n = filter (divides n) primes == []
               where numbers = [x | x <- [2..n-1], x*x <= n]
                     primes  = filter prime numbers
divides a b = (mod a b == 0)

count 0 = 0
count n = 1 + (count (floor (fromIntegral n/10)))

rotate n nr
		| nr == 0 = n
		| otherwise = rotate (((10^((count n) - 1))*(mod n 10)) + (floor (fromIntegral n/10))) (nr-1)


primeList n l nr
		| nr == (count n) = l
		| otherwise = primeList n ((rotate n nr):l) (nr+1)


circularPrimes = [x| x<-primes , (length (filter prime (primeList x [] 0))) == (count x)]

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

data List = Nil | Cons Int List
	deriving (Show, Eq)

list1 = Cons 4 (Cons 5 (Cons 6 Nil))
list2 = Cons 4 (Cons 5 (Cons 6 Nil))
list3 = Cons 7 (Cons 4 (Cons 4 (Cons 3 (Cons 6 (Cons 2 Nil)))))

myHead (Cons x y) = x
myTail (Cons x y) = y

myLength Nil = 0
myLength (Cons a b) = 1 + (myLength b)

myEqual Nil Nil = True
myEqual Nil (Cons a b) = False
myEqual (Cons a b) Nil = False
myEqual (Cons a b) (Cons x y)
	| a == x = myEqual b y
	| otherwise = False	

member el Nil = False
member el (Cons a b)
	| el == a = True
	| otherwise = member el b

noDuplicate Nil = Nil
noDuplicate (Cons a b) 
	| member a b = (noDuplicate b)
	| otherwise = Cons a (noDuplicate b)

merge op Nil Nil = Nil
merge op Nil (Cons a b) = (Cons a b)
merge op (Cons a b) Nil = (Cons a b)
merge op (Cons a b) (Cons x y) 
	| op a x = (Cons a (merge op b (Cons x y)))
	| otherwise =  (Cons x (merge op y (Cons a b)))

myTake n Nil = Nil
myTake n (Cons a b)
	|n == 0 = Nil
	|otherwise = (Cons a (myTake (n - 1) b))

myDrop n Nil = Nil
myDrop 0 (Cons a b) = (Cons a b)
myDrop n (Cons a b)
	|n == 1 = b
	|otherwise = (myDrop (n - 1) b)


split (Cons a b) = (myTake n (Cons a b), myDrop n (Cons a b))
	where n = floor ((myLength (Cons a b)) / 2)

mergeSort Nil = Nil
mergeSort (Cons a b)
	|(myLength (Cons a b)) > 1 = merge (<) (mergeSort (fst aux)) (mergeSort(snd aux))
	| otherwise = (Cons a b)
	where aux = split (Cons a b)


filterList op Nil = Nil
filterList op (Cons a b)
	| op a = (Cons a (filterList op b))
	| otherwise = (filterList op b)

mapList op Nil = Nil
mapList op (Cons a b) = (Cons (op a) (mapList op b))

checkPerm (Cons a b) (Cons x y) = myEqual (mergeSort (Cons a b)) (mergeSort (Cons x y))
