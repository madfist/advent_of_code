#include <iostream>
#include <vector>

template<typename T1, typename T2>
std::ostream& operator<<(std::ostream& os, const std::pair<T1,T2>& s) {
  return os << s.first << ' ' << s.second;
}

std::ostream& operator<<(std::ostream& os, const std::vector<char> v) {
  for (auto i = v.begin(); i != v.end(); ++i) {
    os << static_cast<int>(*i);
    if (i != v.end() - 1) {
      os << ' ';
    }
  }
  return os;
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
