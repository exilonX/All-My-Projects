#include <iostream>
#include <fstream>
#include <queue>

#include "Maze.h"

typedef std::pair<int, int> Coord;
const Coord NOWHERE(-1, -1);

const int dirLin[4] = { -1, 0, 1, 0 };
const int dirCol[4] = { 0, 1, 0, -1 };

enum Color {
  WHITE,
  GRAY,
  BLACK
};

using namespace std;

void find_exit(Maze& maze, Coord source)
{
  /* Pentru a reconstitui drumul inainte de iesirea din functie,
   * vom folosi o matrice de parinti. */
  Coord parent[maze.get_height()][maze.get_width()];
  int color[maze.get_height()][maze.get_width()];

  for (unsigned int i = 0; i < maze.get_height(); ++i) {
    for (unsigned int j = 0; j < maze.get_width(); ++j) {
      parent[i][j] = NOWHERE;
      color[i][j] = WHITE;
    }
  }

  /* TODO: Implementati o cautare care sa depisteze drumul cel mai scurt de la
   * pozitia "source" la o iesire din labiring (vezi functia "is_exit_point").
   *
   * Dupa ce ati calculat drumul, inainte de iesirea din functie, trebuie sa il
   * marcati celula cu celula folosind functia "mark_solution_step" din clasa
   * Maze. */
	Coord exit;
	int ok = 1;

	queue<Coord> q;
	q.push(source);
	maze.mark_solution_step(source);

   	while (!q.empty() && ok){
		Coord next = q.front();
		
		/* Verificam vecinii. */
		for (int i = 0; i < 4; i++){
			Coord v = Coord(next.first + dirLin[i], next.second + dirCol[i]);
			if (parent[v.first][v.second] == NOWHERE){
				if (maze.is_exit_point(v)) {
					parent[v.first][v.second] = next;
					exit = v;
					ok = 0;
				}
				else{
					if (maze.is_walkable(v)){
						q.push(v);
						parent[v.first][v.second] = next;
					}
				}
			}
		}
	
		q.pop();
	}

	while (parent[exit.first][exit.second] != source){
		maze.mark_solution_step(exit);
		exit = parent[exit.first][exit.second];
	}
	maze.mark_solution_step(exit);
	
}

int main()
{
  /* Citim o harta din fisierul de intrare. */
  std::ifstream in("src-lab6/Labirint.txt");
  Maze maze;
  unsigned int lineTrudy, columnTrudy;
  in >> maze >> lineTrudy >> columnTrudy;

  /* Calculam pe ea drumul din labirint. */
  find_exit(maze, Coord(lineTrudy, columnTrudy));

  /* Si afisam drumul final. */
  std::cout << "Labirintul cu drumul marcat spre iesire este: " << std::endl
      << maze;

  return 0;
}

