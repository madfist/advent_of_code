#include <iostream>
#include <fstream>
#include <sstream>
#include <vector>

struct Point;
struct Line;
typedef std::pair<char, int> step_t;
typedef std::vector<step_t> data_t;
typedef std::vector<Line> wire_t;
typedef std::vector<Point> crosses_t;

template<typename T1, typename T2>
std::ostream& operator<<(std::ostream& os, const std::pair<T1,T2>& s) {
  return os << s.first << ' ' << s.second;
}

template<typename T>
std::ostream& operator<<(std::ostream& os, const std::vector<T> v) {
  for (auto i = v.begin(); i != v.end(); ++i) {
    os << *i;
    if (i != v.end() - 1) {
      os << ' ';
    }
  }
  return os;
}

bool operator%(int x, std::pair<int, int> o) {
  return (o.first < o.second) ? (x > o.first && x < o.second) : (x < o.first && x > o.second);
}

struct Point {
  int x,y;
  bool operator%(const Line& l) const;
};

std::ostream& operator<<(std::ostream& os, const Point& p) {
  return os << '(' << p.x << ',' << p.y << ')';
}

struct Line {
  Point a,b;
  bool operator&&(const Line& l) const {
    if (a.x == b.x && l.a.y == l.b.y) {
      return l.a.y % std::make_pair(a.y, b.y) && a.x % std::make_pair(l.a.x, l.b.x);
    }
    if (a.y == b.y && l.a.x == l.b.x) {
      return l.a.x % std::make_pair(a.x, b.x) && a.y % std::make_pair(l.a.y, l.b.y);
    }
    return false;
  }
  Point operator&(const Line& l) const {
    if (*this && l) {
      return (a.x == b.x) ? Point{a.x, l.a.y} : Point{l.a.x, a.y};
    }
    return Point{0, 0};
  }
  int length() const {
    return std::abs(a.x - b.x + a.y - b.y);
  }
};

bool Point::operator%(const Line& l) const {
  return (l.a.x == l.b.x) ? x == l.a.x && y % std::make_pair(l.a.y, l.b.y) : y == l.a.y && x % std::make_pair(l.a.x, l.b.x);    
}

std::ostream& operator<<(std::ostream& os, const Line& l) {
  return os << l.a << "->" << l.b;
}

crosses_t operator&(const wire_t& w1, const wire_t& w2) {
  crosses_t ret;
  for (auto i = w1.begin(); i != w1.end(); ++i) {
    for (auto j = w2.begin(); j != w2.end(); ++j) {
      if (*i && *j) {
        std::cout << "cross" << *i << *j << '=' << (*i & *j) << '\n';
        ret.push_back(*i & *j);
      }
    }
  }
  return ret;
}

int manhattan(const Point& p) {
  return std::abs(p.x) + std::abs(p.y);
}

int wire_length(const wire_t& w) {
  int length = 0;
  for (auto i = w.begin(); i != w.end(); ++i) {
    length += i->length();
  }
  return length;
}

int wire_steps(const wire_t& w, const Point& p) {
  int steps = 0;
  for (auto i = w.begin(); i != w.end(); ++i) {
    if (p % *i) {
      return steps + (Line{i->a, p}).length();
    }
    steps += i->length();
  }
  return steps;
}

data_t read(std::istream& is) {
  std::string line;
  std::getline(is, line);
  std::stringstream ss(line);
  
  int num;
  char comma;
  char dir;
  data_t tokens;
  while (ss >> dir >> num >> comma) {
    //std::cout << "read " << dir << num << static_cast<int>(comma) << '\n';
    tokens.push_back(std::make_pair(dir, num));
  }
  ss >> dir >> num;
  tokens.push_back(std::make_pair(dir, num));
  return tokens;
}

wire_t parse(const data_t& data) {
  Point start = {0, 0};
  Point last = start;
  wire_t lines;
  for (auto s = data.begin(); s != data.end(); ++s) {
    switch (s->first) {
      case 'U':
        last = {last.x, last.y + s->second}; break;
      case 'L':
        last = {last.x - s->second, last.y}; break;
      case 'D':
        last = {last.x, last.y - s->second}; break;
      case 'R':
        last = {last.x + s->second, last.y}; break;
    }
    lines.push_back({start, last});
    start = last;
  }
  std::cout << "lines " << lines;
  return lines;
}

int task1(const wire_t& w1, const wire_t& w2) {
  crosses_t crs = w1 & w2;
  int min = manhattan(crs[0]);
  for (auto cr = crs.begin(); cr != crs.end(); ++cr) {
    min = std::min(min, manhattan(*cr));
  }
  return min;
}

int task2(const wire_t& w1, const wire_t& w2) {
  crosses_t crs = w1 & w2;
  int min = wire_length(w1) + wire_length(w2);
  for (auto cr = crs.begin(); cr != crs.end(); ++cr) {
    min = std::min(min, wire_steps(w1, *cr) +wire_steps(w2, *cr));
  }
  return min;
}

std::pair<int, int> solve(std::istream& is) {
  wire_t w1 = parse(read(is));
  wire_t w2 = parse(read(is));
  return std::make_pair(task1(w1, w2), task2(w1, w2));
}

int main(int argc, char **argv) {
  if (argc < 2) {
    std::cout << solve(std::cin) << '\n';
  } else {
    std::ifstream inf(argv[1]);
    std::cout << solve(inf) << '\n';
  }
  
  return 0;
}
