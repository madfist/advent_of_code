#include <iostream>
#include <cstdlib>
#include <string>
#include <fstream>
#include <streambuf>
#include <list>

#include "../common/utils.h"

int dialpad[] = {1, 2, 3, 4, 5, 6, 7, 8, 9};

struct ButtonPosition {
  int x,y;
  ButtonPosition(): x(1), y(1) {}
  void left() { x = (x>0) ? x-1 : x; }
  void right() { x = (x<2) ? x+1 : x; }
  void up() { y = (y>0) ? y-1 : y; }
  void down() { y = (y<2) ? y+1 : y; }
  int operator()() { return dialpad[3*y + x]; }
  int operator()(int a, int b) { return dialpad[3*b + a]; }
};

int dialpad_fancy[] = {
  14, 14,  1, 14, 14,
  14,  2,  3,  4, 14,
  5,   6,  7,  8,  9,
  14, 10, 11, 12, 14,
  14, 14, 13, 14, 14
};

struct ButtonPositionFancy {
  int x,y;
  ButtonPositionFancy(): x(0), y(3) {}
  void left() { x = (x>0 && (*this)(x-1,y) != 14) ? x-1 : x; }
  void right() { x = (x<4 && (*this)(x+1,y) != 14) ? x+1 : x; }
  void up() { y = (y>0 && (*this)(x,y-1) != 14) ? y-1 : y; }
  void down() { y = (y<4 && (*this)(x,y+1) != 14) ? y+1 : y; }
  int operator()() { return dialpad_fancy[5*y + x]; }
  int operator()(int a, int b) { return dialpad_fancy[5*b + a]; }
};

int main(int argc, char* argv[]) {
  if (argc < 2) {
    std::cout << "Usage: " << argv[0] << " <file>" << std::endl;
    exit(-1);
  }
  std::ifstream input(argv[1]);
  std::string steps((std::istreambuf_iterator<char>(input)),std::istreambuf_iterator<char>());
  // std::cout << steps << std::endl;
  std::list<std::string> instructions;
  tokenize(steps, "\n", instructions);

  ButtonPosition pos;
  ButtonPositionFancy fpos;

  std::string combination;
  std::string combination_fancy;
  while (instructions.size() != 0) {
    // std::cout << instructions.front() << std::endl;
    std::string steps = instructions.front();
    for (int i=0; i<steps.length(); ++i) {
      switch(steps[i]) {
        case 'U': pos.up(); fpos.up(); break;
        case 'D': pos.down(); fpos.down(); break;
        case 'L': pos.left(); fpos.left(); break;
        case 'R': pos.right(); fpos.right(); break;
      }
      // std::cout << "s" << fpos() << ", ";
    }
    // std::cout << "| ";

    combination += std::to_string(pos());
    switch(fpos()) {
      case 10: combination_fancy += "A"; break;
      case 11: combination_fancy += "B"; break;
      case 12: combination_fancy += "C"; break;
      case 13: combination_fancy += "D"; break;
      default: combination_fancy += std::to_string(fpos());
    }
    instructions.pop_front();
  }
  std::cout << "Combinations: " << combination << " " << combination_fancy << std::endl;

}