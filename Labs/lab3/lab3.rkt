;; Functii curry si uncurry
;;--------------------------

;; 1. (1p)
;; sa se implementeze functia uncurry->curry, dupa urmatoarea specificatie:
;; fiind data o functie uncurry f care ia in Scheme 2 argumente,
;; (uncurry->curry f) trebuie sa produca o functie curry cu acelasi efect ca functia f
;; practic uncurry->curry este o functie care primeste mai intai ca parametru o functie
;; uncurry, apoi argumentele acesteia (pe rand, pt ca altfel (uncurry->curry f) nu ar 
;; fi o functie curry) si face exact ce facea f asupra acelor argumente
;; (((uncurry->curry >) 5) 4) => #t
;(define uncurry->curry
;  (lambda (f)
;    (lambda (x)
;      (lambda (y)

;; 2. (1p)
;; sa se implementeze functia curry->uncurry (procesul invers celui de la punctul 1)
;; acum (curry->uncurry g) va produce o functie uncurry dintr-o functie curry g de 
;; 2 argumente
; (define curry->uncurry 

;; Functionale
;;------------

;; functionala = o functie care primeste functii ca parametri sau returneaza functii
;; in specificatiile de mai jos
;; f = functie binara
;; p = predicat (functie care testeaza o proprietate a argumentului
;; sau si intoarce true sau false)
;; L, L1, L2 = liste
;; x = element in lista

;; 3. (0.5p)
;; sa se implementeze functionala foldleft
;; (foldleft f init lst) acumuleaza aplicatia functiei binare f pe lista lst in felul urmator:
;; mai intai (f init (car lst)) => rezultat1
;; apoi (f rezultat1 (cadr lst)) => rezultat2, (f rezultat2 (caddr lst)) => rezultat3 etc
;; pana la (f rezultat-pana-acum ultimul-element-din-lst)

;; 4. (0.5p)
;; sa se obtina inversa unei liste ca o aplicatie a lui foldleft

;; 5. (0.5p)
;; sa se implementeze functionala zip-with 
;; (zip-with f L1 L2) = lista aplicarilor lui f asupra elementelor de pe 
;; pozitii corespunzatoare in L1 si L2
;; (zip-with * '(2 4 6) '(3 5 7 9)) => (6 20 42)

;; 6. (0.5p)
;; sa se obtina zip-fun din zip-with
;; (zip-fun L1 L2) = lista aplicarilor functiilor din L2 asupra elementelor de pe 
;; pozitiile corespunzatoare din L1
;; (zip-fun (list 1 2 '(1 2 3) 4 #t) (list odd? list? list? even? boolean?)) => (#t #f #t #t #t)

;; 7. (0.5p)
;; sa se implementeze functionala forall?
;; (forall? p L) = true daca oricare ar fi x in L avem p(x) adevarat
;; (forall? even? '(2 4 6 8)) => #t

;; 8. (1p)
;; sa se implementeze functia prime? dupa specificatia:   
;; pt n > 1, (prime? n) <=> forall d in 2..n/2 . n mod d <> 0
;; se cere NEAPARAT sa folositi o functie anonima pt reprezentarea 
;; predicatului n mod d <> 0, definita direct ca parametru pt forall?
;; (modulo 19 4) - asta e functia mod in Scheme...
;; (prime? 2) => #t
;; (prime? 17) => #t
;; (prime? 10) => #f

;; 9. (0.5p)
;; sa se determine lista tuturor numerelor prime <= n
;; (primes-up-to 20) => (2 3 5 7 11 13 17 19)

;; 10. (2p)
;; sa se implementeze produsul cartezian a n liste dintr-o lista de liste
;; (cartesian-product '((1 2) (a b) (x y z))) =>
;; ((1 a x) (1 a y) (1 a z) (1 b x) (1 b y) (1 b z) (2 a x) (2 a y) (2 a z) (2 b x) (2 b y) (2 b z))
;; nu neaparat in aceasta ordine
;; trebuie sa obtineti o singura lista cu toate tuplurile, nu tot felul de liste imbricate!
;; util: functia uncurry->curry

;; 11. (2p)
;; o functie care calculeaza transpusa unei matrici
;; (transpose '((1 2 3 4) (5 6 7 8) (9 10 11 12)))
;; => '((1 5 9) (2 6 10) (3 7 11) (4 8 12))

;; 12. (bonus - 2p)
;; Se da o expresie in Scheme formata doar din aplicatiile functiilor:
;; +, *, -, /, cons, append, list si corespondentele lor curry, definite
;; curry+, curry*, curry-, curry/, curry-cons, curry-append, curry-list
;; Expresia data poate contine greseli in sensul ca functii uncurry 
;; au fost aplicate de parca ar fi functii curry. (exemplu: ((+ 1) 2))
;; Scrieti functia curry-corrector care primeste o astfel de expresie (data sub forma de lista)
;; si corecteaza greselile existente, inlocuind functiile aplicate incorect
;; cu formele lor curry.
;; Functii ajutatoare: number?, boolean?, list?, symbol?, equal?
;;
;; (symbol? '+) => #t
;; (symbol? +) => #f
;; (symbol? (car '(+ 1 2))) => #t
;; (equal? '+ (car '(+ 1 2))) => #t
;; (equal? + '+) => #f
;;
;; Exemple:
;;
;; (curry-corrector '(+ 1 2)) => (+ 1 2)
;; (curry-corrector '((+ 1) 2)) => ((curry+ 1) 2)
;; (curry-corrector '(cons (- 1 ((* 3) 2)) (list ((+ 1) 3) (* 2 2) (/ 3 ((- 1) 2))))) => 
;;                => (cons (- 1 ((curry* 3) 2)) (list ((curry+ 1) 3) (* 2 2) (/ ((curry- 1) 2))))
;; 
;; Folositi eval pe expresia rezultata pentru a verifica corectitudinea. (eval expresie)

;(define curry+
;(define curry*
; etc

;; E este o aplicatie (de tip curry sau uncurry) a unui operator asupra a 2 argumente
;; (curry? E) va returna true daca operatorul isi primeste argumentele pe rand
;(define (curry? E) 

;(define (curry-corrector E)

(define (corrector-eval E)
  (eval (curry-corrector E)))

;(corrector-eval '(cons (- 1 ((* 3) 2)) (list ((+ 1) 3) (* 2 2) (/ 3 ((- 1) 2)))))
;(corrector-eval '((+ 1) 2))

