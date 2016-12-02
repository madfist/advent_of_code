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
    std::cout << "(" << x0 << "," << y0 << "->" << x1 << "," << y1 << ") X (";
    std::cout << other.x0 << "," << other.y0 << "->" << other.x1 << "," << other.y1 << ")" << std::endl;
    //EAST
    if ((x1 > x0) &&
        (x0 < other.x0 && other.x0 < x1 || x0 < other.x1 && other.x1 < x1) &&
        (other.y0 <= y0 && y0 <= other.y1 || other.y1 <= y0 && y0 <= other.y0)) {
      int cx0 = (other.x0 <= other.x1) ? other.x0 : other.x1;
      cross.set(cx0, y0, cx0, y0);
      return true;
    }
    //WEST
    if ((x0 > x1) &&
        (x1 < other.x0 && other.x0 < x0 || x1 < other.x1 && other.x1 < x0) &&
        (other.y0 <= y0 && y0 <= other.y1 || other.y1 <= y0 && y0 <= other.y0)) {
      int cx0 = (other.x0 >= other.x1) ? other.x0 : other.x1;
      cross.set(cx0, y0, cx0, y0);
      return true;
    }
    //NORTH
    if ((y0 < y1) &&
        (y0 < other.y0 && other.y0 < y1 || y0 < other.y1 && other.y1 < y1) &&
        (other.x0 <= x0 && x0 <= other.x1 || other.x1 <= x0 && x0 <= other.x0)) {
      int cy0 = (other.y0 <= other.y1) ? other.y0 : other.y1;
      cross.set(x0, cy0, x0, cy0);
      return true;
    }
    //SOUTH
    if ((y0 > y1) &&
        (y1 < other.y0 && other.y0 < y0 || y1 < other.y1 && other.y1 < y0) &&
        (other.x0 <= x0 && x0 <= other.x1 || other.x1 <= x0 && x0 <= other.x0)) {
      int cy0 = (other.y0 >= other.y1) ? other.y0 : other.y1;
      cross.set(x0, cy0, x0, cy0);
      return true;
    }
    return false;
  }
};

class Walker {
public:
  Walker() : ft(), steps(), firstTwice(false) {
    Step step;
  }
  void instruction(const std::string& inst) {
    Step s;
    if (!steps.empty()) {
      s = steps.back();
    }
    Step next;
    std::string cmd = inst.substr(1);
    if (inst.c_str()[0] == 'L') {
      next = goLeft(s, std::stoi(cmd));
    } else {
      next = goRight(s, std::stoi(cmd));
    }
    if (!firstTwice) {
      crossCheck(next);
    }
    steps.push_back(next);
  }
  int getShortestPath() {
    Step s = steps.back();
    return abs(s.x1) + abs(s.y1);
  }
  int getFirstTwice() {
    std::cout << "(" << ft.x1 << "," << ft.y1 << ")" << std::endl;
    return (firstTwice) ? abs(ft.x1) + abs(ft.y1) : -1;
  }

private:
  Step goLeft(const Step& s, int step) {
    Step next;
    if (s.x1 > s.x0) { //EAST
      next.set(s.x1, s.y1, s.x1, s.y1+step);
    } else if (s.x1 < s.x0) { //WEST
      next.set(s.x1, s.y1, s.x1, s.y1-step);
    } else if (s.y1 < s.y0) { //SOUTH
      next.set(s.x1, s.y1, s.x1+step, s.y1);
    } else { //NORTH
      next.set(s.x1, s.y1, s.x1-step, s.y1);
    }
    return next;
  }
  Step goRight(const Step& s, int step) {
    Step next;
    if (s.x1 > s.x0) { //EAST
      next.set(s.x1, s.y1, s.x1, s.y1-step);
    } else if (s.x1 < s.x0) { //WEST
      next.set(s.x1, s.y1, s.x1, s.y1+step);
    } else if (s.y1 < s.y0) { //SOUTH
      next.set(s.x1, s.y1, s.x1-step, s.y1);
    } else { //NORTH
      next.set(s.x1, s.y1, s.x1+step, s.y1);
    }
    return next;
  }
  void crossCheck(Step& s) {
    for (std::list<Step>::iterator i = steps.begin(); i != steps.end() && !firstTwice; ++i) {
      firstTwice |= s.crossCheck(*i, ft);
    }
  }

  Step ft;
  std::list<Step> steps;
  bool firstTwice;
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

  std::cout << "Shortest path: " << walker.getShortestPath() << std::endl;
  std::cout << "Shortest path to first cross: " << walker.getFirstTwice() << std::endl;
}
