#include <iostream>
#include <cstdlib>
#include <string>
#include <fstream>
#include <streambuf>
#include <list>

struct Step {
  int x0,y0,x1,y1;
  Step() : x0(0), y0(0), x1(0), y1(0) {}
  void set(int a0, int b0, int a1, int b1) {
    x0 = a0; y0 = b0; x1 = a1; y1 = b1;
  }
  bool crossCheck(const Step& other, Step& cross) {
    if (x1 > x0 && x0 < other.x0 && other.x0 <= x1) { //EAST
      // if (other.x0 == other.x1) {
      //   if (x0 < other.x0 && other.x0 <= x1) {
      //     cross.set(other.x0, y0, other.x0, y0);
      //     return true;
      //   }
      // } else if (other.x0 < x1 && x1 <= other.x1) {
      //   cross.set(other.x0, y0, other.x0, y0);
      //   return true;
      // }
      cross.set(other.x0, y0, other.x0, y0);
      return true;
    } else if (x1 < x0 && x1 <= other.x0 && other.x0 < x0)
    return false;
  }
};

class Walker {
public:
  Walker() : ft(), steps() {
    Step step;
    steps.push_back(step);
  }
  void instruction(const std::string& inst) {
    std::string steps = inst.substr(1);
    if (inst.c_str()[0] == 'L') {
      goLeft(std::stoi(steps));
    } else {
      goRight(std::stoi(steps));
    }
  }
  int getShortestPath() {
    Step s = steps.back();
    return abs(s.x1) + abs(s.y1);
  }
  int getFirstTwice() {
    return abs(ft.x1) + abs(ft.y1);
  }

private:
  void goLeft(int step) {
    Step s = steps.back();
    Step next;
    if (s.x1 > s.x0) { //EAST
      next.set(s.x1, s.y1, s.x1, s.y1+step);
    } else if (s.x1 < s.x0) { //WEST
      next.set(s.x1, s.y1, s.x1, s.y1-step);
    } else if (s.y1 > s.y0) { //SOUTH
      next.set(s.x1, s.y1, s.x1-step, s.y1);
    } else { //NORTH
      next.set(s.x1, s.y1, s.x1+step, s.y1);
    }
    steps.push_back(next);
  }
  void goRight(int step) {
    Step s = steps.back();
    Step next;
    if (s.x1 > s.x0) { //EAST
      next.set(s.x1, s.y1, s.x1, s.y1-step);
    } else if (s.x1 < s.x0) { //WEST
      next.set(s.x1, s.y1, s.x1, s.y1+step);
    } else if (s.y1 > s.y0) { //SOUTH
      next.set(s.x1, s.y1, s.x1+step, s.y1);
    } else { //NORTH
      next.set(s.x1, s.y1, s.x1-step, s.y1);
    }
    steps.push_back(next);
  }

  Step ft;
  std::list<Step> steps;
};

void tokenize(const std::string& data, const std::string& delim, std::list<std::string>& l) {
  size_t pos = 0, last = 0;
  while ((pos = data.find_first_of(delim, last)) != std::string::npos) {
    //std::string step = data.substr(last, pos-last);
    //std::cout << "LAST " << last << " POS " << pos << " STEP " << step << std::endl;
    l.push_back(data.substr(last, pos-last));
    //std::cout << "T: " << l.back() << std::endl;
    last = pos + 2;
  }
  l.push_back(data.substr(last));
}

int main(int argc, char* argv[]) {
  if (argc < 2) {
    std::cout << "Usage: " << argv[0] << " <file>" << std::endl;
    exit(-1);
  }
  std::ifstream input(argv[1]);
  std::string steps((std::istreambuf_iterator<char>(input)),std::istreambuf_iterator<char>());
  std::list<std::string> instructions;
  tokenize(steps, ", ", instructions);
  Walker walker;

  while (instructions.size() != 0) {
    walker.instruction(instructions.front());
    instructions.pop_front();
  }

  std::cout << "Result: " << walker.getShortestPath() << std::endl;
}
