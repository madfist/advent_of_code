#include <iostream>
#include <fstream>
#include <vector>
#include <array>

int task1(const std::vector<std::string>& forest, size_t step_down, size_t step_right) {
  int counter = 0;
  for (size_t i = 0, j = 0; i < forest.size(); i += step_down, ++j) {
    size_t l = forest[i].size();
    if (forest[i][j*step_right % l] == '#') {
      ++counter;
    }
  }
  return counter;
}

int task2(const std::vector<std::string>& forest, std::pair<size_t, size_t> steps[], size_t size) {
  int counter = 1;
  for (size_t i = 0; i < size; ++i) {
    counter *= task1(forest, steps[i].first, steps[i].second);
  }
  return counter;
}

int main(int argc, char** argv) {
  if (argc < 2) {
    std::cout << "Usage: " << argv[0] << " <input>\n";
  }
  std::ifstream inf(argv[1]);
  std::vector<std::string> forest;
  std::string line;
  while (inf >> line) {
    forest.push_back(line);
  }
  std::cout << task1(forest, 1, 3) << '\n';
  std::pair<size_t, size_t> task2_steps[5] = {
    std::make_pair(1, 1),
    std::make_pair(1, 3),
    std::make_pair(1, 5),
    std::make_pair(1, 7),
    std::make_pair(2, 1)
  };
  std::cout << task2(forest, task2_steps, 5) << '\n';
  return 0;
}
