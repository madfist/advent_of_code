#include <iostream>
#include <fstream>
#include <string>
#include <map>

typedef std::multimap<std::string, std::string> orbitmap_t;

std::ostream& operator<<(std::ostream &os, const orbitmap_t &om) {
  for (auto i = om.begin(); i != om.end(); ++i) {
    os << i->first << "->" << i->second << ' ';
  }
  return os;
}

orbitmap_t read(std::istream& is) {
  orbitmap_t omap;
  for (std::string a,b; !!std::getline(is, a, ')') && !!std::getline(is, b);) {
    omap.insert({a, b});
  }
  return omap;
}

int count_orbit(const orbitmap_t &om, const std::string &node, int indirects) {
  int sum = 1;
  auto children = om.equal_range(node);
  for (auto child = children.first; child != children.second; ++child) {
    sum += count_orbit(om, child->second, indirects + 1);
  }
  return sum + indirects;
}

std::pair<int, bool> orbital_transfer(const orbitmap_t &om, const std::string &root, const std::string &node1, const std::string &node2) {
  int sum = 0;
  if (root == node1) {
    return std::make_pair(0, true);  
  }
  if (root == node2) {
    return std::make_pair(0, true);
  }
  auto children = om.equal_range(root);
  int relevant_count = 0;
  for (auto child = children.first; child != children.second; ++child) {
    auto res = orbital_transfer(om, child->second, node1, node2);
    sum += static_cast<int>(res.second) + res.first;
    //std::cout << "  --" << child->second << "_r" << res.second << '\n';
    relevant_count += static_cast<int>(res.second);
  }
  //std::cout << 'r' << root << relevant_count << 's' << sum << '\n';
  if (relevant_count == 2) {
    sum -= 2;
  }
  return std::make_pair(sum, (relevant_count == 1));
}

int main(int argc, char** argv) {
  orbitmap_t om;
  if (argc < 2) {
    om = std::move(read(std::cin));
  } else {
    std::ifstream inf(argv[1]);
    om = std::move(read(inf));
  }
  std::cout << count_orbit(om, "COM", -1) << '\n';
  bool relevant = false;
  auto otr = orbital_transfer(om, "COM", "YOU", "SAN");
  std::cout << otr.first << '\n';
  return 0;
}
