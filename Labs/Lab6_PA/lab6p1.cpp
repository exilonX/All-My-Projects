#include <iostream>
#include <fstream>
#include <queue>

#include "Maze.h"
#include "PairIO.h"

typedef std::pair<int, int> Coord;
const Coord NOWHERE(-1, -1);

const int dirLin[4] = { -1, 0, 1, 0 };
const int dirCol[4] = { 0, 1, 0, -1 };

/* 0 = alb;
*  1 = gri
*  2 = negru */


void find_exit(Maze& maze, Coord source)
{
  /* Pentru a reconstitui drumul inainte de iesirea din functie,
   * vom folosi o matrice de parinti. */
  Coord parent[maze.get_height()][maze.get_width()];
  int colour[maze.get_height()][maze.get_width()];
  Coord exit;
  
  for (unsigned int i = 0; i < maze.get_height(); ++i) {
    for (unsigned int j = 0; j < maze.get_width(); ++j) {
      parent[i][j] = NOWHERE;
      colour[i][j] = 0; 
    }
}
    
    std::queue<Coord> myQueue;    
    myQueue.push(source);
    maze.mark_solution_step(source);
    
    colour[source.first][source.second] = 1;
    
    while (!myQueue.empty()){
          Coord u = myQueue.front();
           
          for (int i=0; i<4; i++){
              Coord succ = Coord(u.first+dirLin[i],u.second+dirCol[i]);
              
              if ( succ.first>=0 && succ.second>=0
                   && succ.first<maze.get_width() &&succ.second<maze.get_height() &&
                   colour[succ.first][succ.second] ==0) 
                if (maze.is_walkable(succ))
                {
                   parent[succ.first][succ.second] = u;
                   colour[succ.first][succ.second] = 1;
                   
                  if (maze.is_exit_point(succ)){
                        exit=succ;
                        break;
                        }
                  myQueue.push(succ);
                  }
                  else colour[succ.first][succ.second] = 1;

                  }//endfor
                  myQueue.pop();
              }
       
    while (exit != source){
          maze.mark_solution_step(exit);
          exit= parent[exit.first][exit.second];          
          }
          
   
    return;
  }
  
  
  

  /* TODO: Implementati o cautare care sa depisteze drumul cel mai scurt de la
   * pozitia "source" la o iesire din labiring (vezi functia "is_exit_point").
   *
   * Dupa ce ati calculat drumul, inainte de iesirea din functie, trebuie sa il
   * marcati celula cu celula folosind functia "mark_solution_step" din clasa
   * Maze. */


int main()
{
  /* Citim o harta din fisierul de intrare. */
  std::ifstream in;
  in.open("Labirint.txt");
  
  std::cout << in;
  Maze maze;
  unsigned int lineTrudy, columnTrudy;
  in >> maze >> lineTrudy >> columnTrudy;
  std::cout << "Labirintul de intrare: " << std::endl
      << maze;

  /* Calculam pe ea drumul din labirint. */
  find_exit(maze, Coord(lineTrudy, columnTrudy));

  /* Si afisam drumul final. */
  std::cout << "Labirintul cu drumul marcat spre iesire este: " << std::endl
      << maze;

  system("pause");
  return 0;
}

