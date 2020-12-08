#include <fstream>
#include <iostream>
#include <set>
#include <vector>
#include <numeric>
#include <map>

#include "../include/utils.h"

int count_all_answered(const std::set<char>& questionaire, const std::vector<std::string>& answers) {
  std::map<char, unsigned> questions;
  for (auto q = questionaire.begin(); q != questionaire.end(); ++q) {
    unsigned cnt = 0;
    for (auto an = answers.begin(); an != answers.end(); ++an) {
      if (an->find_first_of(*q) != std::string::npos) {
        ++cnt;
      }
    }
    questions.insert(std::make_pair(*q, cnt));
  }
  unsigned counter = 0;
  for (auto q = questions.begin(); q != questions.end(); ++q) {
    if (q->second == answers.size()) {
      ++counter;
    }
  }
  return counter;
}

int main(int argc, char** argv) {
  if (argc < 2) {
    std::cout << "Usage: " << argv[0] << " <input>\n";
  }
  std::ifstream inf(argv[1]);
  std::string line;
  std::set<char> questionaire;
  std::vector<unsigned> any_yess;
  std::vector<unsigned> all_yess;
  std::vector<std::string> answers;
  while(std::getline(inf, line)) {
    if (line.empty()) {
      // std::cout << "q " << questionaire << '\n';
      any_yess.push_back(questionaire.size());
      all_yess.push_back(count_all_answered(questionaire, answers));
      questionaire.clear();
      answers.clear();
      continue;
    }
    answers.push_back(line);
    for (auto c = line.begin(); c != line.end(); ++c) {
      questionaire.insert(*c);
    }
  }
  // std::cout << "q " << questionaire << '\n';
  any_yess.push_back(questionaire.size());
  all_yess.push_back(count_all_answered(questionaire, answers));
  questionaire.clear();
  answers.clear();
  // std::cout << any_yess << '\n';
  std::cout << std::accumulate(any_yess.begin(), any_yess.end(), 0) << '\n';
  std::cout << std::accumulate(all_yess.begin(), all_yess.end(), 0) << '\n';
  return 0;
}
